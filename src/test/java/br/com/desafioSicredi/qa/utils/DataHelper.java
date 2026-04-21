package br.com.desafioSicredi.qa.utils;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

public class DataHelper {

    /**
     * Obtém o total de produtos dinamicamente.
     * Útil para validar IDs de novos cadastros e paginação.
     */
    public static int getTotalProdutosAtual() {
        return given()
                // Se o seu BaseTest já configurou o RestAssured.requestSpecification,
                // você não precisa passar nada aqui.
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .path("total");
    }
}