package com.biostore.backend.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {

    private Integer id;

    private Integer usuarioId;

    private List<Integer> itemsIds;
}
