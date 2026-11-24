package com.biostore.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaDTO {

    private Integer id;

    @NotBlank(message = "El nombre de la comuna no puede estar vacio")
    @Size(min = 2, max = 50, message = "El campo debe contener entre 2 y 50 caracteres")
    private String nombreComuna;

    @NotNull(message = "La region es obligatoria")
    private Integer regionId;

    private String nombreRegion;
}
