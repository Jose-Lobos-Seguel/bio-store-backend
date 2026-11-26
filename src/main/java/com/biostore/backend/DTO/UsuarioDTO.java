package com.biostore.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 30, message = "El nombre no puede superar los 50 caracteres")
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato valido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 8, max = 8, message = "El telefono debe contener exactamente 8 digitos")
    private String numeroTelefonico;

    @NotNull(message = "El rol es obligatorio")
    private Integer rolId;

    private String nombreRol;
}
