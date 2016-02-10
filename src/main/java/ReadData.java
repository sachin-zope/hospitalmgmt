
import hospitalmgmt.beans.IndoorRegister;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

/**
 * 
 * <dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi-ooxml</artifactId>
			<version>3.13</version>
		</dependency>
 *
 */
public class ReadData {

	public static void main(String[] args) throws Exception {
		String excelFilePath = "/media/gooru/data/personal/Delivery_Register.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        List<IndoorRegister> indoorRegisters = new ArrayList<IndoorRegister>();
        
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
         
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            IndoorRegister indoorRegister = new IndoorRegister();
            int rowNum = nextRow.getRowNum();
            System.out.print("Row: " + rowNum + " == ");
            if(rowNum == 0) {
            	System.out.println();
            	continue;
            }
            
            Cell cell0 = nextRow.getCell(0);
            indoorRegister.setAdmitDate(cell0.getDateCellValue());
            indoorRegister.setDischargeDate(nextRow.getCell(1).getDateCellValue());
            indoorRegister.setpName(nextRow.getCell(2).getStringCellValue());
            indoorRegister.setGender(nextRow.getCell(3).getStringCellValue());
            indoorRegister.setpAddress(nextRow.getCell(4).getStringCellValue());
            
            indoorRegister.setAge(new Double(nextRow.getCell(5).getNumericCellValue()).intValue());
            indoorRegister.setDiagnosis(nextRow.getCell(6).getStringCellValue());
            indoorRegister.setTreatment(nextRow.getCell(7).getStringCellValue());
            
            indoorRegister.setFees(nextRow.getCell(17).getNumericCellValue());
            indoorRegisters.add(indoorRegister);
            System.out.println(indoorRegister.toString());

            System.out.println();
        }
        
        //Writing PDF
        Document doc = new Document(PageSize.LETTER.rotate());
        PdfWriter.getInstance(doc, new FileOutputStream("/media/gooru/data/personal/1.pdf"));
        doc.open();
        
        doc.add(new Paragraph("Indoor Register Report"));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {0.7f,0.6f, 0.6f, 2.0f, 1.0f, 1.0f, 0.5f});
        table.setSpacingBefore(10);
        
        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);
         
        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(5);
         
        // write table header
        cell.setPhrase(new Phrase("IPD No", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Admit Date", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Discharge Date", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("Name & Address", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Diagnosis", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Treatment", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Fees", font));
        table.addCell(cell);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        int cnt = 1;
        // write table row data
        for (IndoorRegister ir : indoorRegisters) {
        	table.addCell(new PdfPCell(new Phrase(sdf1.format(ir.getAdmitDate()) + cnt++)));
            table.addCell(new PdfPCell(new Phrase(sdf.format(ir.getAdmitDate()))));
            table.addCell(new PdfPCell(new Phrase(sdf.format(ir.getDischargeDate()))));
            String name = ir.getpName();
            String address = ir.getpAddress();
            String gender = ir.getGender() + "/" + ir.getAge();
            table.addCell(new PdfPCell(new Phrase(name + "\n" + address + "          " + gender)));
            table.addCell(new PdfPCell(new Phrase(ir.getDiagnosis())));
            table.addCell(new PdfPCell(new Phrase(ir.getTreatment())));
            table.addCell(new PdfPCell(new Phrase(""+ir.getFees())));
        }
         
        doc.add(table);
        doc.close();
        
        workbook.close();
        inputStream.close();
	}

}
