package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class PuntoVerdeDTO {
	private Long id;
	private String nombre;
	private String direccion;
	private String descripcion;
	private Double latitud;
	private Double longitud;
	private boolean activo;
	private String creadoPor;
	private LocalDateTime fechaRegistro;
	private LocalDateTime fechaActualizacion;

	public PuntoVerdeDTO() {
	}

	public PuntoVerdeDTO(Long id, String nombre, String direccion, String descripcion, Double latitud, Double longitud,
			boolean activo, String creadoPor, LocalDateTime fechaRegistro, LocalDateTime fechaActualizacion) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.descripcion = descripcion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.activo = activo;
		this.creadoPor = creadoPor;
		this.fechaRegistro = fechaRegistro;
		this.fechaActualizacion = fechaActualizacion;
	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}
