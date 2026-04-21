package br.com.desafioSicredi.qa.tests;

import br.com.desafioSicredi.qa.specs.RequestSpecs;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.given;

public class BaseTest {


    @BeforeAll
    public static void setup() {
        // 1. Vincula o seu RequestSpecs globalmente
        // Agora você não precisa chamar given(RequestSpecs.requestSpec()) em todo teste
        RestAssured.requestSpecification = RequestSpecs.getBasicRequestSpec();

        // 2. SMOKE TEST / HEALTH CHECK
        // Valida se o endpoint /test da imagem que você mandou está respondendo
        System.out.println("====== Verificando integridade da API ======");

        try {
            given()
                    .when()
                    .get("/test") // Endpoint da documentação
                    .then()
                    .statusCode(200);

            System.out.println("✅ API Online. Prosseguindo com a execução...");
        } catch (Throwable e) {
            System.err.println("❌ ABORTANDO: API Indisponível ou fora do ar!");
            // Interrompe tudo se o ambiente não estiver pronto
            throw new RuntimeException("Falha no Health Check: " + e.getMessage());
        }
    }
}