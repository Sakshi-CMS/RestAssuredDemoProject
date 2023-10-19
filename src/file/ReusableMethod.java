package file;

import io.restassured.path.json.JsonPath;

public class ReusableMethod {

	 public static JsonPath rawToJSON(String response) {
		 
		 JsonPath js1=new JsonPath(response);
		 return js1;
	 }
}
