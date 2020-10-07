package johanhaleby;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExcelPostbookProject
{
	public static void main(String[] args) throws Exception
	{
		//Read data from excel file
		File f=new File("postbook.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
		sh.getRow(0).createCell(nouc).setCellValue("UserID");
		sh.getRow(0).createCell(nouc+1).setCellValue("Title");
		sh.getRow(0).createCell(nouc+2).setCellValue("Body");
		//Data Driven
		for(int i=1;i<nour;i++)
		{
			DataFormatter df=new DataFormatter();
			String uri=df.formatCellValue(sh.getRow(i).getCell(0));
			String id=df.formatCellValue(sh.getRow(i).getCell(1));
			//Register end-point and Create HTTP Request
			RestAssured.baseURI=uri;
			RequestSpecification req=RestAssured.given();
			//Send HTTP Request with one path parameter value
			Response res=req.request(Method.GET,"/"+id);
			JsonPath jp=res.jsonPath();
			String x=jp.getString("userId");
			String y=jp.getString("title");
			String z=jp.getString("body");
			sh.getRow(i).createCell(nouc).setCellValue(x);
			sh.getRow(i).createCell(nouc+1).setCellValue(y);
			sh.getRow(i).createCell(nouc+2).setCellValue(z);
		}
		sh.autoSizeColumn(nouc);
		sh.autoSizeColumn(nouc+1);
		sh.autoSizeColumn(nouc+2);
		//Save excel file in HDD
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fi.close();
		fo.close();
		wb.close();	
	}
}
