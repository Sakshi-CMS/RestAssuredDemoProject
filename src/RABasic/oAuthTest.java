package RABasic;

import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;

import Pojo.Api;
import Pojo.getCources;
import Pojo.webAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class oAuthTest {
	public static void main(String[] args) {

		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		String url = ("https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AfJohXnIol3WpULC6XGLtPCPtpbAmw5nFJxX4dCT80QZN5rnrcAaC8oPIbrHpwhPhQQLdQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none");
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);

		String accesstokenRespense = given().urlEncodingEnabled(false).queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshecodettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(accesstokenRespense);
		String accesstoken = js.getString("access_token");

		getCources gc = given().queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(getCources.class);
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());

		if (accesstokenRespense.startsWith("AUTHENTICATION FAILED !")) {
			System.out.println("Authentication failed. Please enter a valid access token.");
		} else {
			System.out.println("Unexpected response format.");
		}

		// System.out.println(response);
		List<Api> apiCourses = gc.getCourses().getApi();

		for (int i = 0; i < apiCourses.size(); i++) {

			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoupUI Webservices testing")) {

				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		ArrayList<String> a = new ArrayList<String>();

		List<webAutomation> WebCourses = gc.getCourses().getWebAutomation();
		for (int j = 0; j < WebCourses.size(); j++) {

			System.out.println(WebCourses.get(j).getCourseTitle());

			// compair array and arraylist is very trick so we are comparing array to
			// arraylist using java method (arrays.asList)
			List<String> expectedTitle = Arrays.asList(courseTitles);
			Assert.assertTrue(a.equals(expectedTitle));

		}

	}

}