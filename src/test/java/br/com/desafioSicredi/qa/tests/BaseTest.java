package br.com.desafioSicredi.qa.tests;

import br.com.desafioSicredi.qa.specs.RequestSpecs;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.given;

public class BaseTest {


    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = RequestSpecs.getBasicRequestSpec();

        System.out.println("====== Verificando integridade da API ======");

        try {
            given()
                    .when()
                    .get("/test") // Endpoint da documentação
                    .then()
                    .statusCode(200);

            System.out.println("API Online. Prosseguindo com a execução...");
        } catch (Throwable e) {
            System.err.println("ABORTANDO: API Indisponível ou fora do ar!");
            throw new RuntimeException("Falha no Health Check: " + e.getMessage());
        }
    }
}