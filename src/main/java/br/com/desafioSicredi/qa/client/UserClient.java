package br.com.desafioSicredi.qa.client;

import br.com.desafioSicredi.qa.specs.RequestSpecs;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String USERS_ENDPOINT = "/users";

    public Response listarTodos() {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .when()
                .get(USERS_ENDPOINT);
    }

    public Response buscarPorId(int id) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .pathParam("id", id)
                .when()
                .get(USERS_ENDPOINT + "/{id}");
    }

    public Response buscarUsuarios(String query) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .queryParam("q", query)
                .when()
                .get(USERS_ENDPOINT + "/search");
    }
}