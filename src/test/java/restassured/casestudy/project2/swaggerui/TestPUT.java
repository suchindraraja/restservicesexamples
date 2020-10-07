package restassured.casestudy.project2.swaggerui;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestPUT 
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RequestSpecification req=RestAssured.given();
		//Pack data
		JSONObject jo=new JSONObject();
		req.contentType("application/json");
		//jo.put("id","25");
		jo.put("username","Virushka");
		jo.put("firstName","Virat");
		jo.put("lastName","Kohli");
		jo.put("email","viratkohli@cricket.com");
		jo.put("password","anushkasharma");
		jo.put("phone","9143143143");
		//jo.put("userStatus","");
		req.body(jo.toString());
		//Send HTTP Request with one path parameter value
		Response res=req.request(Method.PUT,"/user/Viratkohli");
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
