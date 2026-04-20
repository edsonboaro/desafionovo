package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.client.ProductClient;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class ProductContractTest {
    @Test
    public void deveValidarContratoDaListaDeProdutos() {
        new ProductClient().listarTodos()
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/product-list-schema.json"));
    }

}