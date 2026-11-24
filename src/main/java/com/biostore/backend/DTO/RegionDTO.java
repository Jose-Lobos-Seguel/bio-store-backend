package com.biostore.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {

    private Integer id;

    @NotBlank(message = "El nombre de la region no puede estar vacio")
    @Size(min = 2, max = 50, message = "El campo debe contener entre 2 y 50 caracteres")
    private String nombreRegion;
}
