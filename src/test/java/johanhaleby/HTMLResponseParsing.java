package johanhaleby;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HTMLResponseParsing 
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="http://lipsum.com";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.request(Method.GET,"/hindi");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Response Body
		String rbody=res.getBody().asString();
		System.out.println("Response Body is :\n"+rbody);
		String hv=res.getHeader("Content-Type");
		if(hv.contains("html"))
		{
			XmlPath hp=res.htmlPath();
			String svinres1=hp.get("html.head.title");
			System.out.println("Title value in Response is:\n"+svinres1);
			String svinres2=hp.get("html.@lang");
			System.out.println("lang attribute value in Response is:\n"+svinres2);
		}
	}
}
