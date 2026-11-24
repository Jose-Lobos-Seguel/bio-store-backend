package com.biostore.backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "imagenes")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "El url de imagen no puede estar vacio DEBE haber una imagen para crear un producto")
    private String urlImagen;

    @Column(nullable = false, length = 75)
    @Size(min = 2, max = 75, message = "El campo debe contener entre 2 y 75 caracteres")
    @NotBlank(message = "El texto alternativo no puede estar vacio")
    //Ideal por si usamos varias imagenes para un producto tipo posiciones o verciones de una figura
    private String textoAlternativo;

    @ManyToOne
    @JoinColumn(
        name = "producto_id",
        nullable = false)
    @NotNull(message = "El producto es obligatorio")
    private Producto producto;
    
}
