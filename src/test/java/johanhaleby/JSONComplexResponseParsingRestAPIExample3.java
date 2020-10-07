package johanhaleby;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JSONComplexResponseParsingRestAPIExample3
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
		int noer=jp.getList("data").size();
		List<Map<String,Object>> loemprec=jp.getList("data");
		for(int i=0;i<noer;i++)
		{
			System.out.println("Employee"+(i+1)+" record:");
			System.out.println("Emp id is-->"+loemprec.get(i).get("id"));
			System.out.println("Emp name is-->"+loemprec.get(i).get("employee_name"));
			System.out.println("Emp sal is-->"+loemprec.get(i).get("employee_salary"));
			System.out.println("Emp age is-->"+loemprec.get(i).get("employee_age"));
			System.out.println("Emp pro_img is-->"+loemprec.get(i).get("profile_image"));
			System.out.println();
		}
	}
}
