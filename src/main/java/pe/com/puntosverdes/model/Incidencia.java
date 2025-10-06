package pe.com.puntosverdes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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

	private boolean validada = false;
	private LocalDateTime fechaValidacion;

	private String observaciones;
	private int puntosGanados = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reportado_por_id", nullable = false)
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario reportadoPor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "validado_por_id")
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario validadoPor;

	@ElementCollection
	@CollectionTable(name = "incidencia_evidencias", joinColumns = @JoinColumn(name = "incidencia_id"))
	@Column(name = "url")
	private java.util.List<String> evidencias = new java.util.ArrayList<>();

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

	public boolean isValidada() {
		return validada;
	}

	public void setValidada(boolean validada) {
		this.validada = validada;
	}

	public LocalDateTime getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(LocalDateTime fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getPuntosGanados() {
		return puntosGanados;
	}

	public void setPuntosGanados(int puntosGanados) {
		this.puntosGanados = puntosGanados;
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

	public java.util.List<String> getEvidencias() {
		return evidencias;
	}

	public void setEvidencias(java.util.List<String> evidencias) {
		this.evidencias = evidencias;
	}
}
