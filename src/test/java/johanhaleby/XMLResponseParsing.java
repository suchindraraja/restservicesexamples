package johanhaleby;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class XMLResponseParsing
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="http://ergast.com/api/f1/2008";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.request(Method.GET,"/9");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Response Body
		String rbody=res.getBody().asString();
		System.out.println("Response Body is :\n"+rbody);
		String hv=res.getHeader("Content-Type");
		if(hv.contains("xml"))
		{
			XmlPath xp=res.xmlPath();
			String svinres1=xp.get("MRData.RaceTable.Race.Circuit.Location.Country");
			System.out.println("Node like Country value in Response is:\n"+svinres1);
			String svinres2=xp.get("MRData.RaceTable.Race.Circuit.Location.@long");
			System.out.println("Attribute like longitude value in Response is:\n"+svinres2);
			String svinres3=xp.get("MRData.@series");
			System.out.println("Attribute like series value in Response is:\n"+svinres3);
		}
	}
}
