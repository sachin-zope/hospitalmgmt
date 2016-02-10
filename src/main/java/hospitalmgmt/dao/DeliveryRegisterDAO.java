package hospitalmgmt.dao;

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

import hospitalmgmt.beans.DeliveryRegister;
import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.transformer.DeliveryRegisterDTO;
import hospitalmgmt.transformer.IndoorRegisterDTO;
import hospitalmgmt.utils.AppConstants;
import hospitalmgmt.utils.HibernateUtil;

public class DeliveryRegisterDAO {
	
	@SuppressWarnings("unchecked")
	public List<DeliveryRegister> getAll() {
		
		Session session = null;
		List<DeliveryRegister> deliveryRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from DeliveryRegister");
			deliveryRegisters = query.list();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return deliveryRegisters;
	}
	
	public DeliveryRegister findById(int deliveryRegisterId) {
		Session session = null;
		DeliveryRegister deliveryRegister = null; 
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			deliveryRegister = (DeliveryRegister) session.load(DeliveryRegister.class, deliveryRegisterId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) 
				session.close();
		}
		
		return deliveryRegister;
	}
	
	/*@SuppressWarnings("unchecked")
	public List<DeliveryRegister> findByMonth(String month, String year) {
		Session session = null;
		List<DeliveryRegister> deliveryRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from DeliveryRegister where date_format(deliveryDate, '%b') = :month and date_format(deliveryDate, '%Y') = :year order by deliveryDate asc");
			query.setParameter("month", month);
			query.setParameter("year", year);

			deliveryRegisters = query.list();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return deliveryRegisters;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<IndoorRegister> findByMonth(String month, String year) {
		Session session = null;
		List<IndoorRegister> indoorRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from IndoorRegister ir where date_format(ir.deliveryRegister.deliveryDate, '%b') = :month and date_format(ir.deliveryRegister.deliveryDate, '%Y') = :year order by ir.deliveryRegister.deliveryDate asc");
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
	

	public void save(DeliveryRegister deliveryRegister) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(deliveryRegister);
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
			session.close();
		}
	}
	
	public void delete(DeliveryRegister deliveryRegister) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(deliveryRegister);
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
			session.close();
		}
	}
	
	public long generateSerialNo(Date dischargeDate) throws Exception {
		SimpleDateFormat serialNoFormatter = new SimpleDateFormat("yyyyMMdd");
		Long serialNo = getLastSerialNo(dischargeDate);
		if(serialNo != null) {
			System.out.println("database returned serial no = " + serialNo);
			serialNo = serialNo + 1;
		} else if (serialNo == null) {
			System.out.println("database returned null serial no, generating new");
			String strSerialNo = serialNoFormatter.format(dischargeDate) + "01";
			serialNo = new Long(strSerialNo);
		}
		
		System.out.println("setting Serial No:" + serialNo);
		return serialNo;
	}
	
	private Long getLastSerialNo(Date deliveryDate) throws Exception{
		Session session = null;
		Long lastSerialNo = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("select max(serialNo) from DeliveryRegister where deliveryDate = STR_TO_DATE(:deliveryDate,'%d-%m-%Y')");
			query.setParameter("deliveryDate", dateFormatter.format(deliveryDate));
			
			lastSerialNo = (Long) query.list().get(0);	
		} catch(Exception e) {
			throw e;
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
	
			doc.add(new Paragraph("Delivery Register Report - " + month + " " + year));
	
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 0.7f, 2.1f, 1.0f, 1.0f, 1.0f, 1.0f });
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
	
			cell.setPhrase(new Phrase("Name & Address", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Child Details", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Diagnosis", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Delivery Type", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Indication", headerFont));
			table.addCell(cell);

			// write table row data
			for (IndoorRegisterDTO ir : indoorRegisters) {
				DeliveryRegisterDTO deliveryRegisterDTO = ir.getDeliveryRegister();
				
				PdfPCell cellSerialNo = new PdfPCell(new Phrase(""+deliveryRegisterDTO.getSerialNo(), bodyFont)); 
				cellSerialNo.setRowspan(2);
				table.addCell(cellSerialNo);
				
				String name = ir.getpName();
				PdfPCell cellName = new PdfPCell(new Phrase(name, bodyFont));
				table.addCell(cellName);
				
				//Child Details
				String dob = "DOB: " + deliveryRegisterDTO.getDeliveryDate();
				String birthTime = "Time: " +deliveryRegisterDTO.getBirthTime();
				
				PdfPCell cellChildDetails = new PdfPCell(new Phrase(dob +"\n" + birthTime , bodyFont));
				table.addCell(cellChildDetails);
				
				PdfPCell cellDiagnosis = new PdfPCell(new Phrase(ir.getDiagnosis(), bodyFont));
				cellDiagnosis.setRowspan(2);
				table.addCell(cellDiagnosis);
				
				PdfPCell cellDeliveryType = new PdfPCell(new Phrase("" + deliveryRegisterDTO.getDeliveryType(), bodyFont));
				cellDeliveryType.setRowspan(2);
				table.addCell(cellDeliveryType);
				
				PdfPCell cellIndication = new PdfPCell(new Phrase(deliveryRegisterDTO.getIndication(), bodyFont));
				cellIndication.setRowspan(2);
				table.addCell(cellIndication);
				
				String address = ir.getpAddress();
				String gender = ir.getGender() + "/" + ir.getAge();
				PdfPCell cellAddress = new PdfPCell(new Phrase(address	+ "          " + gender, bodyFont));
				table.addCell(cellAddress);
				
				String birthWeight = "Weight : " + deliveryRegisterDTO.getBirthWeight();
				String sex = "Sex : " + deliveryRegisterDTO.getSexOfChild();
				PdfPCell cellChildDetails1 = new PdfPCell(new Phrase(birthWeight + "\n" + sex, bodyFont));
				table.addCell(cellChildDetails1);
				/*
				PdfPCell cellWeight = new PdfPCell(new Phrase(birthWeight, bodyFont));
				table.addCell(cellWeight);
				
				PdfPCell cellSex = new PdfPCell(new Phrase(sex, bodyFont));
				table.addCell(cellSex);*/
			}
			doc.add(table);
			doc.close();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}
