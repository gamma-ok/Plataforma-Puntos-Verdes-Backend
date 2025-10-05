package pe.com.puntosverdes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "puntos_verdes")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PuntoVerde {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private String direccion;
	private Double latitud;
	private Double longitud;
	private boolean activo = true;

	private LocalDateTime fechaRegistro = LocalDateTime.now();

	// Usuario creador (admin o municipalidad)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creado_por_id")
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario creadoPor;

	// Relación con entregas
	@OneToMany(mappedBy = "puntoVerde", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({ "puntoVerde" })
	private Set<Entrega> entregas = new HashSet<>();

	public PuntoVerde() {
	}

	public PuntoVerde(String nombre, String direccion, Double latitud, Double longitud, Usuario creadoPor) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.creadoPor = creadoPor;
		this.activo = true;
		this.fechaRegistro = LocalDateTime.now();
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Usuario getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Usuario creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Set<Entrega> getEntregas() {
		return entregas;
	}

	public void setEntregas(Set<Entrega> entregas) {
		this.entregas = entregas;
	}
}
