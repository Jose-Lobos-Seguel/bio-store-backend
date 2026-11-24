package com.biostore.backend.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "franquicias")
public class Franquicia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    @Size(min = 2, max = 50, message = "El campo debe contener entre 2 y 50 caracteres")
    @NotBlank(message = "El nombre de la franquicia no puede estar vacio")
    private String nombreFranquicia;

    @Column(nullable = false)
    @NotNull(message = "Este valor debe tener un estado")
    //Esto para seguir con la idea de activar productos
    //Desactivar una franquicia desactivara todos los productos de la franquicia
    private String franquiciaActiva;

    @OneToMany(mappedBy = "franquicia")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Producto> productos = new ArrayList<>(); 

}
