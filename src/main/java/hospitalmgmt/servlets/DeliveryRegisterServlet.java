package hospitalmgmt.servlets;

import hospitalmgmt.beans.DeliveryRegister;
import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.dao.DeliveryRegisterDAO;
import hospitalmgmt.transformer.IndoorRegisterDTO;
import hospitalmgmt.transformer.Transform;
import hospitalmgmt.utils.AppConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class DeliveryRegisterServlet
 */
public class DeliveryRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DeliveryRegisterDAO deliveryRegisterDAO = new DeliveryRegisterDAO();
    Transform transform = new Transform();
	Gson gson = new Gson();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliveryRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
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
					response.getWriter().write(gson.toJson(convertedIndoorRegisters));
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
				} else if(action.equalsIgnoreCase("delete")) {
					//int deliveryRegisterId = Integer.parseInt(request.getParameter("id"));
					//DeliveryRegister deliveryRegister = deliveryRegisterDAO.findById(deliveryRegisterId);
					//deliveryRegisterDAO.delete(deliveryRegister);
				}
			} else {
				throw new Exception("no action defined");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("delivery_register_report.jsp");;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
