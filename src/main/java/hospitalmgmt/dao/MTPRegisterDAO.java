package hospitalmgmt.dao;

import hospitalmgmt.beans.IndoorRegister;
import hospitalmgmt.beans.MTPRegister;
import hospitalmgmt.transformer.IndoorMTPDTO;
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
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MTPRegisterDAO {
	
	@SuppressWarnings("unchecked")
	public List<MTPRegister> getAll() {
		
		Session session = null;
		List<MTPRegister> mtpRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from MTPRegister");
			mtpRegisters = query.list();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
		
		return mtpRegisters;
	}
	
	public MTPRegister findById(int mtpRegisterId) {
		Session session = null;
		MTPRegister mtpRegister = null; 
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			mtpRegister = (MTPRegister) session.load(MTPRegister.class, mtpRegisterId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) 
				session.close();
		}
		
		return mtpRegister;
	}
	
	@SuppressWarnings("unchecked")
	public List<IndoorRegister> findByMonth(String month, String year) {
		Session session = null;
		List<IndoorRegister> indoorRegisters = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from IndoorRegister ir where date_format(ir.mtpRegister.operationDate, '%b') = :month and date_format(ir.mtpRegister.operationDate, '%Y') = :year order by ir.mtpRegister.operationDate asc");
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

	public void save(MTPRegister mtpRegister) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(mtpRegister);
			session.getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null)
			session.close();
		}
	}
	
	public Integer generateSerialNo(Date operationDate) throws Exception {
		Integer serialNo = 0;
		//this date will be used while updating serialno
		Date originalOperationDate = operationDate;
		
		if(operationDate != null) {
			Date resetDate = AppUtil.getMTPResetDate(operationDate);
			Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
			boolean doUpdate = false;
			
			//Check how many record exists for current year.
			int recordCount = checkForEmpty(currentYear);
			System.out.println("number of records in mtp_register after:" + new SimpleDateFormat("dd/MM/yyyy").format(resetDate) + " are " + recordCount);
			if(recordCount > 0) {
				Calendar newOperationDate = Calendar.getInstance();
				do {
					newOperationDate.setTime(operationDate);
					serialNo = getLastSerialNo(newOperationDate.getTime());
					System.out.println("got serial no:" + serialNo + " for operation date:" + operationDate);
					newOperationDate.add(Calendar.DATE, -1);
					operationDate = newOperationDate.getTime();
				} while(serialNo == 0 && operationDate.compareTo(resetDate) >= 0);
				
				//if serialNo is still zero, operation date is earlier than all present in table
				//so setting it to CURRENT_YEAR + 001
				if(serialNo == 0) {
					serialNo = new Integer(currentYear.toString() + "001");
				} else {
					serialNo = serialNo + 1;
				}
				doUpdate = true;				
				
				//if recordCount is zero then no record present in table
				//so set it to CURRENT_YEAR + 001
			} else if(recordCount == 0) {
				serialNo = new Integer(currentYear.toString() + "001");
			}
			
			System.out.println("generated serial no:" + serialNo);
			if(doUpdate) {
				updateSerialNoGreaterThanOperationDate(originalOperationDate);
				System.out.println("updated all serialNo which is having operation date greater than :" + operationDate);
			}
		}
		//if still serialNo is zero, then something went wrong. Check can be placed while calling this method.
		return serialNo;
	}
	
	private Integer getLastSerialNo(Date operationDate) throws Exception{
		Session session = null;
		Integer lastSerialNo = 0;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("select max(mtpSerialNo) from MTPRegister where operationDate = STR_TO_DATE(:operationDate,'%d-%m-%Y')");
			query.setParameter("operationDate", dateFormatter.format(operationDate));
			lastSerialNo = (query.list().get(0) != null) ? (Integer)query.list().get(0) : 0;
		} catch(Exception e) {
			throw e;
		} finally {
			if(session != null)
			session.close();
		}

		return lastSerialNo;
	}
	
	public int checkForEmpty(int currentYear) {
		Session session = null;
		int resultCount = 0;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("select count(*) from MTPRegister where date_format(operationDate, '%Y') = :year");
			query.setParameter("year", "" + currentYear);
			if(query.list() != null) {
				resultCount = new Long((Long)query.list().get(0)).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultCount;
	}
	
	private boolean updateSerialNoGreaterThanOperationDate(Date operationDate) throws Exception {
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("update MTPRegister set mtpSerialNo = mtpSerialNo + 1 where operationDate > :operationDate");
			query.setParameter("operationDate", operationDate);
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
	
	public void writeToPDF(String fileName, List<IndoorMTPDTO> indoorRegisters, String month, String year) {
		try {
			Document doc = new Document(PageSize.LETTER.rotate());
			FileOutputStream file = new FileOutputStream(AppConstants.DOWNLOAD_FILE_PATH + fileName);
			PdfWriter.getInstance(doc, file);
			doc.open();
	
			doc.add(new Paragraph("MTP Register Report - " + month + " " + year));
	
			PdfPTable table = new PdfPTable(9);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] { 0.6f, 0.5f, 0.7f, 2.1f, 0.5f, 1.0f, 1.0f, 1.0f, 0.5f });
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
			cell.setPhrase(new Phrase("Yearly No", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Monthly No", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("DOT", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Name & Address", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Duration of Pregnancy", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Indication", headerFont));
			table.addCell(cell);
	
			cell.setPhrase(new Phrase("Opinion given by", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("MTP done by", headerFont));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Total Childrens", headerFont));
			table.addCell(cell);

			// write table row data
			for (IndoorMTPDTO ir : indoorRegisters) {
				
				PdfPCell cellYearlyNo = new PdfPCell(new Phrase("" + ir.getMtpSerialNo(), bodyFont)); 
				cellYearlyNo.setRowspan(2);
				table.addCell(cellYearlyNo);
				
				PdfPCell cellMonthlyNo = new PdfPCell(new Phrase("" + ir.getMtpMonthlySerialNo(), bodyFont)); 
				cellMonthlyNo.setRowspan(2);
				table.addCell(cellMonthlyNo);

				PdfPCell cellDOT = new PdfPCell(new Phrase(ir.getOperationDate(), bodyFont)); 
				cellDOT.setRowspan(2);
				table.addCell(cellDOT);
				
				String name = ir.getpName();
				PdfPCell cellName = new PdfPCell(new Phrase(name, bodyFont));
				table.addCell(cellName);
				
				PdfPCell cellDurationOfP = new PdfPCell(new Phrase("" + ir.getDurationOfPregnancy() + " W" , bodyFont));
				cellDurationOfP.setRowspan(2);
				table.addCell(cellDurationOfP);
				
				StringBuffer indication = new StringBuffer();
				indication.append("Indication: ");
				indication.append(ir.getMindication());
				indication.append("\nMethod: ");
				indication.append(ir.getProcedure());
				PdfPCell cellIndication = new PdfPCell(new Phrase(indication.toString(), bodyFont));
				table.addCell(cellIndication);
				
				PdfPCell cellOpinion = new PdfPCell(new Phrase(ir.getOpinionGivenBy(), bodyFont));
				cellOpinion.setRowspan(2);
				table.addCell(cellOpinion);
			
				PdfPCell cellDoneBy = new PdfPCell(new Phrase(ir.getDoneByDr(), bodyFont));
				cellDoneBy.setRowspan(2);
				table.addCell(cellDoneBy);
				
				StringBuffer totalChildrens = new StringBuffer();
				totalChildrens.append("M: ");
				totalChildrens.append(ir.getmChildrens());
				totalChildrens.append("\nF: ");
				totalChildrens.append(ir.getfChildrens());
				PdfPCell cellTotalChildrens = new PdfPCell(new Phrase(totalChildrens.toString(), bodyFont));
				cellTotalChildrens.setRowspan(2);
				table.addCell(cellTotalChildrens);
				
				String address = ir.getpAddress();
				String gender = ir.getGender() + "/" + ir.getAge();
				PdfPCell cellAddress = new PdfPCell(new Phrase(address	+ "          " + gender + "\n" + ir.getMarried(), bodyFont));
				table.addCell(cellAddress);
				
				StringBuffer indication1 = new StringBuffer();
				indication1.append("With: ");
				indication1.append(ir.getAlongWith());
				if(ir.getBatchNo() != null && ir.getBatchNo().length() > 0) {
					indication1.append("\nBatch No: ");
					indication1.append(ir.getBatchNo());
				}
				PdfPCell cellIndication1 = new PdfPCell(new Phrase(indication1.toString(), bodyFont));
				table.addCell(cellIndication1);
				
			}
			doc.add(table);
			doc.close();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}
