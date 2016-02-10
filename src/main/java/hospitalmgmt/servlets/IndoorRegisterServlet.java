package hospitalmgmt.servlets;

import hospitalmgmt.beans.DeliveryRegister;
import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.beans.MTPRegister;
import hospitalmgmt.beans.OTRegister;
import hospitalmgmt.dao.DeliveryRegisterDAO;
import hospitalmgmt.dao.IndoorRegisterDAO;
import hospitalmgmt.dao.MTPRegisterDAO;
import hospitalmgmt.dao.OTRegisterDAO;
import hospitalmgmt.transformer.IndoorRegisterDTO;
import hospitalmgmt.transformer.Transform;
import hospitalmgmt.utils.AppConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * Servlet implementation class IndoorRegisterServlet
 */
public class IndoorRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String INSERT_UPDATE_URL = "/indoor_register.jsp";
	Logger logger = LoggerFactory.getLogger(IndoorRegisterServlet.class);
	
	IndoorRegisterDAO indoorRegisterDAO = new IndoorRegisterDAO();
	DeliveryRegisterDAO deliveryRegisterDAO = new DeliveryRegisterDAO();
	MTPRegisterDAO mtpRegisterDAO = new MTPRegisterDAO();
	OTRegisterDAO otRegisterDAO = new OTRegisterDAO();
	Transform transform = new Transform();
	Gson gson = new Gson();
	
	/**
	 * Default constructor.
	 */
	public IndoorRegisterServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		logger.info("Action: " + action);
		try {
			if(action != null) {
				if(action.equalsIgnoreCase("edit")) {
					int id = Integer.parseInt(request.getParameter("id"));
					IndoorRegister indoorRegister = indoorRegisterDAO.findById(id);
					request.setAttribute("INDOOR_REGISTER", indoorRegister);
					getServletContext().getRequestDispatcher("/edit_indoor_register.jsp").forward(
							request, response);
				} else if(action.equalsIgnoreCase("get_by_month")) {
					String month = request.getParameter("month");
					String year = request.getParameter("year");
					System.out.println("Month: " + month + " " + year);
					
					List<IndoorRegister> allIndoorRegister = null;
					try {
						allIndoorRegister = indoorRegisterDAO.findByMonth(month, year);
					} catch (Exception e) { 
						e.printStackTrace();
						allIndoorRegister = new ArrayList<IndoorRegister>();
					}
					
					List<IndoorRegisterDTO> convertedIndoorRegisters = transform.transformIndoorRegisters(allIndoorRegister);
					String fileName = "IndoorRegister_" + month + year + ".pdf";
					indoorRegisterDAO.writeToPDF(fileName, convertedIndoorRegisters, month, year);
					
					HttpSession session = request.getSession();
					session.setAttribute("FILE_NAME", fileName);
					
					response.setContentType("application/json");
					response.getWriter().write(gson.toJson(convertedIndoorRegisters));
				} else if(action.equalsIgnoreCase("get_incomplete_indoor_register")) {
					List<IndoorRegister> allIndoorRegister = null;
					try {
						allIndoorRegister = indoorRegisterDAO.getIncompleteIndoorRegister();
						System.out.println("number of records in indoor register do not have discharge date :" + allIndoorRegister.size());
					} catch (Exception e) { 
						e.printStackTrace();
						allIndoorRegister = new ArrayList<IndoorRegister>();
					}
					List<IndoorRegisterDTO> convertedIndoorRegisters = transform.transformIndoorRegisters(allIndoorRegister);
					response.setContentType("application/json");
					response.getWriter().write(gson.toJson(convertedIndoorRegisters));
				} else if(action.equalsIgnoreCase("download")) {
					HttpSession session = request.getSession();
					String fileName = session.getAttribute("FILE_NAME") != null ? session.getAttribute("FILE_NAME").toString() : null;
					OutputStream os = null;
					if(fileName != null) {
						File fileToDownload = new File(AppConstants.DOWNLOAD_FILE_PATH + fileName);
						
						response.setContentType("application/pdf");
						response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
						response.setContentLength((int) fileToDownload.length());
						
						FileInputStream fileInputStream = new FileInputStream(fileToDownload);
						OutputStream responseOutputStream = response.getOutputStream();
						int bytes;
						while ((bytes = fileInputStream.read()) != -1) {
							responseOutputStream.write(bytes);
						}
					}
				}
			} else {
				throw new Exception("no action defined");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("indoor_register.jsp");;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("Action:" + action);
		if(action != null && action.length() > 0) {
			if(action.equalsIgnoreCase("edit")) {
				try {
					int indoorId = Integer.parseInt(request.getParameter("indoorId"));
					IndoorRegister indoorRegister = indoorRegisterDAO.findById(indoorId);
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else if(action.equalsIgnoreCase("update_discharge_date")) { 
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String dischargeDate = request.getParameter("dischargeDate");
				String id = request.getParameter("id");
				System.out.println(dischargeDate + "  " + id);
				IndoorRegister indoorRegister = null;
				try {
				  if (id != null && !id.isEmpty()) {
					indoorRegister = indoorRegisterDAO.findById(Integer.parseInt(id));
					indoorRegister.setDischargeDate(dateFormatter.parse(dischargeDate));
				  }
				  if(indoorRegister != null) {
					  indoorRegisterDAO.update(indoorRegister);
					  RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/incomplete_indoor_register_report.jsp");
					  dispatcher.forward(request, response);
				  }
				} catch (ParseException pe) {
					pe.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} if(action.equalsIgnoreCase("delete")) {
				int id = Integer.parseInt(request.getParameter("id"));
				String src = request.getParameter("src");
				System.out.println("deleting indoor register id:" + id);
				
				String page = "/indoor_register.jsp";
				if(indoorRegisterDAO.delete(id)) {
					if(src.equalsIgnoreCase("incomplete_register")) {
						page = "/incomplete_indoor_register_report.jsp";
					} else if(src.equalsIgnoreCase("indoor_register")) {
						page = "/indoor_register_report.jsp";
					}
				} 
				getServletContext().getRequestDispatcher(page).forward(
						request, response);
			} else if(action.equalsIgnoreCase("add")){
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				IndoorRegister indoorRegister = new IndoorRegister();
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(INSERT_UPDATE_URL);
				try {
					String strAdmitDate = request.getParameter("admitDate");
					Date admitDate = dateFormatter.parse(strAdmitDate);
					indoorRegister.setAdmitDate(admitDate);
		
					Long ipdNo = indoorRegisterDAO.generateIPDNo(admitDate);
					indoorRegister.setIpdNo(ipdNo);
					
					String dischargeDate = request.getParameter("dischargeDate");
					try {
						indoorRegister.setDischargeDate(dateFormatter.parse(dischargeDate));
					} catch (ParseException pe) {
						indoorRegister.setDischargeDate(null);
					}
					
					indoorRegister.setpName(request.getParameter("pName"));
					indoorRegister.setGender(request.getParameter("gender"));
					indoorRegister.setpAddress(request.getParameter("pAddress"));
					indoorRegister.setAge(Integer.parseInt(request.getParameter("age")));
					String diagnosis = request.getParameter("diagnosis");
					if(diagnosis.equalsIgnoreCase("other")){
						indoorRegister.setDiagnosis(request.getParameter("OtherDiagnosis"));
					} else {
						indoorRegister.setDiagnosis(diagnosis);
					}
			
					String treatment = request.getParameter("treatment");
					indoorRegister.setTreatment(treatment);
					indoorRegister.setRemarks(request.getParameter("remarks"));
					
					String fees = request.getParameter("fees");
					if(fees != null && fees.length() > 0) {
						indoorRegister.setFees(Double.valueOf(fees));
					}
					
					// treatmentMTP
					if (treatment.equalsIgnoreCase("mtp")) {
						MTPRegister mtpRegister = new MTPRegister();
						mtpRegister.setDurationOfPregnancy(Integer.parseInt(request.getParameter("durationOfPregnancy")));
						mtpRegister.setgName(request.getParameter("gName"));
						mtpRegister.setReligion(request.getParameter("religion"));
						mtpRegister.setMarried(request.getParameter("married"));
						mtpRegister.setMindication(request.getParameter("mindication"));
						mtpRegister.setProcedure(request.getParameter("procedure"));
						mtpRegister.setAlongWith(request.getParameter("alongWith"));
						String strMChildrens = request.getParameter("mChildrens"); 
						int mChildrens =  (strMChildrens != null && strMChildrens.length() > 0 ) ? Integer.parseInt(strMChildrens) : 0;  
						mtpRegister.setmChildrens(mChildrens);
						
						String strFChildrens = request.getParameter("fChildrens");
						int fChildrens = (strFChildrens != null && strFChildrens.length() > 0 ? Integer.parseInt(strFChildrens) : 0);
						mtpRegister.setfChildrens(fChildrens);
						
						mtpRegister.setDoneByDr(request.getParameter("doneby"));
						mtpRegister.setOpinionGivenBy(request.getParameter("opinionby"));
						
						Date operationDate = dateFormatter.parse(request.getParameter("mtpOperationDate"));
						mtpRegister.setOperationDate(operationDate);
						Integer serialNo = mtpRegisterDAO.generateSerialNo(operationDate);
						if(serialNo.intValue() == 0) {
							throw new Exception("MTP serial number not generated correctly");
						}
						mtpRegister.setMtpSerialNo(serialNo);
						indoorRegister.setMtpRegister(mtpRegister);
						System.out.println(mtpRegister);
					} else if (treatment.equalsIgnoreCase("mtp + abdominal tubectomy") || treatment.equalsIgnoreCase("mtp + laparoscopic tubectomy")) {  
						MTPRegister mtpRegister = new MTPRegister();
						mtpRegister.setDurationOfPregnancy(Integer.parseInt(request.getParameter("MTdurationOfPregnancy")));
						mtpRegister.setgName(request.getParameter("gName"));
						mtpRegister.setReligion(request.getParameter("MTreligion"));
						mtpRegister.setMarried(request.getParameter("MTmarried"));
						mtpRegister.setMindication(request.getParameter("MTmindication"));
						mtpRegister.setProcedure(request.getParameter("MTprocedure"));
						mtpRegister.setAlongWith(request.getParameter("MTalongWith"));
						String strMChildrens = request.getParameter("MTmChildrens"); 
						int mChildrens =  (strMChildrens != null && strMChildrens.length() > 0 ) ? Integer.parseInt(strMChildrens) : 0;  
						mtpRegister.setmChildrens(mChildrens);
						
						String strFChildrens = request.getParameter("MTfChildrens");
						int fChildrens = (strFChildrens != null && strFChildrens.length() > 0 ? Integer.parseInt(strFChildrens) : 0);
						mtpRegister.setfChildrens(fChildrens);
						
						mtpRegister.setDoneByDr(request.getParameter("MTdoneby"));
						mtpRegister.setOpinionGivenBy(request.getParameter("MTopinionby"));
						
						Date operationDate = dateFormatter.parse(request.getParameter("MTmtpOperationDate"));
						mtpRegister.setOperationDate(operationDate);
						Integer serialNo = mtpRegisterDAO.generateSerialNo(operationDate);
						if(serialNo.intValue() == 0) {
							throw new Exception("MTP serial number not generated correctly");
						}
						mtpRegister.setMtpSerialNo(serialNo);
						indoorRegister.setMtpRegister(mtpRegister);
						System.out.println(mtpRegister);
						
						OTRegister otRegister = new OTRegister();
						otRegister.setNameOfSurgeon(request.getParameter("MTdoneby"));
						otRegister.setAssistant(request.getParameter("assistantForTubectomy"));
						otRegister.setAnaesthetist(request.getParameter("anaesthetistForTubectomy"));
						//String strOperationDate = deliveryDate;
						//System.out.println("operationDate =" + strOperationDate);
						//Date operationDate = dateFormatter.parse(strOperationDate);
						otRegister.setOperationDate(operationDate);;
						
						Long otSerialNo = otRegisterDAO.generateSerialNo(operationDate);
						otRegister.setSerialNo(otSerialNo);
						
						System.out.println(otRegister);
						indoorRegister.setOtRegister(otRegister);
						
					}
					else if (treatment.equalsIgnoreCase("delivery")) { // treatmentDelivery
						DeliveryRegister deliveryRegister = new DeliveryRegister(); 
						String deliveryDate = request.getParameter("deliveryDate");
						deliveryRegister.setDeliveryDate(dateFormatter.parse(deliveryDate));
						Long serialNo = deliveryRegisterDAO.generateSerialNo(dateFormatter.parse(deliveryDate));
						deliveryRegister.setSerialNo(serialNo);
						String episiotomy = (request.getParameter("episiotomy") != null) ? "Given" : null;
						deliveryRegister.setEpisiotomy(episiotomy);
						deliveryRegister.setDeliveryType(request.getParameter("deliveryType"));
						deliveryRegister.setSexOfChild(request.getParameter("sexOfChild"));
						deliveryRegister.setBirthWeight(Double.valueOf(request.getParameter("birthWeight")));
						deliveryRegister.setBirthTime(request.getParameter("birthTime"));
						String indication = request.getParameter("indication");
						if(indication != null && indication.equalsIgnoreCase("other")) {
							deliveryRegister.setIndication(request.getParameter("otherIndication"));
						} else {
							deliveryRegister.setIndication(indication);
						}
						deliveryRegister.setDeliveryRemarks(request.getParameter("deliveryRemarks"));
						indoorRegister.setDeliveryRegister(deliveryRegister);
						System.out.println(deliveryRegister);
						
					} else if(treatment.equalsIgnoreCase("lscs") || treatment.equalsIgnoreCase("LSCS with Tubectomy")) {
						DeliveryRegister deliveryRegister = new DeliveryRegister(); 
						String deliveryDate = request.getParameter("deliveryDateForLSCS");
						deliveryRegister.setDeliveryDate(dateFormatter.parse(deliveryDate));
						Long serialNo = deliveryRegisterDAO.generateSerialNo(dateFormatter.parse(deliveryDate));
						deliveryRegister.setSerialNo(serialNo);
						String episiotomy = (request.getParameter("episiotomyForLSCS") != null) ? "Given" : null;
						deliveryRegister.setEpisiotomy(episiotomy);
						deliveryRegister.setDeliveryType(request.getParameter("deliveryTypeForLSCS"));
						deliveryRegister.setSexOfChild(request.getParameter("sexOfChildForLSCS"));
						deliveryRegister.setBirthWeight(Double.valueOf(request.getParameter("birthWeightForLSCS")));
						deliveryRegister.setBirthTime(request.getParameter("birthTimeForLSCS"));
						String indication = request.getParameter("indicationForLSCS");
						if(indication != null && indication.equalsIgnoreCase("other")) {
							deliveryRegister.setIndication(request.getParameter("otherIndicationForLSCS"));
						} else {
							deliveryRegister.setIndication(indication);
						}
						deliveryRegister.setDeliveryRemarks(request.getParameter("deliveryRemarksForLSCS"));
						indoorRegister.setDeliveryRegister(deliveryRegister);
						System.out.println(deliveryRegister);
						
						OTRegister otRegister = new OTRegister();
						otRegister.setNameOfSurgeon(request.getParameter("NameOfSurgeonForLSCS"));
						otRegister.setAssistant(request.getParameter("assistantForLSCS"));
						otRegister.setAnaesthetist(request.getParameter("anaesthetistForLSCS"));
						String strOperationDate = deliveryDate;
						System.out.println("operationDate =" + strOperationDate);
						Date operationDate = dateFormatter.parse(strOperationDate);
						otRegister.setOperationDate(operationDate);;
						
						Long otSerialNo = otRegisterDAO.generateSerialNo(operationDate);
						otRegister.setSerialNo(otSerialNo);
						
						System.out.println(otRegister);
						indoorRegister.setOtRegister(otRegister);
					} else if (treatment.equalsIgnoreCase("other")) {
						indoorRegister.setTreatment(request.getParameter("OtherTreatment")); 
						
						OTRegister otRegister = new OTRegister();
						otRegister.setNameOfSurgeon(request.getParameter("NameOfSurgeon"));
						otRegister.setAssistant(request.getParameter("assistant"));
						otRegister.setAnaesthetist(request.getParameter("anaesthetist"));
						String strOperationDate = request.getParameter("operationDate");
						System.out.println("operationDate =" + strOperationDate);
						Date operationDate = dateFormatter.parse(strOperationDate);
						otRegister.setOperationDate(operationDate);;
						
						Long otSerialNo = otRegisterDAO.generateSerialNo(operationDate);
						otRegister.setSerialNo(otSerialNo);
						
						System.out.println(otRegister);
						indoorRegister.setOtRegister(otRegister);
					} else {
						// treatmentOperation
						OTRegister otRegister = new OTRegister();
						otRegister.setNameOfSurgeon(request.getParameter("NameOfSurgeon"));
						otRegister.setAssistant(request.getParameter("assistant"));
						otRegister.setAnaesthetist(request.getParameter("anaesthetist"));
						String strOperationDate = request.getParameter("operationDate");
						System.out.println("operationDate =" + strOperationDate);
						Date operationDate = dateFormatter.parse(strOperationDate);
						otRegister.setOperationDate(operationDate);
						
						Long otSerialNo = otRegisterDAO.generateSerialNo(operationDate);
						otRegister.setSerialNo(otSerialNo);
						
						System.out.println(otRegister);
						indoorRegister.setOtRegister(otRegister);
					}
					System.out.println(indoorRegister);
					indoorRegisterDAO.save(indoorRegister);
					
					request.setAttribute("RESP", "success");
					
				} catch(ParseException pe) {
					pe.printStackTrace();
					request.setAttribute("RESP", "error");
					request.setAttribute("ERROR", pe.getMessage());
				} catch(Exception e) {
					e.printStackTrace();
					request.setAttribute("RESP", "error");
					request.setAttribute("ERROR", e.getMessage());
				}
				
				dispatcher.forward(request, response);
			}
		}
	}
}

