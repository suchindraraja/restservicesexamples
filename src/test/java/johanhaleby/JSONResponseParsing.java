package johanhaleby;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JSONResponseParsing 
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="http://jsonplaceholder.typicode.com/posts";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.request(Method.GET,"/1");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Response Body
		String rbody=res.getBody().asString();
		System.out.println("Response Body is :\n"+rbody);
		String hv=res.getHeader("Content-Type");
		if(hv.contains("json"))
		{
			JsonPath jp=res.jsonPath();
			String svinres=jp.get("title");
			System.out.println("Title value in Response is:\n"+svinres);
		}
	}
}
