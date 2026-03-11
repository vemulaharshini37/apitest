package apitest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import reusableMethods_API.Payload;
import reusableMethods_API.ShortcutforResponses;

import static io.restassured.RestAssured.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.json.Json;
import org.testng.Assert;

import static org.hamcrest.Matchers.*;

public class PutPrgm_Update {

	public static void main(String[] args) 
	{
         RestAssured.baseURI = "https://rahulshettyacademy.com/";
         //Calling baseURi(baseURI) -- is optional as we declared above which is global and it will take automatically
        String response = given().log().all().baseUri(baseURI).queryParams("key","qaclick123").
         body(Payload.addplace())
         .when().post("maps/api/place/add/json").
         then().log().all().assertThat().statusCode(200)
         .body("status", equalTo("OK")).body("scope", equalTo("APP")).body("place_id", notNullValue()).
         extract().asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String pid = js.getString("place_id");
        System.out.println(pid);
        String statusid = js.getString("status");
        System.out.println(statusid);
        Assert.assertEquals(statusid, "OK");
        
        System.out.println("----another way from other package-----");
        JsonPath js1 = ShortcutforResponses.rawToJson(response);
       String pid1 = js1.getString("place_id");
       System.out.println(pid1);
       //-----Put----or---Update---------//
       System.out.println("----Put or Update-----");
       given().log().all().queryParams("key","qaclick123").body("{\r\n"
       		+ "\"place_id\":\""+pid+"\",\r\n"
       		+ "\"address\":\"70 Summer walk, USA, seerole, mhbd\",\r\n"
       		+ "\"key\":\"qaclick123\"\r\n"
       		+ "}\r\n"
       		+ "").
       when().put("maps/api/place/update/json").then().log().all().statusCode(200)
       .body("msg",equalTo("Address successfully updated"));
  //------------------Get or to Read api------------//
       System.out.println("----Get or to Read api-----");
       String response1 = given().log().all().queryParams("key","qaclick123","place_id",pid).when().get("maps/api/place/get/json").then().log()
       .all().statusCode(200).extract().asString();
       JsonPath jsp2 = new JsonPath(response1);
       Assert.assertEquals(jsp2.getString("address"),"70 Summer walk, USA, seerole, mhbd");
       Assert.assertEquals(jsp2.getString("types"),"Office");
       
	}

}
