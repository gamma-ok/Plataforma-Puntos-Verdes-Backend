package pe.com.puntosverdes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entregas")
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ciudadano_id", nullable = false)
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario ciudadano;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recolector_id")
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario recolector;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "punto_verde_id", nullable = false)
	private PuntoVerde puntoVerde;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campania_id")
	@JsonIgnoreProperties({ "entregas" })
	private Campania campania;

	private String material;
	private Double cantidad;
	private String unidad;

	private LocalDateTime fechaEntrega = LocalDateTime.now();

	private boolean validada = false;
	private LocalDateTime fechaValidacion;
	private String observaciones;

	private int puntosGanados = 0;

	@ElementCollection
	@CollectionTable(name = "entrega_evidencias", joinColumns = @JoinColumn(name = "entrega_id"))
	@Column(name = "url")
	private List<String> evidencias = new ArrayList<>();

	@Column(length = 2000)
	private String respuestaAdmin;

	private String validadoPor;

	public Entrega() {
	}

	public Entrega(Usuario ciudadano, PuntoVerde puntoVerde, String material, Double cantidad, String unidad) {
		this.ciudadano = ciudadano;
		this.puntoVerde = puntoVerde;
		this.material = material;
		this.cantidad = cantidad;
		this.unidad = unidad;
		this.fechaEntrega = LocalDateTime.now();
		this.validada = false;
		this.puntosGanados = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Usuario ciudadano) {
		this.ciudadano = ciudadano;
	}

	public Usuario getRecolector() {
		return recolector;
	}

	public void setRecolector(Usuario recolector) {
		this.recolector = recolector;
	}

	public PuntoVerde getPuntoVerde() {
		return puntoVerde;
	}

	public void setPuntoVerde(PuntoVerde puntoVerde) {
		this.puntoVerde = puntoVerde;
	}

	public Campania getCampania() {
		return campania;
	}

	public void setCampania(Campania campania) {
		this.campania = campania;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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

	public List<String> getEvidencias() {
		return evidencias;
	}

	public void setEvidencias(List<String> evidencias) {
		this.evidencias = evidencias;
	}

	public String getRespuestaAdmin() {
		return respuestaAdmin;
	}

	public void setRespuestaAdmin(String respuestaAdmin) {
		this.respuestaAdmin = respuestaAdmin;
	}

	public String getValidadoPor() {
		return validadoPor;
	}

	public void setValidadoPor(String validadoPor) {
		this.validadoPor = validadoPor;
	}
}
