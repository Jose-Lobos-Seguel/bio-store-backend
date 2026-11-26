package com.biostore.backend.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 75)
    @Size(min = 5, max = 75, message = "El campo debe contener entre 5 y 75 caracteres")
    @NotBlank(message = "El nombre del producto no puede estar vacio")
    private String nombreProducto;

    @Column(nullable = false, length = 125)
    @Size(min = 5, max = 125, message = "El campo debe contener entre 5 y 125 caracteres")
    @NotBlank(message = "La descripcion no puede estar vacia")
    private String descripcionProducto;

    @Column(nullable = false, precision = 10, scale = 0)
    @NotNull(message = "El precio debe tener un valor")
    private BigDecimal precioProducto;

    @Column(nullable = false)
    @NotNull(message = "El stock debe tener un valor")
    private Integer stockProducto;

    @Column(nullable = false)
    @NotNull(message = "El producto debe estar activado o desactivado")
    //Podria utilizarse para desactivar un producto pero no eliminarlo
    //Asi que no se muestre por razones x
    private Boolean productoActivo;

    @OneToMany(
        mappedBy = "producto",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    //Una lista por si podemos agregar que se muestren varias imagenes en una pagina de producto, varias poses etc
    private List<Imagen> imagenes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
        name = "franquicia:id",
        nullable = false)
    @NotNull(message = "La franquicia es obligatoria")
    private Franquicia franquicia;
}
