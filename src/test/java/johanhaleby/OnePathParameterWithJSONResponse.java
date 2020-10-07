package johanhaleby;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class OnePathParameterWithJSONResponse
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
		//Get Status Headers from Response
		Headers rhs=res.getHeaders();
		List<Header> lrhs=rhs.asList();
		System.out.println("Status Headers are:");
		for(Header h:lrhs)
		{
			System.out.println(h.getName()+" : "+h.getValue());
		}
		//Get Response Body
		ResponseBody rb=res.getBody();
		String rbody=rb.asString();
		System.out.println("Response Body is :\n"+rbody);
	}
}
