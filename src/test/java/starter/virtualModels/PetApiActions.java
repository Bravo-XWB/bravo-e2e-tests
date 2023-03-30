package starter.virtualModels;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static net.serenitybdd.rest.SerenityRest.then;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import net.serenitybdd.core.steps.UIInteractions;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;

public class PetApiActions extends UIInteractions {

  @Given("Kitty is available in the pet store")
  public Long givenKittyIsAvailableInPetStore() {

    Pet pet = new Pet("Kitty", "available");

    Long newId = SerenityRest.given()
        .baseUri("https://petstore.swagger.io")
        .basePath("/v2/pet")
        .body(pet, ObjectMapperType.GSON)
        .accept(ContentType.JSON)
        .contentType(ContentType.JSON).post().getBody().as(Pet.class, ObjectMapperType.GSON).getId();
    System.out.println("New id" + newId);
    return newId;
  }

  @When("I ask for a pet using Kitty's ID: {0}")
  public void whenIAskForAPetWithId(Long id) {
    System.out.println("id = " + id);
    Response response = SerenityRest.when().get("https://petstore.swagger.io/v2/pet/" + id);
    System.out.println("response = " + response);
  }

  @Then("I get Kitty as result")
  public void thenISeeKittyAsResult() {
    System.out.println("thenISeeKittyAsResult");
    SerenityRest.then().body("name", Matchers.equalTo("Kitty"));
    System.out.println("ok");
  }

}
