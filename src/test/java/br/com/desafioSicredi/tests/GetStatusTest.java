package br.com.desafioSicredi.tests;

import br.com.desafioSicredi.api.GetStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;

public class GetStatusTest extends BaseTest { // Herda as configurações da BaseTest

    private GetStatus getStatus = new GetStatus();

    @Test
    @DisplayName("Cenário de Sucesso - Validar status da aplicação")
    public void validarGetStatusComSucesso() {
        getStatus.buscarStatusDaAplicacao()
                .then()
                .statusCode(200)
                .body("status", equalTo("ok"));
    }

    @Test
    @DisplayName("Cenário de Exceção - Validar endpoint inexistente")
    public void validarEndpointInexistente() {
        // Testando um caminho que não existe para garantir cobertura de erro
        io.restassured.RestAssured.get("/caminho-errado")
                .then()
                .statusCode(404);
    }
}