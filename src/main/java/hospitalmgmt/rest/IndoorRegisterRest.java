package hospitalmgmt.rest;

import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.dao.IndoorRegisterDAO;
import hospitalmgmt.transformer.Transform;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("indoorregister")
public class IndoorRegisterRest {
	private IndoorRegisterDAO indoorRegisterDAO = new IndoorRegisterDAO();
	Transform transform = new Transform();
	Gson gson = new Gson();
	
/*	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<IndoorRegisterDTO> getIndoorRegisterRecords(@QueryParam("month") String month, @QueryParam("year") String year) {
		
		System.out.println("Month: " + month + " " + year);
		indoorRegisterDAO = new IndoorRegisterDAO();
		List<IndoorRegister> allIndoorRegister = indoorRegisterDAO.findByMonth(month, year);
		
		Transform transform = new Transform();
		return transform.transformIndoorRegisters(allIndoorRegister);
	}*/
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getIndoorRegisterById(@PathParam("id") int id) {
		IndoorRegister ir = null;
		try {
			ir = indoorRegisterDAO.findById(id);
		} catch(Exception e) {
			e.printStackTrace();
			ir = new IndoorRegister();
		}
		return gson.toJson(transform.transformIndoorRegister(ir));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIndoorRegisterRecords(@QueryParam("month") String month, @QueryParam("year") String year) {
		
		System.out.println("Month: " + month + " " + year);
		List<IndoorRegister> allIndoorRegister = null;
		try {
			allIndoorRegister = indoorRegisterDAO.findByMonth(month, year);
		} catch (Exception e) { 
			e.printStackTrace();
			allIndoorRegister = new ArrayList<IndoorRegister>();
		}
		
		return gson.toJson(transform.transformIndoorRegisters(allIndoorRegister));
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String saveIndoorRegister() {
		return null;
	}

}
