package johanhaleby;

import java.io.File;
import java.io.FileWriter;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class QueryParametersGoogleSite
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="https://www.google.co.in";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with query parameter value but without path parameter
		Response res=req.queryParam("q","nageswara rao pusuluri").get("/search");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		File f = null;
		String hv=res.getHeader("Content-Type");
		if(hv.contains("json"))
		{
			f=new File("googlesearchjsonresponse.json");
		}
		else if(hv.contains("xml"))
		{
			f=new File("googlesearchxmlresponse.xml");
		}
		else if(hv.contains("html"))
		{
			f=new File("googlesearchhtmlresponse.html");
		}
		else if(hv.contains("plain"))
		{
			f=new File("googlesearchplainresponse.txt");
		}
		else if(hv.contains("csv"))
		{
			f=new File("googlesearchcsvresponse.csv");
		}
		else
		{
			System.out.println("Invalid data format as per Restful service");
		}
		FileWriter fw=new FileWriter(f);
		fw.write(res.getBody().asString());
		fw.close();
	}
}
