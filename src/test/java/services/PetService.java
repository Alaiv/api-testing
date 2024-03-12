package services;

import io.restassured.response.Response;
import models.PetDto;
import utils.Serializer;

import static io.restassured.RestAssured.given;

public class PetService extends BaseService {
    private final String basePath = "/pet";

    public PetService(String baseUrl) {
        super(baseUrl);
    }

    public Response addPet(PetDto petDto) {
        String json = Serializer.extractFrom(petDto);

        return given()
                .spec(requestSpec)
                .when()
                .body(json)
                .post(basePath)
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public Response getPet(int id) {
        return given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .get(basePath + "/{id}")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public Response deletePet(int id) {
        return given()
                .spec(requestSpec)
                .pathParam("id", id)
                .when()
                .delete(basePath + "/{id}")
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }
}
