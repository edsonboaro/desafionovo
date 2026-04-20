package br.com.desafioSicredi.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de Autenticação (Login)")
public class PostLoginTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://dummyjson.com";
    }

    @Test
    @DisplayName("CT01 - Login com sucesso (Validando Bug de Contrato e Status)")
    public void deveRealizarLoginComSucesso() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"emilys\", \"password\": \"emilyspass\" }")
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(201)  // Defeito Aberto para ajuste conforme documentação o correto eh 201, porem a API retorna 200
                .body("id", instanceOf(Integer.class))              // Valida se ID é um número inteiro
                .body("username", instanceOf(String.class))         // Valida se username é String
                .body("email", containsString("@"))         // Valida formato básico de e-mail
                .body("username", equalTo("emilys"))
                .body("firstName", not(emptyOrNullString()))
                .body("$", hasKey("token"))
                .body("token", not(emptyOrNullString()));
    }

    @Test
    @DisplayName("CT02 - Tentativa de Login com credenciais inválidas")
    public void deveRetornarErroAoLogarComDadosInvalidos() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{ \"username\": \"usuario_inexistente\", \"password\": \"12345\" }")
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                /* --- ERRO --- */
                .statusCode(400)
                .body("message", equalTo("Invalid credentials"))
                .body("message", instanceOf(String.class));
    }
}