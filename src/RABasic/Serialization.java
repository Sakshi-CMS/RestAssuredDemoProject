package RABasic;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import Pojo.LandInfo;
import Pojo.Location;
import file.payload;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Serialization {
	
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
		
	    Response res= given().log().all().queryParam("key","qaclick123")
	   .body(lf)
	   .when().post("/maps/api/place/add/json")
	   .then().assertThat().statusCode(200).extract().response();
	    
	    String responseString=res.asPrettyString();
	    System.out.println(responseString);
	}
	

}
