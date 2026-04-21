package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.data.factory.ProductDataFactory;
import br.com.desafioSicredi.qa.model.request.ProductRequest;
import br.com.desafioSicredi.qa.specs.RequestSpecs;
import br.com.desafioSicredi.qa.tests.BaseTest;
import br.com.desafioSicredi.qa.utils.DataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductCreationTest extends BaseTest {

    @Test
    @DisplayName("Deve cadastrar um novo produto com sucesso e validar o ID incremental")
    public void shouldCreateProductSuccessfully() {
        int currentTotal = DataHelper.getTotalProdutosAtual();
        int expectedNextId = currentTotal + 1;

        ProductRequest novoProduto = ProductDataFactory.novoProdutoValido();

        given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(novoProduto)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("title", is(novoProduto.getTitle()))
                .body("id", is(expectedNextId));
    }

    @Test
    @DisplayName("Deve permitir cadastrar produto mesmo sem o título")
    public void shouldHandleProductCreationWithoutTitle() {
        int currentTotal = DataHelper.getTotalProdutosAtual();
        int expectedNextId = currentTotal + 1;

        ProductRequest semTitulo = ProductDataFactory.produtoSemTitulo();

        given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(semTitulo)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("id", is(expectedNextId))
                .body("title", anyOf(nullValue(), emptyOrNullString()))
                .body("description", is(semTitulo.getDescription()));
    }

    @Test
    @DisplayName("Deve cadastrar produto com preço igual a zero")
    public void shouldCreateProductWithZeroPrice() {
        ProductRequest gratis = ProductDataFactory.produtoComPrecoZero();

        given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(gratis)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("price", comparesEqualTo(0));
    }

    @Test
    @DisplayName("Deve retornar erro para JSON malformado")
    public void shouldReturnErrorForMalformedJson() {
        String malformedPayload = "{ \"title\": \"JSON invalido\" ";

        given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(malformedPayload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(400)
                .body(notNullValue());
    }

    @Test
    @DisplayName("Deve permitir cadastrar produto com tipo inválido para o campo price")
    public void shouldAllowProductCreationWithInvalidPriceType() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "Produto Teste");
        payload.put("price", "cem");

        given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(payload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("title", is("Produto Teste"))
                .body("price", is("cem"));
    }

    @Test
    @DisplayName("Deve permitir cadastrar produto com valor negativo para o campo price")
    public void shouldAllowProductCreationWithNegativePrice() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "Produto Teste");
        payload.put("price", -100);

        given()
                .spec(RequestSpecs.getBasicRequestSpec())
                .body(payload)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .body("title", is("Produto Teste"))
                .body("price", is(-100));
    }

}