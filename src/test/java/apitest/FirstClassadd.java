package apitest;

import io.restassured.RestAssured;
import reusableMethods_API.Payload;

import static io.restassured.RestAssured.*;

public class FirstClassadd
{
	public static void main(String args[])
	{
  RestAssured.baseURI = "https://rahulshettyacademy.com/";
  given().log().all().baseUri(baseURI).queryParams("key", "qaclick123").
  body(Payload.addplace()).
  when().post("maps/api/place/add/json").
  then().log().all().statusCode(200);
  
}
}