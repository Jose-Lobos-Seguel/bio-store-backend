package com.biostore.backend.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {

    private Integer id;

    private Integer ventaId;

    private Integer productoId;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;
}
