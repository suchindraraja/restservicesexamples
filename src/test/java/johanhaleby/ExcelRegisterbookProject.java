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
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExcelRegisterbookProject
{
	public static void main(String[] args) throws Exception
	{
		//Read data from excel file
		File f=new File("registerbook.xlsx");
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
		for(int i=1;i<nour;i++)
		{
			DataFormatter df=new DataFormatter();
			String uid=df.formatCellValue(sh.getRow(i).getCell(0));
			String id=df.formatCellValue(sh.getRow(i).getCell(1));
			String title=df.formatCellValue(sh.getRow(i).getCell(2));
			String body=df.formatCellValue(sh.getRow(i).getCell(3));
			//Register end-point and Create HTTP Request
			RestAssured.baseURI="http://jsonplaceholder.typicode.com/posts";
			RequestSpecification req=RestAssured.given();
			JSONObject jo=new JSONObject();
			jo.put("userId",uid);
			jo.put("id",id);
			jo.put("title",title);
			jo.put("body",body);
			req.body(jo.toString());
			//Send HTTP Request with one path parameter value
			Response res=req.request(Method.POST,"");
			//Analyze Response
			int scode=res.getStatusCode();
			String ctype=res.getHeader("Content-Type");
			String resbody=res.getBody().asString();
			sh.getRow(i).createCell(nouc).setCellValue(scode);
			sh.getRow(i).createCell(nouc+1).setCellValue(ctype);
			sh.getRow(i).createCell(nouc+2).setCellValue(resbody);
		}
		sh.autoSizeColumn(nouc);
		sh.autoSizeColumn(nouc+1);
		sh.autoSizeColumn(nouc+2);
		//Save excel file in HDD
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		wb.close();
	}
}
