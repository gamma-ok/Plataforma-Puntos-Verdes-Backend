package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class CampaniaDTO {

	private Long id;
	private String titulo;
	private String descripcion;
	private String ubicacion;
	private boolean activa;
	private int puntosExtra;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private String creadoPorNombre;

	public CampaniaDTO(Long id, String titulo, String descripcion, String ubicacion, boolean activa, int puntosExtra,
			LocalDateTime fechaInicio, LocalDateTime fechaFin, String creadoPorNombre) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.ubicacion = ubicacion;
		this.activa = activa;
		this.puntosExtra = puntosExtra;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.creadoPorNombre = creadoPorNombre;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public boolean isActiva() {
		return activa;
	}

	public int getPuntosExtra() {
		return puntosExtra;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public String getCreadoPorNombre() {
		return creadoPorNombre;
	}
}
