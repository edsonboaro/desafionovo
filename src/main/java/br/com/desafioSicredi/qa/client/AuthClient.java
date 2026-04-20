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
        LoginRequest login = LoginRequest.builder()
                .username("kminchelle")
                .password("0lel09me")
                .build();
        return login(login).then().extract().path("token");
    }
}