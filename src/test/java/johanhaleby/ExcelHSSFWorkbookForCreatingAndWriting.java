package johanhaleby;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelHSSFWorkbookForCreatingAndWriting
{
	public static void main(String[] args) throws Exception
	{
		//Create an .xls excel file
		File f=new File("hssfxls.xls");
		HSSFWorkbook wb=new HSSFWorkbook();
		Sheet sh=wb.createSheet("Sheet1");
		Row r=sh.createRow(0);
		Cell c=r.createCell(0);
		c.setCellValue("We will try to finish SOAP services testing by tomorrow");
		sh.autoSizeColumn(0);
		//Save .xls file in HDD
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		wb.close();
	}
}
