package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class UsuarioPerfilDTO {
    private String username;       // visible pero no editable
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String perfil;         // foto de perfil (URI o ruta)
    private Set<String> roles;     // lista de roles del usuario
    private int puntosAcumulados;  // puntos obtenidos
    private LocalDateTime fechaRegistro; // fecha de registro

    // Constructor vacío
    public UsuarioPerfilDTO() {}

    // Constructor con todo
    public UsuarioPerfilDTO(String username, String nombre, String apellido, String email, String celular,
                            String perfil, Set<String> roles, int puntosAcumulados, LocalDateTime fechaRegistro) {
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.perfil = perfil;
        this.roles = roles;
        this.puntosAcumulados = puntosAcumulados;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPerfil() {
        return perfil;
    }
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }
    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
