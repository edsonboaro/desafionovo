package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.tests.BaseTest;
import br.com.desafioSicredi.qa.utils.DataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ProductCreationTest extends BaseTest {

    @Test
    @DisplayName("Deve cadastrar um novo produto com sucesso e validar o ID incremental")
    public void shouldCreateProductSuccessfully() {
        int currentTotal = DataHelper.getTotalProdutosAtual();
        int expectedNextId = currentTotal + 1;

        String payload = """
                {
                    "title": "Novo Produto Automação QA",
                    "price": 150.0,
                    "description": "Descricao do produto de teste",
                    "categoryId": 2
                }
                """;

        given()
                .body(payload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("title", is("Novo Produto Automação QA"))
                .body("id", is(expectedNextId));
    }

    @Test
    @DisplayName("Deve permitir cadastrar produto mesmo sem o título")
    public void shouldHandleProductCreationWithoutTitle() {
        int currentTotal = DataHelper.getTotalProdutosAtual();
        int expectedNextId = currentTotal + 1;

        String partialPayload = """
                {
                    "price": 100.0,
                    "description": "Produto sem titulo"
                }
                """;

        given()
                .body(partialPayload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("id", is(expectedNextId))
                .body("description", is("Produto sem titulo"));
    }

    @Test
    @DisplayName("Deve cadastrar produto com preço igual a zero")
    public void shouldCreateProductWithZeroPrice() {
        String zeroPricePayload = """
                {
                    "title": "Produto Gratis",
                    "price": 0.0,
                    "description": "Teste de limite inferior de preco"
                }
                """;

        given()
                .body(zeroPricePayload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("price", org.hamcrest.Matchers.comparesEqualTo(0));
    }

    @Test
    @DisplayName("Deve retornar erro para JSON malformado")
    public void shouldReturnErrorForMalformedJson() {
        String malformedPayload = "{ 'title': 'JSON invalido' ";

        given()
                .body(malformedPayload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(400);
    }
}