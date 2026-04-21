package br.com.desafioSicredi.qa.data.factory;

import br.com.desafioSicredi.qa.model.request.ProductRequest;

public class ProductDataFactory {

    public static ProductRequest novoProdutoValido() {
        return ProductRequest.builder()
                .title("iPhone 15 Pro Max")
                .price(999.99)
                .description("Smartphone de última geração")
                .category("smartphones")
                .build();
    }

    public static ProductRequest produtoSemTitulo() {
        return ProductRequest.builder()
                .price(500.0)
                .description("Produto de teste sem titulo")
                .category("Outros")
                .build();
    }

    public static ProductRequest produtoComPrecoZero() {
        return ProductRequest.builder()
                .title("Produto Grátis")
                .price(0.0)
                .description("Teste de limite inferior")
                .category("Outros")
                .build();
    }
}