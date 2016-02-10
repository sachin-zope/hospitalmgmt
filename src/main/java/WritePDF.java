import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.dao.IndoorRegisterDAO;
import hospitalmgmt.dao.MTPRegisterDAO;
import hospitalmgmt.transformer.Transform;
import hospitalmgmt.utils.AppUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WritePDF {
	public static void main(String[] args) {
		/*try {
			IndoorRegisterDAO dao = new IndoorRegisterDAO();
			Transform t = new Transform();
			List<IndoorRegister> indoorRegisters = dao.findByMonth("Nov", "2015");
			
			dao.writeToPDF("Indoor_Register_Temp.pdf", t.transformIndoorRegisters(indoorRegisters));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/*MTPRegisterDAO dao = new MTPRegisterDAO();
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 9, 2);
		
		try {
			int serialNo = dao.generateSerialNo(cal.getTime());
			System.out.println(serialNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
	}
}
