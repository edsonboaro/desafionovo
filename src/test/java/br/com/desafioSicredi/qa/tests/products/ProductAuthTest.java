package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ProductAuthTest extends BaseTest {

    @Test
    @DisplayName("Deve impedir a listagem de produtos sem token de autenticação")
    public void shouldReturn401WhenNoTokenIsProvided() {
        given()
                .when()
                .get("/auth/products")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Deve impedir o acesso com token inválido")
    public void shouldReturn401WithInvalidToken() {
        given()
                .header("Authorization", "Bearer token_invalido_123")
                .when()
                .get("/auth/products")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Deve listar produtos com sucesso usando um token válido")
    public void shouldListProductsWithValidToken() {
        String validToken = given()
                .contentType("application/json")
                .body("{ \"username\": \"emilys\", \"password\": \"emilyspass\" }")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().path("accessToken");

        given()
                .header("Authorization", "Bearer " + validToken)
                .when()
                .get("/auth/products")
                .then()
                .statusCode(200)
                .body("products", notNullValue());
    }
}