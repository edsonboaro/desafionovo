package br.com.desafioSicredi.qa.client;

import br.com.desafioSicredi.qa.specs.RequestSpecs;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProductClient {

    private static final String PRODUCTS_ENDPOINT = "/products";

    public Response listarTodos() {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .when()
                .get(PRODUCTS_ENDPOINT);
    }

    public Response buscarPorId(int id) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .pathParam("id", id)
                .when()
                .get(PRODUCTS_ENDPOINT + "/{id}");
    }

    public Response buscarComPaginacao(int limit, int skip) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .when()
                .get(PRODUCTS_ENDPOINT);
    }

    public Response listarComParametros(int limit, int skip) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec()) // Adicionado aqui!
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .when()
                .get(PRODUCTS_ENDPOINT); // Usando a constante para evitar erro de digitação
    }

    public Response buscarProduto(String nomeProduto) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec()) // Adicionado aqui!
                .queryParam("q", nomeProduto)
                .when()
                .get(PRODUCTS_ENDPOINT + "/search"); // Usando a constante + sub-rota
    }
}