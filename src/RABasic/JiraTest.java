package RABasic;
import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {
	public static void main (String []args) {
		 
		 
		RestAssured.baseURI="http://localhost:8443";
		//login to the jira and extract session id
		 SessionFilter session=new SessionFilter();
		 String response=given().header("Content-Type", "application/json")
		.body("{ \"username\": \"sakshi\", \"password\": \"Sakshi@1904#\" }")
		.log().all()
		.filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		 String expectedMessage="Hii How are you";
		//Add comment
		String AddComment=given().pathParams("id", "10000").log().all()
		.header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n "
				+ "}").filter(session).
		when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		JsonPath js=new JsonPath(AddComment);
		String commentID=js.get("id");
		
		
		//Add Attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id", "10000")
		.header("Content-Type","multipart/form-data")
		.multiPart("file", new File ("jira.txt"))
		.when().post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
		
		//get Issue
		String issueDetails=given().filter(session).pathParam("id", "10000")
				.queryParam("fields","comment") 
				.log().all().when().get("/rest/api/2/issue/{id}")
		.then().log().all().extract().response().asString();
		System.out.println(issueDetails);
		
		
		 JsonPath js1=new JsonPath(issueDetails);
		 int commentCount=js1.getInt("fields.comment.comments.size()");
		 for(int i=0; i<=commentCount; i++) {
			 
			 String CommentIdIssue=js1.get("fields.comment.comments.("+i+").id").toString();
			 if(CommentIdIssue.equalsIgnoreCase(commentID)) {
				 String message=js1.get("fields.comment.comments.("+i+").body").toString();
			System.out.println(message);
			Assert.assertEquals(message, expectedMessage);
				 
			 }
		 }
	
	}

}
