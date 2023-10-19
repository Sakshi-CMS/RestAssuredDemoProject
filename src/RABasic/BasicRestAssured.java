package RABasic;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import file.ReusableMethod;
import file.payload;

public class BasicRestAssured {

	public static void main(String[] args) throws IOException {
		
		//Add Address using post mothod
		RestAssured.baseURI="https://rahulshettyacademy.com";
		 String response=given().log().all().queryParam("key","qaclick123").header("content-Type","application/json")
		//if we want so sent JSON direct from Notepad or any file
	    //1.content of file convert into byte->byte data convert into string
		.body(new String(Files.readAllBytes(Paths.get("D:\\Eclipse2021-09 Workspace\\Seleneium_Tranning\\RestAssuredDemoProject\\AddPlace.json"))))
		.when().post("/maps/api/place/add/json")
	    .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
	    .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
              //48be32182d9b6eb4d7d9a7a0bb633802
		 
		 System.out.println(response);
		 JsonPath js=new JsonPath(response);
		 String  PlaceID=js.getString("place_id");
		 System.out.println("Extract place id:" + PlaceID);
		 
		 //Update place using Put method
		 String newAddresss="Summer walk, Africa";
		 
		 given().log().all().queryParam("key", "qaclick123").header("content-Type","application/json")
		 .body("{\r\n"
		 		+ "\"place_id\":\""+PlaceID+"\",\r\n"
		 		+ "\"name\":\"Sakshi Bhavasar\",\r\n"
		 		+ "\"address\":\""+newAddresss+"\",\r\n"
		 		+ "\"key\":\"qaclick123\"\r\n"
		 		+ "}")
		 .when().put("/maps/api/place/update/json")
		 .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		 
		 //get place iusing get method
		 
		 String getPlaceResponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", PlaceID)
		 .when().get("/maps/api/place/get/json")
		 .then().assertThat().log().all().statusCode(200).extract().response().asString();
		 
		 
		 JsonPath js1=ReusableMethod.rawToJSON(getPlaceResponse);
		 String actualAddress=js1.getString("address");
		 System.out.println(actualAddress);
		 Assert.assertEquals(actualAddress, newAddresss);
}

}
