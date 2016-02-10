package hospitalmgmt.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

	public static Date getIPDResetDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH); 

		int resetMonth = 3;
		int resetDay = 1;
	
		Calendar resetDate = Calendar.getInstance();

		if(currentMonth >= 3 && currentMonth <= 11) {
			resetDate.set(currentYear, resetMonth, resetDay);
		}
		
		if(currentMonth >=0 && currentMonth <= 2) {
			resetDate.set(currentYear - 1, resetMonth, resetDay);
		}
		
		System.out.println("Reset Date : " + sdf.format(resetDate.getTime()));
		return resetDate.getTime();
	}
	
	public static Date getMTPResetDate(Date operationDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(operationDate);
		int currentYear = cal.get(Calendar.YEAR);
		Calendar resetDate = Calendar.getInstance();
		resetDate.set(currentYear, 0, 1);
		
		return resetDate.getTime();
	}
}
