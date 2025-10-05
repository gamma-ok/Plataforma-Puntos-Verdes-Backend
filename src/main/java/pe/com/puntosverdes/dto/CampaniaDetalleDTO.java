package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CampaniaDetalleDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private boolean activa;
    private int puntosExtra;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String creadoPorNombre;
    private String creadoPorRol;
    private List<String> entregas;

    public CampaniaDetalleDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public int getPuntosExtra() {
		return puntosExtra;
	}

	public void setPuntosExtra(int puntosExtra) {
		this.puntosExtra = puntosExtra;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCreadoPorNombre() {
		return creadoPorNombre;
	}

	public void setCreadoPorNombre(String creadoPorNombre) {
		this.creadoPorNombre = creadoPorNombre;
	}

	public String getCreadoPorRol() {
		return creadoPorRol;
	}

	public void setCreadoPorRol(String creadoPorRol) {
		this.creadoPorRol = creadoPorRol;
	}

	public List<String> getEntregas() {
		return entregas;
	}

	public void setEntregas(List<String> entregas) {
		this.entregas = entregas;
	}
}
