package restassured.casestudy.project2.swaggerui;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestGETLogin 
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		//Providing credentials in URL is called as deep linking
		Response res=req.queryParam("username","Virushka").queryParam("password","anushkasharma").request(Method.GET,"/user/login");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Status header value
		String header=res.getHeader("Content-Type");
		System.out.println(header);
		//Get Response Body
		String rbody=res.getBody().asString();
		System.out.println(rbody);
	}
}
