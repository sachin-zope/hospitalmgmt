package hospitalmgmt.rest;

import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.dao.OTRegisterDAO;
import hospitalmgmt.transformer.Transform;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("otregister")
public class OTRegisterRest {
	private OTRegisterDAO otRegisterDAO = null;
	private Gson gson = new Gson();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMTPRegisterRecords(@QueryParam("month") String month, @QueryParam("year") String year) {
		
		System.out.println("Month: " + month + " " + year);
		otRegisterDAO = new OTRegisterDAO();
		//List<DeliveryRegister> allDeliveryRegister = deliveryRegisterDAO.findByMonth(month, year);
		List<IndoorRegister> allDeliveryRegister = otRegisterDAO.findByMonth(month, year);
		Transform transform = new Transform();
		String jsonToStr = gson.toJson(transform.transformIndoorRegisters(allDeliveryRegister));
		System.out.println("returning response:" + jsonToStr);
		return jsonToStr;
	}
}
