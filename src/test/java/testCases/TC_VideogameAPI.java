package testCases;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;

public class TC_VideogameAPI {

	// @Test
	public void getAllVideoGames() {

		baseURI = "http://localhost:8080/app/videogames";

		given().when().get("/1").then().statusCode(200).log().all();

	}

	@Test(priority = 1)
	public void postVideogame() {
		baseURI = "http://localhost:8080/app/videogames";

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", 12);
		map.put("name", "SpiderMan");
		map.put("releaseDate", "2023-01-22T17:44:32.132Z");
		map.put("reviewScore", 5);
		map.put("category", "Marvel");
		map.put("rating", "U/A");

		Response response = given().header("Content-type", "application/json").contentType(ContentType.JSON)
				.accept(ContentType.JSON).when().body(map).post().then().statusCode(200).log().body().extract()
				.response();

		Assert.assertEquals(response.asString().contains("Record Added Successfully"), true);

	}

	@Test(priority = 2)
	public void getvideogamebyid() {

		baseURI = "http://localhost:8080/app/videogames";

		given().when().get("/5").then().statusCode(200).body("videogame.id", equalTo("5")).log().body();

	}

	@DataProvider(name = "dataforpost")
	public Object[][] dataforpost() {

		return new Object[][] { { 50, "Avengers", "2023-01-23T16:03:35.262Z", 5, "Action", "Adult" } };

	}

	@Test(dataProvider = "dataforpost",priority = 3)
	public void videogamepost(int id, String gamename, String releasedate, int reviewscore, String category,
			String rating) {
		baseURI = "http://localhost:8080/app/videogames";

		JSONObject request = new JSONObject();
		request.put("id", id);
		request.put("name", gamename);
		request.put("releaseDate", releasedate);
		request.put("reviewScore", reviewscore);
		request.put("category", category);
		request.put("rating", rating);

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).when()
				.body(request.toString()).post().then().statusCode(200).log().body().extract().response();

		Assert.assertEquals(response.asString().contains("Record Added Successfully"), true);
	}

	//@Test(priority = 3)
	public void updatevideogame() {

		// baseURI = "http://localhost:8080/app/videogames";

		Map<String, Object> map = new HashMap<String, Object>();

		// map.put("id", 12);
		map.put("name", "SpiderMan2");
		map.put("releaseDate", "2023-01-23T17:44:32.132Z");
		map.put("reviewScore", 10);
		map.put("category", "Marvel-Avengers");
		map.put("rating", "Adult");

		Response response = given().contentType("application/json").when()
				.put("http://localhost:8080/app/videogames/12").then().log().body().body("videogame.id", equalTo("12"))
				.body("videogame.name", equalTo("SpiderMan2")).extract().response();

	}
	@Test(priority = 4)
	public void deletegame() {
		baseURI = "http://localhost:8080/app/videogames";
		
		when()
			.delete("/12")
		.then()
			.statusCode(200)
		.log().body();
		
	}
}
