package AyDS2.TP2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

    @NotBlank(message = "no puede estar vacío")
    @Size(min = 2, message = "debe tener al menos 2 caracteres")
    private String nombre;

    @NotBlank(message = "no puede estar vacío")
    @Size(min = 2, message = "debe tener al menos 2 caracteres")
    private String apellido;

    @NotBlank(message = "es obligatorio")
    @Email(message = "debe ser un email válido")
    private String email;

    @Pattern(regexp = "^[0-9]+$", message = "solo debe contener dígitos")
    private String telefono;

    public ClienteDTO() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}