package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class UsuarioPerfilDTO {
	private Long id;
	private String username;
	private String nombre;
	private String apellido;
	private String email;
	private String celular;
	private String perfil;
	private Set<String> roles;
	private int puntosAcumulados;
	private LocalDateTime fechaRegistro;
	private boolean enabled;

	public UsuarioPerfilDTO() {
	}

	public UsuarioPerfilDTO(Long id, String username, String nombre, String apellido, String email, String celular,
			String perfil, Set<String> roles, int puntosAcumulados, LocalDateTime fechaRegistro, boolean enabled) {
		this.id = id;
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.celular = celular;
		this.perfil = perfil;
		this.roles = roles;
		this.puntosAcumulados = puntosAcumulados;
		this.fechaRegistro = fechaRegistro;
		this.enabled = enabled;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
