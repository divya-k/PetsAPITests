package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class PetStepDefinition  {

	String accesstoken;
	Response createPetResponse;
    Response getPetResponse;
	
    @Given("^Authorize oAuth with scope of read pets and write pets to receive the access token$")
    public void getAccessToken() {
    	
		String exePath = System.getProperty("user.dir") + "//drivers//chromedriver";
        System.setProperty("webdriver.chrome.driver", exePath);
        WebDriver driver = new ChromeDriver();
		
		driver.get("https://petstore.swagger.io/oauth/authorize?response_type=token&client_id=test&scope=read:pets&scope=write:pets&state=verifyfjdss");
		driver.findElement(By.id("allow")).click();
		driver.findElement(By.id("login")).click();
		driver.findElement(By.name("authorize")).click();
		String url = driver.getCurrentUrl();
		//System.out.println(url);

		String p = url.split("access_token=")[1];
		String code = p.split("&token_type")[0];
		//System.out.println(code);
		driver.close();
		accesstoken=code;
    }

    @When("^we create a pet with id (\\d+) name \"([^\"]*)\" photoUrl \"([^\"]*)\" and status \"([^\"]*)\"$") 
    public void createPet(int id,String name,String photoUrl,String status) { 
    
    		createPetResponse = given().
				 				contentType("application/json").
				 				queryParams("access_token", accesstoken).
				 				body("{"+
				 						"\"id\":" +id+","+
				 						"\"category\": {"+
				 						"\"id\":" +id+","+
				 						"\"name\": \""+name+"\""+
				 						"},"+
				 						"\"name\": \""+name+"\","+
				 						"\"photoUrls\": ["+
				 						"\""+ photoUrl +"\""+
				 						"],"+
				 						"\"tags\": ["+
				 						"{"+
				 						"\"id\":"+ id+","+
				 						"\"name\": \""+name+"\""+
				 						"}"+
				 						"],"+
				 						"\"status\": \""+status+"\""+
				 						"}").
				 			when().
				 				post("https://petstore.swagger.io/v2/pet").
				 			then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

		System.out.println(createPetResponse.asString());		
    }
    
    @When("^the pet should be created successfully with id (\\d+) name \"([^\"]*)\" photoUrl \"([^\"]*)\" and status \"([^\"]*)\"$")
    public void the_pet_should_be_created_successfully_with_id_name_photoUrl_and_status(int id, String name, String photoUrl, String status){
    	String createPetResponseValue = createPetResponse.asString();  	
    	JsonPath js= new JsonPath(createPetResponseValue);
    	
		List<String> list = new ArrayList<String>();
		 list = js.get("photoUrls");
	    
		Assert.assertEquals(js.get("id"), id, "Verify id");
		Assert.assertEquals(js.get("name"), name, "Verify name");
		Assert.assertEquals(js.get("status"), status, "Verify status");
		Assert.assertEquals(list.get(0), photoUrl, "Verify photoUrl");
    }
    
    
    @When("^we get the pet with id (\\d+)$")
    public void when_we_get_the_pet_with_id(int id)  {
    	 getPetResponse= given().
					contentType("application/json").
					queryParams("access_token", accesstoken).
			when()
					.get("https://petstore.swagger.io/v2/pet/"+ id).
					 then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
    	 System.out.println(getPetResponse);
    	 System.out.println(getPetResponse.asString());
      
    }
    @Then("^the pet details should be correct with id (\\d+) name \"([^\"]*)\" photoUrl \"([^\"]*)\" and status \"([^\"]*)\"$")
    public void the_pet_details_should_be_correct_with_id_name_photoUrl_and_status(int id, String name, String photoUrl, String status) {
    	
    	String getPetResponseValue = getPetResponse.asString();  	
    	JsonPath js= new JsonPath(getPetResponseValue);
    	
		System.out.println(js.get("id"));
		
		List<String> list = new ArrayList<String>();
		 list = js.get("photoUrls");
	    
		Assert.assertEquals(js.get("id"), id, "Verify id");
		Assert.assertEquals(js.get("name"), name, "Verify name");
		Assert.assertEquals(js.get("status"), status, "Verify status");
		Assert.assertEquals(list.get(0), photoUrl, "Verify photoUrl");
    }
}
