package hospitalmgmt.servlets;

import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.beans.MTPRegister;
import hospitalmgmt.beans.MTPRegisterDetails;
import hospitalmgmt.dao.MTPRegisterDAO;
import hospitalmgmt.dao.MTPRegisterDetailsDAO;
import hospitalmgmt.transformer.IndoorMTPDTO;
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

import com.google.gson.Gson;

/**
 * Servlet implementation class MTPRegisterServlet
 */
public class MTPRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MTPRegisterDAO mtpRegisterDAO = new MTPRegisterDAO();
	MTPRegisterDetailsDAO mtpRegisterDetailsDAO = new MTPRegisterDetailsDAO();
    Transform transform = new Transform();
	Gson gson = new Gson();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MTPRegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("Action: " + action);
		try {
			if(action != null) {
				if(action.equalsIgnoreCase("edit")) {
					//int id = Integer.parseInt(request.getParameter("id"));
					//IndoorRegister indoorRegister = indoorRegisterDAO.findById(id);
					//request.setAttribute("indoorRegister", indoorRegister);
					//response.sendRedirect(INSERT_UPDATE_URL);
				} else if(action.equalsIgnoreCase("get_by_month")) {
					String month = request.getParameter("month");
					String year = request.getParameter("year");
					System.out.println("Month: " + month + " " + year);
					
					List<IndoorRegister> allIndoorRegister = null;
					List<MTPRegisterDetails> mtpRegisterDetails = null;
					List<IndoorMTPDTO> indoorMTPDTOs = new ArrayList<IndoorMTPDTO>();
					Transform transform = new Transform();
					
					try {
						allIndoorRegister = mtpRegisterDAO.findByMonth(month, year);
						mtpRegisterDetails = mtpRegisterDetailsDAO.findByMonth(month, year);
						
						List<IndoorMTPDTO> indoorMTPDTOs1 = transform.transformIndoorToMTP(allIndoorRegister); 
						List<IndoorMTPDTO> indoorMTPDTOs2 = transform.transformMTPDetailsToMTP(mtpRegisterDetails);
						indoorMTPDTOs = transform.mergeOrdered(indoorMTPDTOs1, indoorMTPDTOs2);
						transform.generateMonthlySerialNo(indoorMTPDTOs);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String jsonToStr = gson.toJson(indoorMTPDTOs);
					System.out.println("returning response:" + jsonToStr);
					
					String fileName = "MTPRegister_" + month + year + ".pdf";
					mtpRegisterDAO.writeToPDF(fileName, indoorMTPDTOs, month, year);
					
					HttpSession session = request.getSession();
					session.setAttribute("FILE_NAME", fileName);
					
					response.setContentType("application/json");
					response.getWriter().write(gson.toJson(indoorMTPDTOs));
					/*List<IndoorRegister> allIndoorRegister = null;
					try {
						allIndoorRegister = deliveryRegisterDAO.findByMonth(month, year);
					} catch (Exception e) { 
						e.printStackTrace();
						allIndoorRegister = new ArrayList<IndoorRegister>();
					}
					
					List<IndoorRegisterDTO> convertedIndoorRegisters = transform.transformIndoorRegisters(allIndoorRegister);
					String fileName = "DeliveryRegister_" + month + year + ".pdf";
					deliveryRegisterDAO.writeToPDF(fileName, convertedIndoorRegisters, month, year);
					
					HttpSession session = request.getSession();
					session.setAttribute("FILE_NAME", fileName);
					
					response.setContentType("application/json");
					response.getWriter().write(gson.toJson(convertedIndoorRegisters));*/
				} else if(action.equalsIgnoreCase("download")) {
					HttpSession session = request.getSession();
					String fileName = session.getAttribute("FILE_NAME") != null ? session.getAttribute("FILE_NAME").toString() : null;
					
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mtp_register.jsp");
		try {
			MTPRegisterDetails mtpRegisterDetails = new MTPRegisterDetails();
			MTPRegister mtpRegister = new MTPRegister();
			
			mtpRegisterDetails.setpName(request.getParameter("pName"));
			mtpRegisterDetails.setGender(request.getParameter("gender"));
			mtpRegisterDetails.setpAddress(request.getParameter("pAddress"));
			mtpRegisterDetails.setAge(Integer.parseInt(request.getParameter("age")));
			
			mtpRegister.setDurationOfPregnancy(Integer.parseInt(request.getParameter("durationOfPregnancy")));
			mtpRegister.setReligion(request.getParameter("religion"));
			mtpRegister.setMarried(request.getParameter("married"));
			mtpRegister.setMindication(request.getParameter("indication"));

			String procedure = request.getParameter("procedure");
			mtpRegister.setProcedure(procedure);
			if(procedure.equalsIgnoreCase("Medication abortion")) {
				mtpRegister.setBatchNo(request.getParameter("batchNo"));
			} 
			mtpRegister.setAlongWith(request.getParameter("alongWith"));
			
			String strMChildrens = request.getParameter("mChildrens"); 
			int mChildrens =  (strMChildrens != null && strMChildrens.length() > 0 ) ? Integer.parseInt(strMChildrens) : 0;  
			mtpRegister.setmChildrens(mChildrens);
			
			String strFChildrens = request.getParameter("fChildrens");
			int fChildrens = (strFChildrens != null && strFChildrens.length() > 0 ? Integer.parseInt(strFChildrens) : 0);
			mtpRegister.setfChildrens(fChildrens);
			
			mtpRegister.setDoneByDr(request.getParameter("doneby"));
			mtpRegister.setOpinionGivenBy(request.getParameter("opinionby"));
			
			Date opdDate = dateFormatter.parse(request.getParameter("opdDate"));
			mtpRegister.setOperationDate(opdDate);
			
			mtpRegisterDetails.setRemarks(request.getParameter("remarks"));
			
			String fees = request.getParameter("fees");
			if(fees != null && fees.length() > 0) {
				mtpRegisterDetails.setFees(Double.valueOf(fees));
			}
			
			Integer serialNo = mtpRegisterDAO.generateSerialNo(opdDate);
			if(serialNo.intValue() == 0) {
				throw new Exception("MTP serial number not generated correctly");
			}
			mtpRegister.setMtpSerialNo(serialNo);
			
			mtpRegisterDetails.setMtpRegister(mtpRegister);
			System.out.println(mtpRegister);
			System.out.println(mtpRegisterDetails);
			
			mtpRegisterDetailsDAO.save(mtpRegisterDetails);
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
