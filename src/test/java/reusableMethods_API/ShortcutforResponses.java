package reusableMethods_API;

import io.restassured.path.json.JsonPath;

public class ShortcutforResponses
{
	public static JsonPath rawToJson(String response)
	{
		JsonPath jp1 = new JsonPath(response);
		return jp1;
	}

}
