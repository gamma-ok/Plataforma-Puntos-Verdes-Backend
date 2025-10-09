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

	private String material;
	private Double cantidad;
	private String unidad;

	private LocalDateTime fechaEntrega = LocalDateTime.now();
	private String estado = "PENDIENTE";

	private int puntosGanados = 0;
	private String observaciones;
	private String respuestaAdmin;

	private LocalDateTime fechaValidacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ciudadano_id", nullable = false)
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario ciudadano;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recolector_id")
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario recolector;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "punto_verde_id")
	private PuntoVerde puntoVerde;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campania_id")
	@JsonIgnoreProperties({ "entregas" })
	private Campania campania;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "validado_por_id")
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario validadoPor;

	@ElementCollection
	@CollectionTable(name = "entrega_archivos", joinColumns = @JoinColumn(name = "entrega_id"))
	@Column(name = "archivo_nombre")
	private List<String> entregaArchivos = new ArrayList<>();

	public Entrega() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getRespuestaAdmin() {
		return respuestaAdmin;
	}

	public void setRespuestaAdmin(String respuestaAdmin) {
		this.respuestaAdmin = respuestaAdmin;
	}

	public LocalDateTime getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(LocalDateTime fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
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

	public Usuario getValidadoPor() {
		return validadoPor;
	}

	public void setValidadoPor(Usuario validadoPor) {
		this.validadoPor = validadoPor;
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

	public List<String> getEntregaArchivos() {
		return entregaArchivos;
	}

	public void setEntregaArchivos(List<String> entregaArchivos) {
		this.entregaArchivos = entregaArchivos;
	}
}
