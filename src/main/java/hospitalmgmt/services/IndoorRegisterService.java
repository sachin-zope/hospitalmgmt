package hospitalmgmt.services;

import hospitalmgmt.beans.IndoorRegister;

import java.util.ArrayList;
import java.util.List;

public class IndoorRegisterService {
	
	public List<IndoorRegister> getAllIndoorRegisterRecords() {
		List<IndoorRegister> allIndoorRegister = new ArrayList<IndoorRegister>();
		IndoorRegister ir = new IndoorRegister();
		
		//ir.setAdmitDate("25/08/2015");
		//ir.setDischargeDate("29/08/2015");
		ir.setpName("This is test name");
		ir.setGender("Male");
		ir.setpAddress("Plot no. 3, something street, city");
		ir.setAge(23);
		ir.setDiagnosis("Infertility for diagnostic laparoscopy");
		ir.setTreatment("Laparoscopic Hysterectomy");
		ir.setFees(1234.00);
		allIndoorRegister.add(ir);
		allIndoorRegister.add(ir);
		allIndoorRegister.add(ir);
		allIndoorRegister.add(ir);
		allIndoorRegister.add(ir);
		allIndoorRegister.add(ir);
		
		return allIndoorRegister;
	}

}
