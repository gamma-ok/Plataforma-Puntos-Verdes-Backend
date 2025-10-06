package pe.com.puntosverdes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "canjes")
public class Canje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recompensa_id", nullable = false)
	private Recompensa recompensa;

	private LocalDateTime fechaSolicitud = LocalDateTime.now();

	private LocalDateTime fechaResolucion;

	private int puntosUsados;

	private String estado = "PENDIENTE";

	@Column(length = 1000)
	private String respuestaAdmin;

	@Column(length = 1000)
	private String motivoRechazo;

	private String resueltoPor;

	public Canje() {
	}

	public Canje(Usuario usuario, Recompensa recompensa, int puntosUsados) {
		this.usuario = usuario;
		this.recompensa = recompensa;
		this.puntosUsados = puntosUsados;
		this.fechaSolicitud = LocalDateTime.now();
		this.estado = "PENDIENTE";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Recompensa getRecompensa() {
		return recompensa;
	}

	public void setRecompensa(Recompensa recompensa) {
		this.recompensa = recompensa;
	}

	public LocalDateTime getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public LocalDateTime getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(LocalDateTime fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public int getPuntosUsados() {
		return puntosUsados;
	}

	public void setPuntosUsados(int puntosUsados) {
		this.puntosUsados = puntosUsados;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRespuestaAdmin() {
		return respuestaAdmin;
	}

	public void setRespuestaAdmin(String respuestaAdmin) {
		this.respuestaAdmin = respuestaAdmin;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getResueltoPor() {
		return resueltoPor;
	}

	public void setResueltoPor(String resueltoPor) {
		this.resueltoPor = resueltoPor;
	}
}
