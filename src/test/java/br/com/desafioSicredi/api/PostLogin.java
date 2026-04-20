package br.com.desafioSicredi.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class PostLogin {

    // Adicionado o /auth conforme documentação
    private String endpoint = "/auth/login";

    public Response realizarLogin(String username, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);

        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);
    }
}