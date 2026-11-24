package com.biostore.backend.DTO;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Integer id;

    @NotBlank(message = "El nombre del producto no puede estar vacio")
    @Size(min = 3, max = 75, message = "El campo debe contener entre 3 y 75 caracteres")
    private String nombreProducto;

    @NotBlank(message = "La descripcion no puede estar vacia")
    @Size(min = 10, max = 125, message = "El campo debe contener entre 10 y 125 caracteres")
    private String descripcionProducto;

    @NotNull(message = "El precio debe tener un valor")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precioProducto;

    @NotNull(message = "El stock debe tener un valor")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stockProducto;

    @NotNull(message = "El producto debe estar activado o desactivado")
    private Boolean productoActivo;

    @NotNull(message = "La franquicia es obligatoria")
    private Integer franquiciaId;

    private String nombreFranquicia;

    private List<String> imagenes;
}
