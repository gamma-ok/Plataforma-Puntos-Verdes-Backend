package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class CanjeDTO {
	private Long id;
	private String estado;
	private String recompensaNombre;
	private String usuarioNombre;
	private int puntosUsados;
	private LocalDateTime fechaSolicitud;
	private LocalDateTime fechaResolucion;
	private String respuestaAdmin;
	private String motivoRechazo;

	public CanjeDTO() {
	}

	public CanjeDTO(Long id, String estado, String recompensaNombre, String usuarioNombre, int puntosUsados,
			LocalDateTime fechaSolicitud, LocalDateTime fechaResolucion, String respuestaAdmin, String motivoRechazo) {
		this.id = id;
		this.estado = estado;
		this.recompensaNombre = recompensaNombre;
		this.usuarioNombre = usuarioNombre;
		this.puntosUsados = puntosUsados;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaResolucion = fechaResolucion;
		this.respuestaAdmin = respuestaAdmin;
		this.motivoRechazo = motivoRechazo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRecompensaNombre() {
		return recompensaNombre;
	}

	public void setRecompensaNombre(String recompensaNombre) {
		this.recompensaNombre = recompensaNombre;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}

	public int getPuntosUsados() {
		return puntosUsados;
	}

	public void setPuntosUsados(int puntosUsados) {
		this.puntosUsados = puntosUsados;
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
}
