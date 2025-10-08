package pe.com.puntosverdes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ajustes_puntos")
public class AjustePuntos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long usuarioId;
	private String realizadoPor;
	private String accion;
	private int cantidad;
	private String motivo;
	private LocalDateTime fechaAjuste;

	public AjustePuntos() {
	}

	public AjustePuntos(Long usuarioId, String realizadoPor, String accion, int cantidad, String motivo) {
		this.usuarioId = usuarioId;
		this.realizadoPor = realizadoPor;
		this.accion = accion;
		this.cantidad = cantidad;
		this.motivo = motivo;
		this.fechaAjuste = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getRealizadoPor() {
		return realizadoPor;
	}

	public void setRealizadoPor(String realizadoPor) {
		this.realizadoPor = realizadoPor;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public LocalDateTime getFechaAjuste() {
		return fechaAjuste;
	}

	public void setFechaAjuste(LocalDateTime fechaAjuste) {
		this.fechaAjuste = fechaAjuste;
	}
}
