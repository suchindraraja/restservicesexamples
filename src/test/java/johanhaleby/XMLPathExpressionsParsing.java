package johanhaleby;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class XMLPathExpressionsParsing
{
	public static void main(String[] args) throws Exception
	{
		//Register end-point and Create HTTP Request
		RestAssured.baseURI="http://ergast.com/api/f1";
		RequestSpecification req=RestAssured.given();
		//Send HTTP Request with one path parameter value
		Response res=req.request(Method.GET,"/2011");
		//Get Response Body which is in XML and parse using xmlpath expressions 
		XmlPath xp=res.xmlPath();
		
		//1. using **,findAll() with one condition on node name, and size()
		int c1=xp.get("**.findAll{it.name()=='Race'}.size()");
		System.out.println("Total no of Race named nodes are: "+c1);
		
		//2. using **,findAll() with one condition on node attribute, and size()
		int c2=xp.get("**.findAll{it.@season=='2011'}.size()");
		System.out.println("Total no of season named attributes are: "+c2);
		
		//3. using **,findAll() with more than 1 condition and size
		int c3=xp.get("**.findAll{it.name()=='Race' && it.@season=='2011' && it.@round=='1'}.size()");
		System.out.println("Matching values are "+c3);
		
		//4. using **,findAll() and child node with size
		int c4=xp.get("**.findAll{it.name()=='Race' && it.@season=='2011'}.RaceName.size()");
		System.out.println("Total no of RaceName named nodes under Race named node are: "+c4);
		
		//4.1. using **,findAll() and child node with size
		int c41=xp.get("**.findAll{it.name()=='Race' && it.@round=='1'}.RaceName.size()");
		System.out.println("Total no of RaceName named nodes under Race named node are: "+c41);
		
		//5. using **,findAll() nodes and get values of all those nodes
		List<String> nvl1=xp.get("**.findAll{it.name()=='Race' && it.@season=='2011'}");
		System.out.println(nvl1);
		
		//5.1. using **,findAll() specific node and get values of that node
		List<String> nvl2=xp.get("**.findAll{it.name()=='Race' && it.@season=='2011'}.RaceName");
		//System.out.println(nvl1);
		for(int i=0;i<nvl2.size();i++)
		{
			System.out.println(nvl2.get(i));
		}
		
		//5.2. using **,findAll() specific node and get value of that node
		String nvl3=xp.get("**.findAll{it.name()=='Race' && it.@round=='1'}.RaceName");
		System.out.println("Specific child node value is "+nvl3);
		
		//6. using **,findAll() nodes and get required attribute values of those nodes
		List<String> avl=xp.get("**.findAll{it.name()=='Race' && it.@season=='2011'}.@url");
		//System.out.println(avl);
		for(int i=0;i<avl.size();i++)
		{
			System.out.println("Url for "+(i+1)+" round is:"+avl.get(i));
		}
		
		//7. using **,find and get that node value
		String v1=xp.getString("**.find{it.@season=='2011' && it.@round=='1'}");
		System.out.println(v1);
		
		//8. using **,find and get that attribute value
		String v2=xp.getString("**.find{it.@season=='2011' && it.@round=='1'}.@url");
		System.out.println("Specific node attribute value is "+v2);
		
		//9. using **,find() specific node and get child node value
		String v3=xp.get("**.find{it.@season=='2011' && it.@round=='1'}.RaceName");
		System.out.println("Specific node value is "+v3);
		
		//10. using **,find and get child node's attribute value
		String v4=xp.get("**.find{it.name()=='Race' && it.@round=='17'}.Circuit.@circuitId");
		System.out.println(v4);
		
		//11. to get Root node attribute value
		String rootnv=xp.getString("MRData.@series");
		System.out.println("Root node attribute value is "+rootnv);
		
		//12. 1st Child node attribute value
		String fnodeav=xp.getString("MRData.RaceTable.@season");
		System.out.println("First node attribute value is "+fnodeav);
		
		//Locating Relative
		List<String> x1=xp.get("**.findAll{it.name()=='Locality'}");
		for(int i=0;i<x1.size();i++)
		{
			System.out.println(x1.get(i));
		}
		
		List<String> w1=xp.get("**.findAll{it.name()=='Date'}");
		for(int i=0;i<w1.size();i++)
		{
			System.out.println(w1.get(i));
		}
		
		String y1=xp.get("**.findAll{it.name()=='Locality'}[0]");
		System.out.println(y1);
		
		String z1=xp.getString("**.findAll{it.name()=='Location'}[0].Country");
		System.out.println(z1);
		
		String p1=xp.get("**.findAll{it.name()=='Location' && it.@long=='144.968'}.Locality");
		System.out.println(p1);
		
		//Create an excel file to store xml response results
		File f=new File("xmlparsingresults.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook();
		Sheet sh=wb.createSheet("Sheet1");
		Row r=sh.createRow(0);
		r.createCell(0).setCellValue("RaceName");
		r.createCell(1).setCellValue("CircuitName");
		r.createCell(2).setCellValue("Locality");
		r.createCell(3).setCellValue("Country");
		r.createCell(4).setCellValue("Date");
		r.createCell(5).setCellValue("Time");
		
		//Font settings for Headings
		Font font1=wb.createFont();
		font1.setColor(IndexedColors.TURQUOISE1.getIndex());
	    font1.setItalic(true);
	    font1.setBold(true);
	    //Cell Style settings for Headings
		CellStyle cs1=wb.createCellStyle();
		cs1.setFont(font1);
		cs1.setAlignment(HorizontalAlignment.CENTER);
		r.getCell(0).setCellStyle(cs1);
		r.getCell(1).setCellStyle(cs1);
		r.getCell(2).setCellStyle(cs1);
		r.getCell(3).setCellStyle(cs1);
		r.getCell(4).setCellStyle(cs1);
		r.getCell(5).setCellStyle(cs1);
		
		for(int i=1;i<=19;i++)
		{
			String a=xp.get("**.findAll{it.name()=='Race' && it.@round=='"+i+"'}.RaceName");
			String b=xp.get("**.findAll{it.name()=='Race' && it.@round=='"+i+"'}.Circuit.CircuitName");
			String c=xp.get("**.findAll{it.name()=='Race' && it.@round=='"+i+"'}.Circuit.Location.Locality");
			String d=xp.get("**.findAll{it.name()=='Race' && it.@round=='"+i+"'}.Circuit.Location.Country");
			String e=xp.get("**.findAll{it.name()=='Race' && it.@round=='"+i+"'}.Date");
			String f1=xp.get("**.findAll{it.name()=='Race' && it.@round=='"+i+"'}.Time");
			Row r1=sh.createRow(i);
			r1.createCell(0).setCellValue(a);
			r1.createCell(1).setCellValue(b);
			r1.createCell(2).setCellValue(c);
			r1.createCell(3).setCellValue(d);
			r1.createCell(4).setCellValue(e);
			r1.createCell(5).setCellValue(f1);
			
			//Font settings for Test Result
			Font font2=wb.createFont();
			font2.setColor(IndexedColors.BLUE.getIndex());
		    font2.setItalic(true);
		    //Cell Style settings for Test Result
			CellStyle cs2=wb.createCellStyle();
			cs2.setFont(font2);
			r1.getCell(0).setCellStyle(cs2);
			r1.getCell(1).setCellStyle(cs2);
			r1.getCell(2).setCellStyle(cs2);
			r1.getCell(3).setCellStyle(cs2);
			r1.getCell(4).setCellStyle(cs2);
			r1.getCell(5).setCellStyle(cs2);
			
		}
		for(int i=0;i<6;i++)
		{
			sh.autoSizeColumn(i);
		}
		//Save and close excel
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		wb.close();
	}
}
