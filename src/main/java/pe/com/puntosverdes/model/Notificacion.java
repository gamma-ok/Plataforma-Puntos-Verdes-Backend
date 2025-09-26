package pe.com.puntosverdes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;
	@Column(length = 2000)
	private String mensaje;

	private LocalDateTime fechaEnvio = LocalDateTime.now();

	/**
	 * Destinatario null => notificación global / campaña a todos. Si no es null =>
	 * notificación dirigida a un usuario en particular.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destinatario_id")
	@JsonIgnoreProperties({ "usuarioRoles" })
	private Usuario destinatario;

	private boolean leida = false;
	private boolean esCampana = false; // si es parte de una campaña masiva

	public Notificacion() {
	}

	public Notificacion(String titulo, String mensaje, Usuario destinatario, boolean esCampana) {
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.destinatario = destinatario;
		this.esCampana = esCampana;
		this.fechaEnvio = LocalDateTime.now();
		this.leida = false;
	}

	// Getters & Setters
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public boolean isLeida() {
		return leida;
	}

	public void setLeida(boolean leida) {
		this.leida = leida;
	}

	public boolean isEsCampana() {
		return esCampana;
	}

	public void setEsCampana(boolean esCampana) {
		this.esCampana = esCampana;
	}
}
