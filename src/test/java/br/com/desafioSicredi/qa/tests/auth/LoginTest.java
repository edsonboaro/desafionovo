package br.com.desafioSicredi.qa.tests.auth;

import br.com.desafioSicredi.qa.client.AuthClient;
import br.com.desafioSicredi.qa.data.factory.DataFactory;
import br.com.desafioSicredi.qa.model.request.LoginRequest;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class LoginTest {

    private final AuthClient authClient = new AuthClient();

    @Test
    @DisplayName("Deve realizar login com sucesso - Validando Token e Contrato")
    public void deveRealizarLoginComSucesso() {
        LoginRequest loginRequest = DataFactory.gerarLoginValido();

        authClient.login(loginRequest)
                .then()
                .statusCode(200)
                .body("accessToken", notNullValue()) // <--- Mudamos de "token" para "accessToken"
                .body("username", equalTo(loginRequest.getUsername()))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/login-schema.json"));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar login com credenciais inválidas")
    public void deveRetornarErroComCredenciaisInvalidas() {
        LoginRequest loginInvalido = new LoginRequest("usuario_errado", "senha_errada");

        authClient.login(loginInvalido)
                .then()
                .statusCode(400) // A DummyJSON costuma retornar 400 para erro de login
                .body("message", containsString("Invalid credentials"));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar login com campos vazios")
    public void deveRetornarErroCamposVazios() {
        LoginRequest loginVazio = new LoginRequest("", ""); // Username e Senha vazios

        authClient.login(loginVazio)
                .then()
                .statusCode(400) // Bad Request
                .body("message", notNullValue());
    }
}