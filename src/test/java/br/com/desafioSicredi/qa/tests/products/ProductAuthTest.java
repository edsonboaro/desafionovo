package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.client.AuthClient;
import br.com.desafioSicredi.qa.client.ProductClient;
import br.com.desafioSicredi.qa.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class ProductAuthTest extends BaseTest {

    private final AuthClient authClient = new AuthClient();
    private final ProductClient productClient = new ProductClient();

    @Test
    @DisplayName("Deve impedir a listagem de produtos sem token de autenticação")
    public void shouldReturn401WhenNoTokenIsProvided() {
        productClient.listarProdutosAutenticados()
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Deve impedir o acesso com token inválido")
    public void shouldReturn401WithInvalidToken() {
        productClient.listarProdutosAutenticados("token_invalido_123")
                .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Deve listar produtos com sucesso usando um token válido")
    public void shouldListProductsWithValidToken() {
        String validToken = authClient.getValidAccessToken();

        productClient.listarProdutosAutenticados(validToken)
                .then()
                .statusCode(200)
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0));
    }
}