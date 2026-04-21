package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.tests.BaseTest;
import br.com.desafioSicredi.qa.utils.DataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductGetTest extends BaseTest {

    @Test
    @DisplayName("Deve listar todos os produtos e validar estrutura da resposta")
    public void shouldListAllProducts() {
        int totalEsperado = DataHelper.getTotalProdutosAtual();

        given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("products", hasSize(greaterThan(0)))
                .body("total", is(totalEsperado));
    }

    @Test
    @DisplayName("Deve validar a paginação de produtos usando limit e skip")
    public void shouldValidateProductPagination() {
        int limit = 10;
        int skip = 5;

        given()
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("products", hasSize(limit))
                .body("skip", is(skip));
    }

    @Test
    @DisplayName("Deve validar o retorno do último produto da lista via paginação")
    public void shouldValidateLastProductViaPagination() {
        int total = DataHelper.getTotalProdutosAtual();
        int skip = total - 1;

        given()
                .queryParam("limit", 1)
                .queryParam("skip", skip)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("products", hasSize(1))
                .body("skip", is(skip));
    }
}