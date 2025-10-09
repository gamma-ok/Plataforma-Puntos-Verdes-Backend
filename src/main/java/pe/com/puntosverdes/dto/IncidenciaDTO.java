package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;
import java.util.List;

public class IncidenciaDTO {
	private Long id;
	private String titulo;
	private String descripcion;
	private String estado;
	private String observacionAdmin;
	private String respuesta;
	private LocalDateTime fechaReporte;
	private LocalDateTime fechaRespuesta;
	private String reportadoPor;
	private String rolReportadoPor;
	private String validadoPor;
	private String rolValidadoPor;
	private List<String> incidenciasArchivos;

	public IncidenciaDTO() {
	}

	public IncidenciaDTO(Long id, String titulo, String descripcion, String estado, String observacionAdmin,
			String respuesta, LocalDateTime fechaReporte, LocalDateTime fechaRespuesta, String reportadoPor,
			String rolReportadoPor, String validadoPor, String rolValidadoPor, List<String> incidenciasArchivos) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.observacionAdmin = observacionAdmin;
		this.respuesta = respuesta;
		this.fechaReporte = fechaReporte;
		this.fechaRespuesta = fechaRespuesta;
		this.reportadoPor = reportadoPor;
		this.rolReportadoPor = rolReportadoPor;
		this.validadoPor = validadoPor;
		this.rolValidadoPor = rolValidadoPor;
		this.incidenciasArchivos = incidenciasArchivos;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacionAdmin() {
		return observacionAdmin;
	}

	public void setObservacionAdmin(String observacionAdmin) {
		this.observacionAdmin = observacionAdmin;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public LocalDateTime getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(LocalDateTime fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	public LocalDateTime getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getReportadoPor() {
		return reportadoPor;
	}

	public void setReportadoPor(String reportadoPor) {
		this.reportadoPor = reportadoPor;
	}

	public String getRolReportadoPor() {
		return rolReportadoPor;
	}

	public void setRolReportadoPor(String rolReportadoPor) {
		this.rolReportadoPor = rolReportadoPor;
	}

	public String getValidadoPor() {
		return validadoPor;
	}

	public void setValidadoPor(String validadoPor) {
		this.validadoPor = validadoPor;
	}

	public String getRolValidadoPor() {
		return rolValidadoPor;
	}

	public void setRolValidadoPor(String rolValidadoPor) {
		this.rolValidadoPor = rolValidadoPor;
	}

	public List<String> getIncidenciasArchivos() {
		return incidenciasArchivos;
	}

	public void setIncidenciasArchivos(List<String> incidenciasArchivos) {
		this.incidenciasArchivos = incidenciasArchivos;
	}
}
