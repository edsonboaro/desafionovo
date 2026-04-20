package br.com.desafioSicredi.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class PostProduct {

    private String endpoint = "/products/add";

    public Response cadastrarProduto(String title, String category, double price) {
        Map<String, Object> body = new HashMap<>();
        body.put("title", title);
        body.put("category", category);
        body.put("price", price);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);
    }
}