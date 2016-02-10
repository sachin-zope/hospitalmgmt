package hospitalmgmt.servlets;

import hospitalmgmt.beans.DeliveryRegister;
import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.dao.DeliveryRegisterDAO;
import hospitalmgmt.dao.IndoorRegisterDAO;
import hospitalmgmt.dao.MTPRegisterDAO;
import hospitalmgmt.dao.OTRegisterDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "C:/navjeevan_files/";
	IndoorRegisterDAO indoorRegisterDAO = new IndoorRegisterDAO();
	DeliveryRegisterDAO deliveryRegisterDAO = new DeliveryRegisterDAO();
	MTPRegisterDAO mtpRegisterDAO = new MTPRegisterDAO();
	OTRegisterDAO otRegisterDAO = new OTRegisterDAO();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("request to upload file");
		String fileName = null;
		// process only if its multipart content
		if (isMultipart) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				// Parse the request
				List<FileItem> multiparts = upload.parseRequest(request);

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						String name = new File(item.getName()).getName();
						fileName = UPLOAD_DIRECTORY + File.separator
								+ name;
						item.write(new File(fileName));
					}
				}

				// File uploaded successfully
				request.setAttribute("message", "Your file has been imported successfully!");
				FileInputStream inputStream = new FileInputStream(new File(fileName));
		         
		        Workbook workbook = new XSSFWorkbook(inputStream);
		        Sheet firstSheet = workbook.getSheetAt(0);
		        Iterator<Row> iterator = firstSheet.iterator();
		        IndoorRegisterDAO indoorRegisterDAO = new IndoorRegisterDAO();
		        while (iterator.hasNext()) {
		            Row nextRow = iterator.next();
		            
		            IndoorRegister indoorRegister = new IndoorRegister();
		            int rowNum = nextRow.getRowNum();
		            System.out.print("Row: " + rowNum + " == ");
		            if(rowNum == 0) {
		            	continue;
		            }
		            
		            Date admitDate = nextRow.getCell(0).getDateCellValue();
		            indoorRegister.setAdmitDate(admitDate);
		            indoorRegister.setIpdNo(indoorRegisterDAO.generateIPDNo(admitDate));
		            System.out.println(indoorRegister.getAdmitDate());
		            indoorRegister.setDischargeDate(nextRow.getCell(1).getDateCellValue());
		            indoorRegister.setpName(nextRow.getCell(2).getStringCellValue());
		            indoorRegister.setGender(nextRow.getCell(3).getStringCellValue());
		            indoorRegister.setpAddress(nextRow.getCell(4).getStringCellValue());
		            indoorRegister.setAge(new Double(nextRow.getCell(5).getNumericCellValue()).intValue());
		            indoorRegister.setDiagnosis(nextRow.getCell(6).getStringCellValue());
		            String treatment = nextRow.getCell(7).getStringCellValue();
		            System.out.println(treatment);
		            indoorRegister.setTreatment(treatment);
		            indoorRegister.setFees(nextRow.getCell(17).getNumericCellValue());
		            indoorRegister.setRemarks(nextRow.getCell(18).getStringCellValue());
		            
		            if (treatment.equalsIgnoreCase("delivery")
							|| treatment.equalsIgnoreCase("lscs")) { // treatmentDelivery
		            	System.out.println("inside delivery");
						DeliveryRegister deliveryRegister = new DeliveryRegister(); 
						Date deliveryDate = nextRow.getCell(8).getDateCellValue();
						deliveryRegister.setDeliveryDate(deliveryDate);
						Long serialNo = deliveryRegisterDAO.generateSerialNo(deliveryDate);
						deliveryRegister.setSerialNo(serialNo);
						

						deliveryRegister.setEpisiotomy(nextRow.getCell(9).getStringCellValue());
						deliveryRegister.setDeliveryType(nextRow.getCell(10).getStringCellValue());
						deliveryRegister.setSexOfChild(nextRow.getCell(11).getStringCellValue());
						deliveryRegister.setBirthWeight(nextRow.getCell(12).getNumericCellValue());
						String bTime = nextRow.getCell(13).getNumericCellValue() + " " + nextRow.getCell(14).getStringCellValue();
						Date dtBTime = new SimpleDateFormat("h.mm a").parse(bTime);
						deliveryRegister.setBirthTime(new SimpleDateFormat("H:mm").format(dtBTime));
						deliveryRegister.setIndication(nextRow.getCell(15).getStringCellValue());
						deliveryRegister.setDeliveryRemarks(nextRow.getCell(16).getStringCellValue());
						indoorRegister.setDeliveryRegister(deliveryRegister);
						System.out.println("Delivery Register:" + deliveryRegister);

		            }
		            System.out.println(indoorRegister);
		            indoorRegisterDAO.save(indoorRegister);
		            System.out.println();
		        }
		         
		        inputStream.close();
			} catch (Exception e) {
				request.setAttribute("message", "File Upload Failed due to "
						+ e);
				e.printStackTrace();
			}
		} else {
			request.setAttribute("message",
					"This Servlet only handles file upload request");
		}
		request.getRequestDispatcher("/upload.jsp").forward(request, response);
	}

}
