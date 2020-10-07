package johanhaleby;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MatrixParameters
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.urlEncodingEnabled=false;
		RestAssured.baseURI="https://petstore.swagger.io/v2/pet/findByStatus;matrixParm=test";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.queryParam("offset","0").queryParam("pageSize","10").request(Method.GET,"");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Status Headers from Response
		List<Header> lrhs=res.getHeaders().asList();
		System.out.println("Status Headers are:");
		for(Header h:lrhs)
		{
			System.out.println(h.getName()+" : "+h.getValue());
		}
		//Get Response Body
		String rbody=res.getBody().asString();
		System.out.println("Response Body is :\n"+rbody);
	}
}
