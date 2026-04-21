package br.com.desafioSicredi.qa.tests.security;

import br.com.desafioSicredi.qa.client.AuthClient;
import br.com.desafioSicredi.qa.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class ProductAuthTest extends BaseTest {

    private final AuthClient authClient = new AuthClient();

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
        String validToken = authClient.getValidAccessToken();

        given()
                .header("Authorization", "Bearer " + validToken)
                .when()
                .get("/auth/products")
                .then()
                .statusCode(200)
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0));
    }
}