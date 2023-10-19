package RABasic;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.RequestBuilder;

import Pojo.LandInfo;
import Pojo.Location;
import file.payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		LandInfo lf=new LandInfo();
		lf.setAccuracy(50);
		lf.setAddress("29, side layout, cohen 09");
		lf.setLanguage("French-IN");
		lf.setLocation(null);
		lf.setName("Frontline house");
		lf.setPhone_number("(+91) 983 893 3937");
		lf.setWebsite("http://google.com");
		List <String> myList=new ArrayList <String>();
		myList.add("shoe park");
		myList.add("shop");
		lf.setTypes(myList);
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		lf.setLocation(l);
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
	    RequestSpecification res = given().spec(req)
	   .body(lf);
	    Response response=res.when().post("/maps/api/place/add/json")
	   .then().spec(resSpec).extract().response();
	    
	    String responseString=response.asString();
	    System.out.println(responseString);
	}
	

}
