package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class RecompensaDTO {
	private Long id;
	private String nombre;
	private String descripcion;
	private int puntosNecesarios;
	private int stock;
	private boolean activo;
	private String creadoPorNombre;
	private LocalDateTime fechaCreacion;

	public RecompensaDTO(Long id, String nombre, String descripcion, int puntosNecesarios, int stock, boolean activo,
			String creadoPorNombre, LocalDateTime fechaCreacion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.puntosNecesarios = puntosNecesarios;
		this.stock = stock;
		this.activo = activo;
		this.creadoPorNombre = creadoPorNombre;
		this.fechaCreacion = fechaCreacion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPuntosNecesarios() {
		return puntosNecesarios;
	}

	public void setPuntosNecesarios(int puntosNecesarios) {
		this.puntosNecesarios = puntosNecesarios;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getCreadoPorNombre() {
		return creadoPorNombre;
	}

	public void setCreadoPorNombre(String creadoPorNombre) {
		this.creadoPorNombre = creadoPorNombre;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
}
