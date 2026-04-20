package br.com.desafioSicredi.tests;

import br.com.desafioSicredi.api.GetProducts;
import br.com.desafioSicredi.api.PostLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class GetProductsTest extends BaseTest {

    private GetProducts getProducts = new GetProducts();
    private PostLogin login = new PostLogin();
    private String token;

    @BeforeEach
    public void prepararToken() {
        // Pegamos o token dinamicamente antes de cada teste de produto
        token = login.realizarLogin("emilys", "emilyspass")
                .then()
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Cenário de Sucesso - Listar produtos com token válido")
    public void deveListarProdutosComSucesso() {
        getProducts.listarProdutos(token)
                .then()
                .log().all()
                .statusCode(200)
                .body("products", notNullValue())
                .body("products[0].title", notNullValue());
    }

    @Test
    @DisplayName("Cenário de Exceção - Tentar listar produtos sem token")
    public void deveRetornarErroAoListarSemToken() {
        getProducts.listarProdutosSemToken()
                .then()
                .log().all()
                .statusCode(401); // Use 401, pois não estava na documentação o correto ou eu não encontrei
    }
}