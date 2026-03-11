package apitest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import reusableMethods_API.Payload;

public class TestNG_API {

	   String pid;
	   @BeforeClass
	   public void setup()
	   {
	       RestAssured.baseURI = "https://rahulshettyacademy.com/";
	   }
		@Test
		
		public void addplace()
		{
	         //Calling baseURi(baseURI) -- is optional as we declared above which is global and it will take automatically
	        String response = given().log().all().baseUri(baseURI).queryParams("key","qaclick123").
	         body(Payload.addplace())
	         .when().post("maps/api/place/add/json").
	         then().log().all().assertThat().statusCode(200)
	         .body("status", equalTo("OK")).body("scope", equalTo("APP")).body("place_id", notNullValue()).
	         extract().asString();
	        System.out.println(response);
	        JsonPath js = new JsonPath(response);
	         pid = js.getString("place_id");
	        System.out.println(pid);
	        String statusid = js.getString("status");
	        System.out.println(statusid);
	        Assert.assertEquals(statusid, "OK");
	        
		}
		
		@Test(dependsOnMethods="addplace")
		
			public void updateplace()
			{
			 System.out.println("----Put or Update-----");
		       given().log().all().queryParams("key","qaclick123").body("{\r\n"
		       		+ "\"place_id\":\""+pid+"\",\r\n"
		       		+ "\"address\":\"70 Summer walk, USA, seerole, mhbd\",\r\n"
		       		+ "\"key\":\"qaclick123\"\r\n"
		       		+ "}\r\n"
		       		+ "").
		       when().put("maps/api/place/update/json").then().log().all().statusCode(200)
		       .body("msg",equalTo("Address successfully updated"));
				
			}
		 @Test(dependsOnMethods="updateplace")
		 
			public void Readplace()
			{
			 String response1 = given().log().all().queryParams("key","qaclick123","place_id",pid).when().get("maps/api/place/get/json").then().log()
				       .all().statusCode(200).extract().asString();
				       JsonPath jsp2 = new JsonPath(response1);
				       Assert.assertEquals(jsp2.getString("address"),"70 Summer walk, USA, seerole, mhbd");
				       Assert.assertEquals(jsp2.getString("types"),"Office");
				       
			}
		 @Test(dependsOnMethods = "Readplace")
		 public void deleteplace()
		 {
			 
			 String response5 = given().log().all().queryParams("key","qaclick123").body("{\\r\\n\" +\r\n"
			 		+ "      \"\\\"place_id\\\":\\\"\"+pid+\"\\\"\\r\\n\" +\r\n"
			 		+ "      \"}").when().delete("maps/api/place/delete/json").then().log()
				       .all().statusCode(200).extract().asString();
			 System.out.println(response5 +"yoyo");
			 //JsonPath jspD = new JsonPath(response5);
			 //Assert.assertEquals(jspD.getString("status"), "OK");
			// Assert.assertEquals(jspD.getString("msg"), "Delete operation failed, looks like the data doesn't exists");
        }

		}

	


