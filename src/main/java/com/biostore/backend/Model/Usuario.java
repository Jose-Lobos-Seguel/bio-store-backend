package com.biostore.backend.Model;

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
import jakarta.validation.constraints.Email;
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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    @Size(min = 8, max = 30, message = "El campo debe contener entre 8 y 30 caracteres")
    @NotBlank(message = "Este campo no puede estar vacio")
    private String password;

    @Column(nullable = false, length = 150)
    @Size(min = 2, max = 50, message = "El campo debe contener entre 2 y 150 caracteres")
    @NotBlank(message = "Este campo no puede estar vacio")
    private String nombre;

    @Column(nullable = false, unique = true)
    @Email(message = "El formato del correo electronico no es valido")
    @NotBlank(message = "El correo electronico es obligatorio")
    private String email;

    @Column(nullable = false, length = 8)
    @Size(min = 8, max = 8)
    @NotBlank(message = "Este campo no puede estar vacio")
    private String numeroTelefonico;

    @OneToMany(
        mappedBy = "usuario",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Direccion> direcciones = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
        name = "rol_id",
        nullable = false)
    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}
