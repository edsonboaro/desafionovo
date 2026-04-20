package br.com.desafioSicredi.qa.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // IMPORTANTE
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // <-- ADICIONE ESTA LINHA
public class ProductResponse {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String category;
    // se você tiver o campo 'image' ou 'thumbnail', mantenha-os
}