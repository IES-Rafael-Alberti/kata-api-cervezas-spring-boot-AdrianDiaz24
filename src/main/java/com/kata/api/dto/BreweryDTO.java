package com.kata.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BreweryDTO {

    private Integer id;

    @NotBlank(message = "El nombre de la cervecería es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String address;

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String city;

    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    private String country;

    @Pattern(regexp = "^[+]?[0-9]{1,3}?[-.\\s]?[(]?[0-9]{1,4}[)]?[-.\\s]?[0-9]{1,4}[-.\\s]?[0-9]{1,9}$|^$",
            message = "El teléfono debe tener un formato válido")
    private String phone;

    @URL(message = "El sitio web debe ser una URL válida")
    private String website;

    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres")
    private String description;
}

