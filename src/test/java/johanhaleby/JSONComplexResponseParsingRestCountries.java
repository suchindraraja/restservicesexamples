package johanhaleby;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JSONComplexResponseParsingRestCountries
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="https://restcountries.eu/rest/v2/name";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.queryParam("fullText","true").request(Method.GET,"/india");
		//Get Status line from Response
		String rsl=res.getStatusLine();
		System.out.println("Status line is:\n"+rsl);
		//Get Response Body
		JsonPath jp=res.jsonPath();
		String name=jp.getString("name");
		System.out.println("Name is "+name);
		
		String capital=jp.getString("capital");
		System.out.println("Capital is "+capital);
		
		List<String> borders=jp.getList("borders");
		System.out.println("No of borders to India are: "+borders.size());
		System.out.println("Borders to India are: "+borders);
		
		String cur=jp.getString("currencies.name");
		System.out.println("Currency name is: "+cur);
		
		List<String> lang=jp.getList("languages.name");
		System.out.println("Languages are: "+lang);	
	}
}