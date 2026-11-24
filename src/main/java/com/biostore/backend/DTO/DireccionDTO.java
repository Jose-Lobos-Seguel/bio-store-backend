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
public class DireccionDTO {

    private Integer id;

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 100, message = "La calle no puede superar los 100 caracteres")
    private String calle;

    @NotBlank(message = "El numero es obligatorio")
    @Size(max = 20, message = "El numero no puede superar los 20 caracteres")
    private String numero;

    @Size(max = 50, message = "El complemento no puede superar los 50 caracteres")
    private String complemento;

    @Size(max = 150, message = "La referencia no puede superar los 150 caracteres")
    private String referencia;

    @NotNull(message = "La comuna es obligatoria")
    private Integer comunaId;

    private String nombreComuna;
    private Integer regionId;
    private String nombreRegion;

    @NotNull(message = "El usuario es obligatorio")
    private Integer usuarioId;
}
