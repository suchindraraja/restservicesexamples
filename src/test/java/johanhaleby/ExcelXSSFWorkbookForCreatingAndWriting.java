package johanhaleby;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelXSSFWorkbookForCreatingAndWriting
{
	public static void main(String[] args) throws Exception
	{
		//Create an .xlsx excel file
		File f=new File("xssfxlsx.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook();
		Sheet sh=wb.createSheet("Sheet1");
		Row r=sh.createRow(0);
		Cell c=r.createCell(0);
		c.setCellValue("Hopefully we will finish Restful services testing today");
		sh.autoSizeColumn(0);
		//Save .xlsx file in HDD
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		wb.close();
	}
}
