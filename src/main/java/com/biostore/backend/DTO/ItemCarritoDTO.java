package com.biostore.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarritoDTO {

    private Integer id;

    private Integer carritoId;

    private Integer productoId;

    private Integer cantidad;
}
