package com.biostore.backend.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FranquiciaDTO {

    private Integer id;

    private String nombreFranquicia;

    private Boolean franquiciaActiva;
    
    private List<Integer> productosIds;
}