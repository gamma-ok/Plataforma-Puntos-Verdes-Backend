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

	private LocalDateTime fechaCanje = LocalDateTime.now();
	private int puntosUsados;
	private String estado = "PENDIENTE"; // PENDIENTE, APROBADO, RECHAZADO
	private String respuestaAdmin;

	public Canje() {
	}

	public Canje(Usuario usuario, Recompensa recompensa, int puntosUsados) {
		this.usuario = usuario;
		this.recompensa = recompensa;
		this.puntosUsados = puntosUsados;
	}

	// Getters y Setters
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

	public LocalDateTime getFechaCanje() {
		return fechaCanje;
	}

	public void setFechaCanje(LocalDateTime fechaCanje) {
		this.fechaCanje = fechaCanje;
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
}
