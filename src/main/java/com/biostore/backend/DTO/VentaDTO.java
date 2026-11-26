package com.biostore.backend.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    private Integer id;

    private Integer usuarioId;

    private Integer direccionId;

    private BigDecimal total;

    private List<Integer> detalles;
}
