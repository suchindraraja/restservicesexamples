package johanhaleby;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExcelCountrybookProject
{
	public static void main(String[] args) throws Exception
	{
		//Read data from excel file
		File f=new File("countrybook.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		String cap="Capital on "+sf.format(dt);
		sh.getRow(0).createCell(nouc).setCellValue(cap);
		String population="Population on "+sf.format(dt);
		sh.getRow(0).createCell(nouc+1).setCellValue(population);
		String jenda="Jenda on "+sf.format(dt);
		sh.getRow(0).createCell(nouc+2).setCellValue(jenda);
		for(int i=1;i<nour;i++)
		{
			DataFormatter df=new DataFormatter();
			String uri=df.formatCellValue(sh.getRow(i).getCell(0));
			String country=df.formatCellValue(sh.getRow(i).getCell(1));
			String fulltext=df.formatCellValue(sh.getRow(i).getCell(2));
			//Register end-point and Create HTTP Request
			RestAssured.baseURI=uri;
			RequestSpecification req=RestAssured.given();
			//Send HTTP Request with one path parameter value
			Response res=req.queryParam("fullText",fulltext).request(Method.GET,"/"+country);
			JsonPath jp=res.jsonPath();
			List<String> capital=jp.getList("capital");
			List<Integer> pop=jp.getList("population");
			List<String> flag=jp.getList("flag");
			sh.getRow(i).createCell(nouc).setCellValue(capital.get(0));
			sh.getRow(i).createCell(nouc+1).setCellValue(pop.get(0));
			sh.getRow(i).createCell(nouc+2).setCellValue(flag.get(0));
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
