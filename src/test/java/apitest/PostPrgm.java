package apitest;

import io.restassured.RestAssured;
import reusableMethods_API.Payload;

import static io.restassured.RestAssured.*;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.Matchers.*;

public class PostPrgm {

	public static void main(String[] args) 
	{
         RestAssured.baseURI = "https://rahulshettyacademy.com/";
         given().log().all().baseUri(baseURI).queryParams("key","qaclick123").
         body(Payload.addplace())
         .when().post("maps/api/place/add/json").
         then().log().all().assertThat().statusCode(200)
         .body("status", equalTo("OK")).body("scope", equalTo("APP")).body("place_id", notNullValue()).
         body("id", instanceOf(String.class)).time(lessThanOrEqualTo(20001L), TimeUnit.MILLISECONDS);
         
	}

}
