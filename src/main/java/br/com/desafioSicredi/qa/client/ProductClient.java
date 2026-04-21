package br.com.desafioSicredi.qa.client;

import br.com.desafioSicredi.qa.specs.RequestSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductClient {

    public Response listarTodos() {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .when()
                .get("/products");
    }

    public Response listarComPaginacao(int limit, int skip) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .when()
                .get("/products");
    }

    public Response buscarPorId(int id) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .when()
                .get("/products/" + id);
    }

    public Response buscarProduto(String termoBusca) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .queryParam("q", termoBusca)
                .when()
                .get("/products/search");
    }

    public Response listarProdutosAutenticados() {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .when()
                .get("/auth/products");
    }

    public Response listarProdutosAutenticados(String token) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/auth/products");
    }

    public Response buscarPorId(String id) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .when()
                .get("/products/" + id);
    }

    public Response criarProduto(Object body) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(body)
                .when()
                .post("/products/add");
    }
}