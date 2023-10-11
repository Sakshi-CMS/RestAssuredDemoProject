package RABasic;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import file.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
   // 6. Verify if Sum of all Course prices matches with Purchase Amount*/
	@Test
	public static void  SumOfAllCoursesPrice()
	{
		int sum=0;
		JsonPath js=new JsonPath(payload.coursePrice());
		    int count=js.getInt("courses.size()");
		    
		for(int i=0; i<count; i++) {
			
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			int amount=price*copies;
			System.out.println(amount);
			sum=sum+amount;
			//assert.assertEquals(amount,js.getInt("dashboard.purchaseAmount"));
			
		}
		System.out.println(sum);
	    int purchesAmount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchesAmount);
		
	}

}
