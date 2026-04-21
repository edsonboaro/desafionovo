package br.com.desafioSicredi.qa.client;

import br.com.desafioSicredi.qa.model.request.LoginRequest;
import br.com.desafioSicredi.qa.specs.RequestSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public Response login(LoginRequest loginRequest) {
        return given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(loginRequest)
                .when()
                .post("/auth/login");
    }

    public String getToken(String username, String password) {
        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        return login(loginRequest)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    public String getValidAccessToken() {
        return getToken("emilys", "emilyspass");
    }
}