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
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 75)
    @Size(min = 2, max = 75, message = "El campo debe contener entre 2 y 75 caracteres")
    @NotBlank(message = "El nombre de calle o pasaje no puede estar vacio")
    private String nombreCalle;

    @Column(nullable = false, length = 10)
    @Size(min = 1, max = 10, message = "El campo debe contener entre 1 y 10 caracteres")
    @NotBlank(message = "Este campo no puede estar vacio")
    private String numeroVivienda;

    @Column(nullable = false, length = 50)
    @Size(min = 2, max = 50, message = "El campo debe contener entre 2 y 50 caracteres")
    @NotBlank(message = "El nombre de vivienda no puede estar vacio")
    private String tipoVivienda;

    @Column(nullable = false, length = 100)
    @Size(min = 2, max = 50, message = "El campo debe contener entre 2 y 100 caracteres")
    private String referencia;

    @ManyToOne
    @JoinColumn(
        name = "comuna_id",
        nullable = false)
    @NotNull(message = "La comuna es obligatoria")
    private Comuna comuna;

    @ManyToOne
    @JoinColumn(
        name = "usuario_id",
        nullable = false)
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

}
