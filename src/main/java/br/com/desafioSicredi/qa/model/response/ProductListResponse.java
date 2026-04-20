package br.com.desafioSicredi.qa.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data // <-- Gera automaticamente getProducts(), getLimit() e getSkip()
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse {
    private List<ProductResponse> products;
    private Integer total;
    private Integer skip;
    private Integer limit;
}