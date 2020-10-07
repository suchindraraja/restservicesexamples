package johanhaleby;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExcelEmployeeRecordsProject
{
	public static void main(String[] args) throws Exception
	{
		//Create a .xlsx file
		File f=new File("employeerecords.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook();
		Sheet sh=wb.createSheet("Sheet1");
		Row r=sh.createRow(0);
		r.createCell(0).setCellValue("ID");
		r.createCell(1).setCellValue("Employee Name");
		r.createCell(2).setCellValue("Employee Salary");
		r.createCell(3).setCellValue("Employee Age");
		r.createCell(4).setCellValue("Profile Image");
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1/employees";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.request(Method.GET,"");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Response Body
		JsonPath jp=res.jsonPath();
		List<Map<String,Object>> emprecs=jp.getList("data");
		int rownum=1;
		for(int i=0;i<emprecs.size();i++)
		{
			Row r1=sh.createRow(rownum);
			r1.createCell(0).setCellValue((int) emprecs.get(i).get("id"));
			r1.createCell(1).setCellValue((String) emprecs.get(i).get("employee_name"));
			r1.createCell(2).setCellValue((int) emprecs.get(i).get("employee_salary"));
			r1.createCell(3).setCellValue((int) emprecs.get(i).get("employee_age"));
			r1.createCell(4).setCellValue((String) emprecs.get(i).get("profile_image"));
			rownum++;
		}
		for(int i=0;i<5;i++)
		{
			sh.autoSizeColumn(i);
		}
		//Save .xlsx in HDD
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		wb.close();
	}
}
