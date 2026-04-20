package br.com.desafioSicredi.qa.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {
    public static RequestSpecification getBasicRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com") // URL base da API do desafio
                .setContentType(ContentType.JSON)
                .build();
    }
}