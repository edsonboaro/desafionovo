package br.com.desafioSicredi.qa.data.factory;

import br.com.desafioSicredi.qa.model.request.LoginRequest;
import br.com.desafioSicredi.qa.model.request.ProductRequest;
import com.github.javafaker.Faker;

public class DataFactory {
    private static final Faker faker = new Faker();

    public static LoginRequest gerarLoginValido() {
        // Dados padrão da API DummyJSON
        return new LoginRequest("emilys", "emilyspass");
    }

    public static ProductRequest gerarNovoProduto() {
        return ProductRequest.builder()
                .title(faker.commerce().productName())
                .price(Double.parseDouble(faker.commerce().price().replace(",", ".")))
                .category("electronic")
                .description(faker.lorem().sentence())
                .build();
    }
}