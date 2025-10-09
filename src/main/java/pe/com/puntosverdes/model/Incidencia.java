package pe.com.puntosverdes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incidencias")
public class Incidencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	@Column(length = 2000)
	private String descripcion;

	private LocalDateTime fechaReporte = LocalDateTime.now();

	private String estado = "PENDIENTE";

	private String observacionAdmin;
	private String respuesta;

	private LocalDateTime fechaRespuesta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reportado_por_id", nullable = false)
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario reportadoPor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "validado_por_id")
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario validadoPor;

	@ElementCollection
	@CollectionTable(name = "incidencia_archivos", joinColumns = @JoinColumn(name = "incidencia_id"))
	@Column(name = "archivo_nombre")
	private List<String> incidenciasArchivos = new ArrayList<>();

	public Incidencia() {
	}

	public Incidencia(String titulo, String descripcion, Usuario reportadoPor) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.reportadoPor = reportadoPor;
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

	public LocalDateTime getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(LocalDateTime fechaReporte) {
		this.fechaReporte = fechaReporte;
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

	public LocalDateTime getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public Usuario getReportadoPor() {
		return reportadoPor;
	}

	public void setReportadoPor(Usuario reportadoPor) {
		this.reportadoPor = reportadoPor;
	}

	public Usuario getValidadoPor() {
		return validadoPor;
	}

	public void setValidadoPor(Usuario validadoPor) {
		this.validadoPor = validadoPor;
	}

	public List<String> getIncidenciasArchivos() {
		return incidenciasArchivos;
	}

	public void setIncidenciasArchivos(List<String> incidenciasArchivos) {
		this.incidenciasArchivos = incidenciasArchivos;
	}
}
