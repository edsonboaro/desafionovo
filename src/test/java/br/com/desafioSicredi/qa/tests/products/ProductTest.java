package br.com.desafioSicredi.qa.tests.products;

import br.com.desafioSicredi.qa.client.ProductClient;
import br.com.desafioSicredi.qa.model.response.ProductListResponse;
import br.com.desafioSicredi.qa.tests.BaseTest;
import br.com.desafioSicredi.qa.utils.DataHelper;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProductTest extends BaseTest {

    private final ProductClient productClient = new ProductClient();

    @Test
    @DisplayName("Deve validar a paginação de produtos com limit e skip válidos")
    void shouldValidateProductPagination() {
        int limit = 1;
        int skip = 10;

        ProductListResponse response = productClient.listarComPaginacao(limit, skip)
                .then()
                .statusCode(200)
                .extract()
                .as(ProductListResponse.class);

        assertThat("A quantidade de produtos deve respeitar o limit informado",
                response.getProducts().size(), is(limit));
        assertThat("O campo limit da resposta deve ser igual ao enviado",
                response.getLimit(), is(limit));
        assertThat("O campo skip da resposta deve ser igual ao enviado",
                response.getSkip(), is(skip));
    }

    @Test
    @DisplayName("Deve validar a paginação dinâmica com base no total de produtos")
    void shouldValidateDynamicProductPagination() {
        int totalProducts = DataHelper.getTotalProdutosAtual();
        int limit = 5;
        int skip = totalProducts / 2;

        productClient.listarComPaginacao(limit, skip)
                .then()
                .statusCode(200)
                .body("products", hasSize(lessThanOrEqualTo(limit)))
                .body("limit", is(limit))
                .body("skip", is(skip))
                .body("total", is(totalProducts));
    }

    @Test
    @DisplayName("Deve validar comportamento quando o limit é maior que o total de produtos")
    void shouldValidateExcessiveLimitBehavior() {
        int totalProducts = DataHelper.getTotalProdutosAtual();

        ProductListResponse response = productClient.listarComPaginacao(999, 0)
                .then()
                .statusCode(200)
                .extract()
                .as(ProductListResponse.class);

        assertThat("O total retornado deve ser igual ao total atual da API",
                response.getTotal(), is(totalProducts));
        assertThat("A lista retornada não deve exceder o total de produtos",
                response.getProducts().size(), lessThanOrEqualTo(totalProducts));
        assertThat("A lista de produtos não deve estar vazia",
                response.getProducts(), is(not(empty())));
    }

    @Test
    @DisplayName("Deve validar comportamento da API quando o limit é inválido")
    void shouldValidateInvalidLimitBehavior() {
        ProductListResponse response = productClient.listarComPaginacao(-100, 0)
                .then()
                .statusCode(200)
                .extract()
                .as(ProductListResponse.class);

        assertThat("A resposta não deve ser nula mesmo com limit inválido",
                response, is(notNullValue()));
        assertThat("A lista de produtos não deve ser nula",
                response.getProducts(), is(notNullValue()));
        assertThat("O tamanho da lista deve ser maior ou igual a zero",
                response.getProducts().size(), greaterThanOrEqualTo(0));
        assertThat("O total deve ser maior ou igual a zero",
                response.getTotal(), greaterThanOrEqualTo(0));
    }

    @Test
    @DisplayName("Deve buscar produtos por termo existente com sucesso")
    void shouldSearchProductByExistingTermSuccessfully() {
        String existingSearchTerm = "phone";

        productClient.buscarProduto(existingSearchTerm)
                .then()
                .statusCode(200)
                .body("products", is(not(empty())))
                .body("products.title.flatten()", hasItem(containsStringIgnoringCase(existingSearchTerm)));
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao buscar produto inexistente")
    void shouldReturnEmptyListForNonExistingProductSearch() {
        productClient.buscarProduto("ProdutoQueNaoExiste123")
                .then()
                .statusCode(200)
                .body("products", hasSize(0))
                .body("total", is(0))
                .body("skip", is(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando o skip for maior que o total de produtos")
    void shouldReturnEmptyListWhenSkipIsGreaterThanTotal() {
        int totalProducts = DataHelper.getTotalProdutosAtual();
        int invalidSkip = totalProducts + 100;

        productClient.listarComPaginacao(10, invalidSkip)
                .then()
                .statusCode(200)
                .body("products", hasSize(0))
                .body("total", is(totalProducts))
                .body("skip", is(invalidSkip));
    }
}