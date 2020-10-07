package johanhaleby;

import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class JSONComplexResponseParsing2
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
		List<String> capital=jp.getList("capital");
		List<Long> population=jp.getList("population");
		List<String> flag=jp.getList("flag");
		List<List<String>> borders=jp.getList("borders");
		System.out.println(capital.get(0));
		System.out.println(population.get(0));
		System.out.println(flag.get(0));
		System.out.println(borders.get(0));
	}
}
