package RABasic;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import file.ReusableMethod;
import file.payload;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="BookData")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
         String response= given().header("content-Type","application/json")
        .body(payload.Addbook(isbn,aisle))
        .when()
        .post("/Library/Addbook.php")
        .then().log().all().assertThat().statusCode(200)
        .extract().response().asString();
         JsonPath js= ReusableMethod.rawToJSON(response);
         String id=js.get("ID");
         System.out.println(id);
         
	}
	//we are using Dataprovider to parameterize API with multiple data
	@DataProvider(name="BookData")
	
	public Object [][] bookdata() {
		
		 return new Object [][] {{"adfs","6364"},{"abfs","6361"},{"acfs","6264"}};
		 
	}

}
