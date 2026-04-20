package br.com.desafioSicredi.tests;

import br.com.desafioSicredi.api.PostProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class PostProductTest extends BaseTest {

    private PostProduct postProduct = new PostProduct();

    @Test
    @DisplayName("Cenário de Sucesso - Cadastrar novo produto")
    public void deveCadastrarProdutoComSucesso() {
        postProduct.cadastrarProduto("Perfume Sicredi", "fragrances", 150.00)
                .then()
                .log().all()
                .statusCode(201) // 201 Created é o padrão para POST de criação
                .body("id", notNullValue())
                .body("title", equalTo("Perfume Sicredi"));
    }

    @Test
    @DisplayName("Cenário de Exceção - Tentar cadastrar sem dados obrigatórios")
    public void deveValidarErroAoCadastrarProdutoVazio() {
        // Testando enviar um corpo vazio para ver como a API se comporta
        io.restassured.RestAssured.given()
                .contentType("application/json")
                .body("{}")
                .post("/products/add")
                .then()
                .log().all()
                .statusCode(400); // Ou o que a API retornar para erro de validação
    }
}