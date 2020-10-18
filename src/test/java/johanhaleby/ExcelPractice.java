package johanhaleby;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelPractice 
{
	public static void main(String[] args) throws Exception
	{
		File f=new File("excelpractice.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("testdata");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
		//Give headings to results in excel file
		sh.getRow(0).createCell(nouc).setCellValue("occupation");
		sh.getRow(0).createCell(nouc+1).setCellValue("best friend");
		sh.getRow(0).createCell(nouc+2).setCellValue("designation");
		sh.getRow(0).createCell(nouc+3).setCellValue("accomplishment");
		//Data Driven from 2nd row(index=1)
		for(int i=1;i<nour;i++)
		{
			//Read data from excel
			DataFormatter df=new DataFormatter();
			String x=df.formatCellValue(sh.getRow(i).getCell(0));
			String y=df.formatCellValue(sh.getRow(i).getCell(1));
			String z=df.formatCellValue(sh.getRow(i).getCell(2));
			String w=df.formatCellValue(sh.getRow(i).getCell(3));
			
			//Project oriented work with data read from excel
			
			//Write result back to excel
			sh.getRow(i).createCell(nouc).setCellValue("Yes");
			sh.getRow(i).createCell(nouc+1).setCellValue("Avnu");
			sh.getRow(i).createCell(nouc+2).setCellValue("SDET");
			sh.getRow(i).createCell(nouc+3).setCellValue("Done");	
		}
		
		sh.autoSizeColumn(nouc);
		sh.autoSizeColumn(nouc+1);
		sh.autoSizeColumn(nouc+2);
		sh.autoSizeColumn(nouc+3);
		
		//Save data back to excel
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fi.close();
		fo.close();
		wb.close();
	}
}
