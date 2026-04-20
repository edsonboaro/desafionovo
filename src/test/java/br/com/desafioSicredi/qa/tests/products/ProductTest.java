package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.client.ProductClient;
import br.com.desafioSicredi.qa.model.response.ProductListResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.module.jsv.JsonSchemaValidator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductTest {
    private ProductClient productClient = new ProductClient();

    @Test
    @DisplayName("Deve validar a paginação de produtos")
    public void deveValidarPaginacaoProdutos() {
        int limit = 1;
        int skip = 10;

        // Aqui usamos o seu ProductListResponse para mapear a resposta
        ProductListResponse response = productClient.buscarComPaginacao(limit, skip)
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(ProductListResponse.class);

        // Validação Sênior: conferir se o que a API prometeu, ela entregou
        assertThat(response.getProducts().size(), is(limit));
        assertThat(response.getLimit(), is(limit));
        assertThat(response.getSkip(), is(skip));
    }

    @Test
    @DisplayName("Deve validar contrato de um produto específico")
    public void deveValidarContratoProdutoUnico() {
        new ProductClient().buscarPorId(1)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/product-schema.json"));
    }

    @Test
    @DisplayName("Deve validar comportamento da paginação com limite excessivo")
    public void deveValidarLimiteExcessivo() {
        io.restassured.response.Response response = productClient.listarComParametros(999, 0);
        int totalNoBanco = response.path("total");
        int tamanhoDaLista = response.path("products.size()");

        System.out.println("LOG [Borda]: Solicitado limite 999. Total no banco: " + totalNoBanco + " | Retornou: " + tamanhoDaLista);

        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("products", hasSize(totalNoBanco));
    }

    @Test
    @DisplayName("Deve validar comportamento da API com limite de paginação inválido")
    public void deveValidarLimiteInvalido() {
        productClient.listarComParametros(-100, 0)
                .then()
                .log().all() // Mostra tudo (Headers e Body) no console do IntelliJ
                .statusCode(200);
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao buscar produto inexistente")
    public void deveRetornarListaVaziaBuscaInexistente() {
        productClient.buscarProduto("ProdutoQueNaoExiste123")
                .then()
                .statusCode(200)
                .body("products", hasSize(0))
                .body("total", is(0));
    }

}