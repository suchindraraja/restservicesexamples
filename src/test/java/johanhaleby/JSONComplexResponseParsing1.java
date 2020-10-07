package johanhaleby;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JSONComplexResponseParsing1
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="https://restcountries.eu/rest/v2/all";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.queryParam("fields","name;capital").request(Method.GET,"");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Response Body
		String rbody=res.getBody().asString();
		File f1=new File("countriesnamesandcapitalsresponsebody.txt");
		FileWriter fw1=new FileWriter(f1);
		BufferedWriter bw1=new BufferedWriter(fw1);
		bw1.write(rbody);
		//Response parsing and taking into files
		JsonPath jp=res.jsonPath();
		List<String> countrynames=jp.getList("name");
		List<String> capitals=jp.getList("capital");
		File f2=new File("countriesnamesandcapitals.txt");
		FileWriter fw2=new FileWriter(f2);
		BufferedWriter bw2=new BufferedWriter(fw2);
		for(int i=0;i<countrynames.size();i++)
		{
			bw2.write(countrynames.get(i)+"--->"+capitals.get(i));
			bw2.newLine();
		}
		bw1.close();
		fw1.close();
		bw2.close();
		fw2.close();
	}
}
