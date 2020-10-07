package johanhaleby;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JSONComplexResponseParsingRestAPIExample1
{
	public static void main(String[] args) throws Exception
	{
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
		String status=jp.getString("status");
		System.out.println("Value of Status field is -->"+status);
		
		String empname=jp.getString("data[0].employee_name");
		System.out.println("First emp name is -->"+empname);
		
		List<String> empnames=jp.getList("data.employee_name");
		System.out.println("Total no of employees using for loop are "+empnames.size()+" and they are:");
		for(int i=0;i<empnames.size();i++)
		{
			System.out.println(empnames.get(i));
		}
		System.out.println("Total no of employees using for each loop are "+empnames.size()+" and they are:");
		for(String a:empnames)
		{
			System.out.println(a);
		}
		
		List<Map<String,String>> emprecords=jp.getList("data");
		System.out.println("Total no of employee records via for loop are "+emprecords.size()+" and they are:");
		for(int i=0;i<emprecords.size();i++)
		{
			System.out.println(emprecords.get(i));
		}
		System.out.println("Total no of employee records via for each loop are \"+emprecords.size()+\" and they are:");
		for(Map<String,String> m:emprecords)
		{
			System.out.println(m);
		}
		
		List<String> specempnames=jp.getList("data[23,6,2,8,20].employee_name");
		System.out.println("Specific employee names are:");
		for(int i=0;i<specempnames.size();i++)
		{
			System.out.println(specempnames.get(i));
		}	
	}
}
