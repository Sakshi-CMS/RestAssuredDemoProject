package RABasic;

import file.payload;
import io.restassured.path.json.JsonPath;

public class ComplrxJsnoParse {
	public  static void main (String[]args) {
	
	//Mock Response
    /*1. Print No of courses returned by API
    2.Print Purchase Amount
    3. Print Title of the first course
    4. Print All course titles and their respective Prices
    5. Print no of copies sold by RPA Course
    6. Verify if Sum of all Course prices matches with Purchase Amount*/
	
	JsonPath js=new JsonPath(payload.coursePrice());
	//1. Print No of courses returned by API
	int count =js.getInt("courses.size()");
	System.out.println(count);
	
	//2.Print Purchase Amount
	int totalAmount=js.getInt("dashboard.purchaseAmount");
	System.out.println(totalAmount);
	
	 //3. Print Title of the first course
	String Titlefirtsname=js.get("courses[0].title");
	System.out.println(Titlefirtsname);
	
	// 4. Print All course titles and their respective Prices
	for(int i=0; i<count; i++) {
		String coursesTitle=js.get("courses["+i+"].title");
		System.out.println(coursesTitle);
		System.out.println(js.get("courses["+i+"].price").toString());
	}
		
	//5. Print no of copies sold by RPA Course
		
	System.out.println("No of copies sold by RPA");
	for(int i=0; i<count; i++) {
		String coursesTitles=js.get("courses["+i+"].title");
		if(coursesTitles.equalsIgnoreCase("RPA"))
		{
		int NumberofCopies=js.get("courses["+i+"].copies");
		System.out.println(NumberofCopies);
		break;
		}	
		
	    // 6. Verify if Sum of all Course prices matches with Purchase Amount
		
		
		
		
	
		
	
		
	}
	}
}
