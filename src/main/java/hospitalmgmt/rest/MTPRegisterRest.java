package hospitalmgmt.rest;

import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.beans.MTPRegisterDetails;
import hospitalmgmt.dao.MTPRegisterDAO;
import hospitalmgmt.dao.MTPRegisterDetailsDAO;
import hospitalmgmt.transformer.IndoorMTPDTO;
import hospitalmgmt.transformer.Transform;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("mtpregister")
public class MTPRegisterRest {
	private MTPRegisterDAO mtpRegisterDAO = null;
	private MTPRegisterDetailsDAO mtpRegisterDetailsDAO = null;
	
	private Gson gson = new Gson();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMTPRegisterRecords(@QueryParam("month") String month, @QueryParam("year") String year) {
		
		System.out.println("Month: " + month + " " + year);
		mtpRegisterDAO = new MTPRegisterDAO();
		mtpRegisterDetailsDAO = new MTPRegisterDetailsDAO();
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
		return jsonToStr;
	}
}
