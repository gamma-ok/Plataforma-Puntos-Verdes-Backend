package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class PuntoVerdeDetalleDTO {
	private Long id;
	private String nombre;
	private String direccion;
	private Double latitud;
	private Double longitud;
	private boolean activo;
	private LocalDateTime fechaRegistro;
	private String creadoPorNombre;
	private int totalEntregas;

	public PuntoVerdeDetalleDTO() {
	}

	// Getters y setters
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

	public String getCreadoPorNombre() {
		return creadoPorNombre;
	}

	public void setCreadoPorNombre(String creadoPorNombre) {
		this.creadoPorNombre = creadoPorNombre;
	}

	public int getTotalEntregas() {
		return totalEntregas;
	}

	public void setTotalEntregas(int totalEntregas) {
		this.totalEntregas = totalEntregas;
	}
}
