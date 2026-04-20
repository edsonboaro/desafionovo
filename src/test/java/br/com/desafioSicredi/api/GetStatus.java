package br.com.desafioSicredi.api; // Note o sub-pacote .api

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class GetStatus {

    private String endpoint = "/test";

    public Response buscarStatusDaAplicacao() {
        return given()
                .when()
                .get(endpoint);
    }
}