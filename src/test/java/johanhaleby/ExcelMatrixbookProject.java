package johanhaleby;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExcelMatrixbookProject 
{
	public static void main(String[] args) throws Exception
	{
		//Read data from excel file
		File f=new File("matrixbook.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		String sc="Status code on "+sf.format(dt);
		sh.getRow(0).createCell(nouc).setCellValue(sc);
		String ct="Content type on "+sf.format(dt);
		sh.getRow(0).createCell(nouc+1).setCellValue(ct);
		String rbody="Response body on "+sf.format(dt);
		sh.getRow(0).createCell(nouc+2).setCellValue(rbody);
		//Data Driven
		for(int i=1;i<nour;i++)
		{
			DataFormatter df=new DataFormatter();
			String mp=df.formatCellValue(sh.getRow(i).getCell(0));
			String offset=df.formatCellValue(sh.getRow(i).getCell(1));
			String pagesize=df.formatCellValue(sh.getRow(i).getCell(2));
			//Register end-point and Create HTTP Request
			RestAssured.urlEncodingEnabled=false;
			RestAssured.baseURI="https://petstore.swagger.io/v2/pet/findByStatus;matrixParm="+mp;
			RequestSpecification req=RestAssured.given();
			//Send HTTP Request with one path parameter value
			Response res=req.queryParam("offset",offset).queryParam("pageSize",pagesize).request(Method.GET,"");
			sh.getRow(i).createCell(nouc).setCellValue(res.getStatusCode());
			sh.getRow(i).createCell(nouc+1).setCellValue(res.getHeader("Content-Type"));
			sh.getRow(i).createCell(nouc+2).setCellValue(res.getBody().asString());
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
