package br.com.desafioSicredi.api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class GetProducts {

    private String endpoint = "/auth/products";

    public Response listarProdutos(String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint);
    }

    public Response listarProdutosSemToken() {
        return given()
                .when()
                .get(endpoint);
    }
}