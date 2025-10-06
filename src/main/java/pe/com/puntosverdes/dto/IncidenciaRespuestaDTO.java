package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class IncidenciaRespuestaDTO {
	private Long id;
	private String titulo;
	private String descripcion;
	private boolean validada;
	private int puntosGanados;
	private String observaciones;
	private LocalDateTime fechaReporte;
	private LocalDateTime fechaValidacion;
	private String reportadoPorUsername;
	private String rolUsuarioReporte;

	public IncidenciaRespuestaDTO(Long id, String titulo, String descripcion, boolean validada, int puntosGanados,
			String observaciones, LocalDateTime fechaReporte, LocalDateTime fechaValidacion,
			String reportadoPorUsername, String rolUsuarioReporte) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.validada = validada;
		this.puntosGanados = puntosGanados;
		this.observaciones = observaciones;
		this.fechaReporte = fechaReporte;
		this.fechaValidacion = fechaValidacion;
		this.reportadoPorUsername = reportadoPorUsername;
		this.rolUsuarioReporte = rolUsuarioReporte;
	}

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

	public boolean isValidada() {
		return validada;
	}

	public void setValidada(boolean validada) {
		this.validada = validada;
	}

	public int getPuntosGanados() {
		return puntosGanados;
	}

	public void setPuntosGanados(int puntosGanados) {
		this.puntosGanados = puntosGanados;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public LocalDateTime getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(LocalDateTime fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	public LocalDateTime getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(LocalDateTime fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getReportadoPorUsername() {
		return reportadoPorUsername;
	}

	public void setReportadoPorUsername(String reportadoPorUsername) {
		this.reportadoPorUsername = reportadoPorUsername;
	}

	public String getRolUsuarioReporte() {
		return rolUsuarioReporte;
	}

	public void setRolUsuarioReporte(String rolUsuarioReporte) {
		this.rolUsuarioReporte = rolUsuarioReporte;
	}
}
