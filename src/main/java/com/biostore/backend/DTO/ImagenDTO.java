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
public class ImagenDTO {

    private Integer id;

    @NotBlank(message = "La URL de la imagen no puede estar vacia")
    @Size(max = 500, message = "La URL no puede superar los 500 caracteres")
    private String urlImagen;

    @NotBlank(message = "El texto alternativo no puede estar vacio")
    @Size(min = 2, max = 75, message = "El campo debe contener entre 2 y 75 caracteres")
    private String textoAlternativo;

    @NotNull(message = "El producto es obligatorio")
    private Integer productoId;

}
