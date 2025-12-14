package com.kata.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDTO {

    private Integer id;

    @NotBlank(message = "El nombre de la cerveza es requerido")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String name;

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;

    @DecimalMin(value = "0.0", message = "El ABV no puede ser negativo")
    @DecimalMax(value = "100.0", message = "El ABV no puede exceder 100")
    private Double abv;

    @DecimalMin(value = "0.0", message = "El IBU no puede ser negativo")
    @DecimalMax(value = "1000.0", message = "El IBU no puede exceder 1000")
    private Double ibu;

    private Integer breweryId;
    private Integer styleId;
    private Integer categoryId;

    private BreweryDTO brewery;
    private StyleDTO style;
    private CategoryDTO category;
}

