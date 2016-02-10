package hospitalmgmt.dao;

import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.transformer.IndoorRegisterDTO;
import hospitalmgmt.utils.AppConstants;
import hospitalmgmt.utils.AppUtil;
import hospitalmgmt.utils.HibernateUtil;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class IndoorRegisterDAO {

	@SuppressWarnings("unchecked")
	public List<IndoorRegister> getAll() throws Exception {
		
		Session session = null;
		List<IndoorRegister> indoorRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from IndoorRegister");
			indoorRegisters = query.list();	
		} catch (Exception e) {
			throw e;
		} finally {
			if(session != null)
				session.close();
		}
		
		return indoorRegisters;
	}
	
	public IndoorRegister findById(int indoorRegisterId) throws Exception {
		Session session = null;
		IndoorRegister indoorRegister = null; 
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from IndoorRegister where id = :indoorRegisterId");
			query.setParameter("indoorRegisterId", indoorRegisterId);
			
			indoorRegister = (IndoorRegister) query.list().get(0);
		} catch (Exception e) {
			throw e;
		} finally {
			if(session != null) 
				session.close();
		}
		
		return indoorRegister;
	}
	
	@SuppressWarnings("unchecked")
	public List<IndoorRegister> getIncompleteIndoorRegister() throws Exception {
		Session session = null;
		List<IndoorRegister> indoorRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from IndoorRegister where dischargeDate is null");
			indoorRegisters = query.list();	
		} catch (Exception e) {
			throw e;
		} finally {
			if(session != null)
				session.close();
		}
		
		return indoorRegisters;
	}
	
	public boolean delete(int id) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			//IndoorRegister ir = findById(id);
			Object obj = session.load(IndoorRegister.class, id);
			if(obj != null) {
				System.out.println("found object for delete");
				session.delete(obj);
				session.flush();
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(session != null)
				session.close();
		}
	}

	
	
	@SuppressWarnings("unchecked")
	public List<IndoorRegister> findByMonth(String month, String year) throws Exception {
		Session session = null;
		List<IndoorRegister> indoorRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			/*
			 * select id, serial_no, ipd_no, date_format(admit_date, '%d/%m/%Y') as admit_date, date_format(discharge_date, '%d/%m/%Y') as discharge_date,
patient_name, gender, address, age, diagnosis, treatment, fees
from indoor_register order by serial_no asc;

	private int id;
	private Long ipdNo;
	private int serialNo;
	private Date admitDate;
	private Date dischargeDate;
	private String pName;
	private String gender;
	private String pAddress;
	private int age;
	private String diagnosis;
	private String treatment;
	private String remarks;
	private double fees;
	
	.createQuery("select id, serialNo, ipdNo, date_format(admitDate, '%d/%m/%Y') as admitDate, "
							+ "date_format(dischargeDate, '%d/%m/%Y') as dischargeDate, pName, gender, pAddress, age, diagnosis, treatment, fees "
							+ "from IndoorRegister where date_format(dischargeDate, '%b') = :month and "
							+ "date_format(dischargeDate, '%Y') = :year order by dischargeDate asc");
			 */
			Query query = session
					.createQuery("from IndoorRegister where date_format(dischargeDate, '%b') = :month and "
							+ "date_format(dischargeDate, '%Y') = :year order by dischargeDate asc");
			query.setParameter("month", month);
			query.setParameter("year", year);
			indoorRegisters = query.list();	
		} catch (Exception e) {
			throw e;
		} finally {
			if(session != null)
				session.close();
		}
		
		return indoorRegisters;
	}
	
	public void update(IndoorRegister indoorRegister) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			int serialNo = 0;
			int recordCount = 0;
			Date dischargeDate = indoorRegister.getDischargeDate();
			if(dischargeDate != null) {
				Date resetDate = AppUtil.getIPDResetDate();
				
				recordCount = checkForEmpty(resetDate);
				System.out.println("number of records in indoor_register after:" + new SimpleDateFormat("dd/MM//yyyy").format(resetDate) + " are " + recordCount);
				
				if(recordCount > 0) {
					do {
						Calendar cal = Calendar.getInstance();
						cal.setTime(dischargeDate);
						serialNo = getLastSerialNo(cal.getTime());
						System.out.println("got serial no:" + serialNo + " for discharge date:" + dischargeDate);
						cal.add(Calendar.DATE, -1);
						dischargeDate = cal.getTime();
					} while(serialNo == 0 && dischargeDate.compareTo(resetDate) >=0);
					serialNo = serialNo + 1;
				} else {
					serialNo = 1;
				}
				
				System.out.println("setting serial no:" + serialNo);
				//update all serial no where dischargeDate > indoorRegister.getDischargeDate()
				updateSerialNoGreaterThanDischargeDate(indoorRegister.getDischargeDate());
				indoorRegister.setSerialNo(serialNo);
			} 

			//if(updateFlag)
			session.update(indoorRegister);
			session.getTransaction().commit();
		} catch(Exception e) {
			throw e;
		} finally {
			if(session != null)
			session.close();
		}
	}

	public void save(IndoorRegister indoorRegister) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			int serialNo = 0;
			int recordCount = 0;
			Date dischargeDate = indoorRegister.getDischargeDate();
			if(dischargeDate != null) {
				Date resetDate = AppUtil.getIPDResetDate();
				
				recordCount = checkForEmpty(resetDate);
				System.out.println("number of records in indoor_register after:" + new SimpleDateFormat("dd/MM//yyyy").format(resetDate) + " are " + recordCount);
				
				if(recordCount > 0) {
					do {
						Calendar cal = Calendar.getInstance();
						cal.setTime(dischargeDate);
						serialNo = getLastSerialNo(cal.getTime());
						System.out.println("got serial no:" + serialNo + " for discharge date:" + dischargeDate);
						cal.add(Calendar.DATE, -1);
						dischargeDate = cal.getTime();
					} while(serialNo == 0 && dischargeDate.compareTo(resetDate) >=0);
					serialNo = serialNo + 1;
				} else {
					serialNo = 1;
				}
				
				System.out.println("setting serial no:" + serialNo);
				//update all serial no where dischargeDate > indoorRegister.getDischargeDate()
				updateSerialNoGreaterThanDischargeDate(indoorRegister.getDischargeDate());
				indoorRegister.setSerialNo(serialNo);
			} 

			//if(updateFlag)
			session.save(indoorRegister);
			session.getTransaction().commit();
		} catch(Exception e) {
			throw e;
		} finally {
			if(session != null)
			session.close();
		}
	}
	
	public long generateIPDNo(Date admitDate) throws Exception {
		SimpleDateFormat ipdNoFormatter = new SimpleDateFormat("yyyyMMdd");
		Long ipdNo = getLastIPDNo(admitDate);
		if(ipdNo != null) {
			System.out.println("database returned IPD no = " + ipdNo);
			ipdNo = ipdNo + 1;
		} else if (ipdNo == null) {
			System.out.println("database returned null IPD no, generating new");
			String strIPDNo = ipdNoFormatter.format(admitDate) + "01";
			ipdNo = new Long(strIPDNo);
		}
		
		System.out.println("setting Serial No:" + ipdNo);
		return ipdNo;
	}
	
	public int checkForEmpty(Date dischargeDate) throws Exception {
		Session session = null;
		int resultCount = 0;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("select count(*) from IndoorRegister where dischargeDate >= :dischargeDate");
			query.setParameter("dischargeDate", dischargeDate);
			
			if(query.list() != null) {
				resultCount = new Long((Long)query.list().get(0)).intValue();
				
			}
		} catch (Exception e) {
			throw e;
		}
		
		return resultCount;
	}
	
	public boolean updateSerialNoGreaterThanDischargeDate(Date dischargeDate) throws Exception {
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("update IndoorRegister set serialNo = serialNo + 1 where dischargeDate > :dischargeDate");
			query.setParameter("dischargeDate", dischargeDate);
			int updateCount = query.executeUpdate();
			if(updateCount >= 0) {
				result = true;
			}
			session.getTransaction().commit();
		} catch(Exception e) {
			throw e;
		}
		return result;
	}
	
	public int getLastSerialNo(Date dischargeDate) throws Exception {
		Session session = null;
		int lastSerialNo = 0;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("select max(serialNo) from IndoorRegister where dischargeDate = STR_TO_DATE(:dischargeDate,'%d-%m-%Y')");
			query.setParameter("dischargeDate", dateFormatter.format(dischargeDate));
			
			if(query.list() != null) {
				Object obj = query.list().get(0);
				lastSerialNo = (obj != null) ? (Integer) obj : 0; 
			}
		} catch(Exception e) {
			throw e;
		} finally {
			if(session != null)
			session.close();
		}
		
		return lastSerialNo;
	}

	public Long getLastIPDNo(Date admitDate) throws Exception{
		Session session = null;
		Long lastIPDNo = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("select max(ipdNo) from IndoorRegister where admitDate = STR_TO_DATE(:admitDate,'%d-%m-%Y')");
			query.setParameter("admitDate", dateFormatter.format(admitDate));
			
			lastIPDNo = (Long) query.list().get(0);	
		} catch(Exception e) {
			throw e;
		} finally {
			if(session != null)
			session.close();
		}

		return lastIPDNo;
	}
	
	class HeaderFooterPageEvent extends PdfPageEventHelper {
	    public void onStartPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Left"), rect.getLeft(), rect.getTop(), 0);
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);
	    }
	    public void onEndPage(PdfWriter writer,Document document) {
	    	Rectangle rect = writer.getBoxSize("art");
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Left"), rect.getLeft(), rect.getBottom(), 0);
	        ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Right"), rect.getRight(), rect.getBottom(), 0);
	    }
	} 
	
	public void writeToPDF(String fileName, List<IndoorRegisterDTO> indoorRegisters, String month, String year) {
		try {
			Document doc = new Document(PageSize.LETTER.rotate());
			FileOutputStream file = new FileOutputStream(AppConstants.DOWNLOAD_FILE_PATH + fileName);
			PdfWriter writer = PdfWriter.getInstance(doc, file);
			Rectangle rect = new Rectangle(50, 30, 550, 800);
	        writer.setBoxSize("art", rect);
	        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
	        writer.setPageEvent(event);
	        
			doc.open();
			doc.add(new Paragraph("Indoor Register Report - " + month + " " + year));
	
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 0.6f, 0.8f, 2.1f, 1.0f, 1.0f, 0.5f });
			table.setSpacingBefore(12);
			table.setHeaderRows(1);
	
			// define font for table header row
			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			headerFont.setSize(11);
			headerFont.setColor(BaseColor.BLACK);
			
			Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA);
			bodyFont.setSize(11);
			bodyFont.setColor(BaseColor.BLACK);
			
			// define table header cell
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setPadding(5);
	
			// write table header
			cell.setPhrase(new Phrase("IPD No", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Dates", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Name & Address", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Diagnosis", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Treatment", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Fees", headerFont));
			table.addCell(cell);

			// write table row data
			for (IndoorRegisterDTO ir : indoorRegisters) {
				String treatment = ir.getTreatment(); 
				Date today = new Date();
				Calendar Jan2016_01 = Calendar.getInstance();
				Jan2016_01.set(2016, 0, 1);
				if(treatment.equalsIgnoreCase("MTP + Tubectomy") || treatment.equalsIgnoreCase("MTP")) {
					PdfPCell cellIpdNo = new PdfPCell(new Phrase(""+ir.getIpdNo(), bodyFont)); 
					cellIpdNo.setRowspan(2);
					table.addCell(cellIpdNo);
					
					PdfPCell cellDOA = new PdfPCell(new Phrase("DOA: " + ir.getAdmitDate(), bodyFont));
					table.addCell(cellDOA);
					
					if(today.before(Jan2016_01.getTime())) {
						PdfPCell cellSerialNo = new PdfPCell(new Phrase("", bodyFont));
						cellSerialNo.setRowspan(2);
						table.addCell(cellSerialNo);
					} else {
						String mtpSerialNo = ""+ir.getMtpRegister().getMtpSerialNo();
						PdfPCell cellSerialNo = new PdfPCell(new Phrase(mtpSerialNo, bodyFont));
						cellSerialNo.setRowspan(2);
						table.addCell(cellSerialNo);
					}
					
					PdfPCell cellDiagnosis = new PdfPCell(new Phrase(ir.getDiagnosis(), bodyFont));
					cellDiagnosis.setRowspan(2);
					table.addCell(cellDiagnosis);
					
					PdfPCell cellTreatment = new PdfPCell(new Phrase(ir.getTreatment(), bodyFont));
					cellTreatment.setRowspan(2);
					table.addCell(cellTreatment);
					
					PdfPCell cellFees = new PdfPCell(new Phrase("" + ir.getFees(), bodyFont));
					cellFees.setRowspan(2);
					table.addCell(cellFees);
					
					PdfPCell cellDOD = new PdfPCell(new Phrase("DOD: " + ir.getDischargeDate(), bodyFont));
					table.addCell(cellDOD);
					
				} else {
					PdfPCell cellIpdNo = new PdfPCell(new Phrase(""+ir.getIpdNo(), bodyFont)); 
					cellIpdNo.setRowspan(2);
					table.addCell(cellIpdNo);
					
					PdfPCell cellDOA = new PdfPCell(new Phrase("DOA: " + ir.getAdmitDate(), bodyFont));
					table.addCell(cellDOA);
					
					String name = ir.getpName();
					PdfPCell cellName = new PdfPCell(new Phrase(name, bodyFont));
					table.addCell(cellName);
					
					PdfPCell cellDiagnosis = new PdfPCell(new Phrase(ir.getDiagnosis(), bodyFont));
					cellDiagnosis.setRowspan(2);
					table.addCell(cellDiagnosis);
					
					PdfPCell cellTreatment = new PdfPCell(new Phrase(ir.getTreatment(), bodyFont));
					cellTreatment.setRowspan(2);
					table.addCell(cellTreatment);
					
					PdfPCell cellFees = new PdfPCell(new Phrase("" + ir.getFees(), bodyFont));
					cellFees.setRowspan(2);
					table.addCell(cellFees);
					
					PdfPCell cellDOD = new PdfPCell(new Phrase("DOD: " + ir.getDischargeDate(), bodyFont));
					table.addCell(cellDOD);
					
					String address = ir.getpAddress();
					String gender = ir.getGender() + "/" + ir.getAge();
					PdfPCell cellAddress = new PdfPCell(new Phrase(address	+ "          " + gender, bodyFont));
					table.addCell(cellAddress);
				}
			}
			doc.add(table);
			doc.close();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}