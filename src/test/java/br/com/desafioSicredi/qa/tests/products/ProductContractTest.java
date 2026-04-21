package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.client.ProductClient;
import br.com.desafioSicredi.qa.tests.BaseTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductContractTest extends BaseTest {

    private final ProductClient productClient = new ProductClient();

    @Test
    @DisplayName("Deve validar o contrato da lista de produtos")
    void shouldValidateProductListContract() {
        productClient.listarTodos()
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/product-list-schema.json"));
    }

    @Test
    @DisplayName("Deve validar o contrato de um produto específico")
    void shouldValidateSingleProductContract() {
        productClient.buscarPorId(1)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/product-schema.json"));
    }
}