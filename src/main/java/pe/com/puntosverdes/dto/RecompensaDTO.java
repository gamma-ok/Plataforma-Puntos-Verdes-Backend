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
	private LocalDateTime fechaActualizacion;

	public RecompensaDTO(Long id, String nombre, String descripcion, int puntosNecesarios, int stock, boolean activo,
			String creadoPorNombre, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.puntosNecesarios = puntosNecesarios;
		this.stock = stock;
		this.activo = activo;
		this.creadoPorNombre = creadoPorNombre;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getPuntosNecesarios() {
		return puntosNecesarios;
	}

	public int getStock() {
		return stock;
	}

	public boolean isActivo() {
		return activo;
	}

	public String getCreadoPorNombre() {
		return creadoPorNombre;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}
}
