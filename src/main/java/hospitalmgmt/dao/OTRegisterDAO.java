package hospitalmgmt.dao;

import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.beans.OTRegister;
import hospitalmgmt.transformer.IndoorRegisterDTO;
import hospitalmgmt.transformer.OTRegisterDTO;
import hospitalmgmt.utils.AppConstants;
import hospitalmgmt.utils.HibernateUtil;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class OTRegisterDAO {
	
	@SuppressWarnings("unchecked")
	public List<OTRegister> getAll() {
		
		Session session = null;
		List<OTRegister> otRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from OTRegister");
			otRegisters = query.list();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return otRegisters;
	}
	
	public OTRegister findById(int otRegisterId) {
		Session session = null;
		OTRegister otRegister = null; 
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from OTRegister where id = :otRegisterId");
			query.setParameter("otRegisterId", otRegisterId);
			
			otRegister = (OTRegister) query.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) 
				session.close();
		}
		
		return otRegister;
	}
	
	@SuppressWarnings("unchecked")
	public List<IndoorRegister> findByMonth(String month, String year) {
		Session session = null;
		List<IndoorRegister> indoorRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from IndoorRegister ir where date_format(ir.otRegister.operationDate, '%b') = :month and date_format(ir.otRegister.operationDate, '%Y') = :year order by ir.otRegister.operationDate asc");
			query.setParameter("month", month);
			query.setParameter("year", year);

			indoorRegisters = query.list();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return indoorRegisters;
	}

	public void save(OTRegister otRegister) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(otRegister);
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
			session.close();
		}
	}
	
	public long generateSerialNo(Date operationDate) throws Exception {
		SimpleDateFormat serialNoFormatter = new SimpleDateFormat("yyyyMMdd");
		Long serialNo = getLastSerialNo(operationDate);
		if(serialNo != null) {
			System.out.println("database returned serial no = " + serialNo);
			serialNo = serialNo + 1;
		} else if (serialNo == null) {
			System.out.println("database returned null serial no, generating new");
			String strSerialNo = serialNoFormatter.format(operationDate) + "01";
			serialNo = new Long(strSerialNo);
		}
		
		System.out.println("setting Serial No in OT Register:" + serialNo);
		return serialNo;
	}
	
	private Long getLastSerialNo(Date operationDate) {
		Session session = null;
		Long lastSerialNo = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("select max(serialNo) from OTRegister where operationDate = STR_TO_DATE(:operationDate,'%d-%m-%Y')");
			query.setParameter("operationDate", dateFormatter.format(operationDate));
			
			lastSerialNo = (Long) query.list().get(0);	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
			session.close();
		}

		return lastSerialNo;
	}
	
	public void writeToPDF(String fileName, List<IndoorRegisterDTO> indoorRegisters, String month, String year) {
		try {
			Document doc = new Document(PageSize.LETTER.rotate());
			FileOutputStream file = new FileOutputStream(AppConstants.DOWNLOAD_FILE_PATH + fileName);
			PdfWriter.getInstance(doc, file);
			doc.open();
	
			doc.add(new Paragraph("OT Register Report - " + month + " " + year));
	
			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 0.7f, 0.7f, 2.1f, 1.0f, 1.0f, 1.0f, 1.0f });
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
			cell.setPhrase(new Phrase("Serial No", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("DOS", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Name & Address", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Diagnosis", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Operating Surgon", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Anaesthetist", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Assisting Nurse/s", headerFont));
			table.addCell(cell);

			// write table row data
			for (IndoorRegisterDTO ir : indoorRegisters) {
				OTRegisterDTO otRegisterDTO = ir.getOtRegister();
				
				PdfPCell cellSerialNo = new PdfPCell(new Phrase(""+otRegisterDTO.getSerialNo(), bodyFont)); 
				cellSerialNo.setRowspan(2);
				table.addCell(cellSerialNo);
				
				PdfPCell cellDOP = new PdfPCell(new Phrase(""+otRegisterDTO.getOperationDate(), bodyFont)); 
				cellDOP.setRowspan(2);
				table.addCell(cellDOP);
				
				String name = ir.getpName();
				PdfPCell cellName = new PdfPCell(new Phrase(name, bodyFont));
				table.addCell(cellName);
								
				PdfPCell cellDiagnosis = new PdfPCell(new Phrase(ir.getDiagnosis(), bodyFont));
				cellDiagnosis.setRowspan(2);
				table.addCell(cellDiagnosis);
				
				PdfPCell cellOperatingSurgon = new PdfPCell(new Phrase(otRegisterDTO.getNameOfSurgeon(), bodyFont));
				cellOperatingSurgon.setRowspan(2);
				table.addCell(cellOperatingSurgon);
				
				PdfPCell cellAnaesthetist = new PdfPCell(new Phrase(otRegisterDTO.getAnaesthetist(), bodyFont));
				cellAnaesthetist.setRowspan(2);
				table.addCell(cellAnaesthetist);

				PdfPCell cellAssistant = new PdfPCell(new Phrase(otRegisterDTO.getAssistant(), bodyFont));
				cellAssistant.setRowspan(2);
				table.addCell(cellAssistant);
				
				String address = ir.getpAddress();
				String gender = ir.getGender() + "/" + ir.getAge();
				PdfPCell cellAddress = new PdfPCell(new Phrase(address	+ "          " + gender, bodyFont));
				table.addCell(cellAddress);
				
			}
			doc.add(table);
			doc.close();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
}
