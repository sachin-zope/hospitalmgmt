import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestHibernate {

	public static void main(String[] args) {
		try {
			final String time = "23:15";

			try {
			    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
			    final Date dateObj = sdf.parse(time);
			    System.out.println(dateObj);
			    System.out.println(new SimpleDateFormat("K:mm a").format(dateObj));
			} catch (final ParseException e) {
			    e.printStackTrace();
			}
			/*Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			MTPRegister mtpRegister = new MTPRegister();
			mtpRegister.setAlongWith("Coper T");
			mtpRegister.setDurationOfPregnancy(2);
			mtpRegister.setfChildrens(2);
			mtpRegister.setmChildrens(1);
			mtpRegister.setgName("fsaasfas adfas");
			mtpRegister.setMindication("safasdf sgsd");
			mtpRegister.setMtpSerialNo(201502);
			mtpRegister.setOperationDate(new Date());
			mtpRegister.setProcedure("sgfsadfsa");
			mtpRegister.setReligion("Hindu");
			session.save(mtpRegister);
			session.getTransaction().commit();
			session.close();*/
			
		//Session session = HibernateUtil.getSessionFactory().openSession();
		//session.beginTransaction();
		/*IndoorRegisterDAO dao = new IndoorRegisterDAO();
		Long prevSerialNo = dao.getLastSerialNo(new Date());
		System.out.println(prevSerialNo);
		
		IndoorRegister ir = dao.findById(10);
		System.out.println(ir.toString());
		
		List<IndoorRegister> irs = dao.findByMonth("Oct", "2015");
		for (Iterator iterator = irs.iterator(); iterator.hasNext();) {
			IndoorRegister indoorRegister = (IndoorRegister) iterator.next();
			System.out.println(indoorRegister);
		}*/
			
		/*OTRegisterDAO dao = new OTRegisterDAO();
		OTRegister otRegister = dao.findById(3);
		System.out.println(otRegister.toString());*/
		/*List<OTRegister> ors = dao.getAll();
		for (Iterator iterator = ors.iterator(); iterator.hasNext();) {
			OTRegister otRegister = (OTRegister) iterator.next();
			System.out.println(otRegister.toString());
		}*/
		
		/*IndoorRegister ir = new IndoorRegister();
		ir.setAdmitDate(new Date());
		ir.setDischargeDate(new Date());
		ir.setpName("sdfsadfsa");
		ir.setGender("Female");
		ir.setAge(23);
		ir.setDiagnosis("G2P1L1");
		ir.setTreatment("LSCS");
		//ir.setCreateDate(new Date());
		OTRegister otRegister = new OTRegister();
		otRegister.setNameOfSurgeon("Dr. XYZ");
		otRegister.setAnaesthetist("dsgsdfg");
		otRegister.setAssistant("fasdf");
		otRegister.setOperationDate(new Date());
		otRegister.setCreateDate(new Date());
		
		ir.setOtRegister(otRegister);
		
		session.save(ir);
		session.getTransaction().commit();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
