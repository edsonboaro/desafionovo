package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ProductIdTest extends BaseTest {

    @Test
    @DisplayName("Deve buscar um produto específico por ID com sucesso")
    public void shouldFindProductById() {
        given()
                .pathParam("id", 1)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("title", notNullValue());
    }

    @Test
    @DisplayName("Deve retornar erro 404 ao buscar ID inexistente")
    public void shouldReturn404WhenProductDoesNotExist() {
        given()
                .pathParam("id", 9999)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(404)
                .body("message", is("Product with id '9999' not found"));
    }
}