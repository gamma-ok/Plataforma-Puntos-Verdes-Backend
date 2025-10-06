package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class EntregaValidadaDTO {
	private Long id;
	private String material;
	private double cantidad;
	private int puntosGanados;
	private boolean validada;
	private String respuestaAdmin;
	private String observaciones;
	private LocalDateTime fechaValidacion;
	private String ciudadanoUsername;
	private String puntoVerdeDireccion;
	private String rolUsuarioEntrega;
	private String validadoPor;

	public EntregaValidadaDTO(Long id, String material, double cantidad, int puntosGanados, boolean validada,
			String respuestaAdmin, String observaciones, LocalDateTime fechaValidacion, String ciudadanoUsername,
			String puntoVerdeDireccion, String rolUsuarioEntrega, String validadoPor) {
		this.id = id;
		this.material = material;
		this.cantidad = cantidad;
		this.puntosGanados = puntosGanados;
		this.validada = validada;
		this.respuestaAdmin = respuestaAdmin;
		this.observaciones = observaciones;
		this.fechaValidacion = fechaValidacion;
		this.ciudadanoUsername = ciudadanoUsername;
		this.puntoVerdeDireccion = puntoVerdeDireccion;
		this.rolUsuarioEntrega = rolUsuarioEntrega;
		this.validadoPor = validadoPor;
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

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public int getPuntosGanados() {
		return puntosGanados;
	}

	public void setPuntosGanados(int puntosGanados) {
		this.puntosGanados = puntosGanados;
	}

	public boolean isValidada() {
		return validada;
	}

	public void setValidada(boolean validada) {
		this.validada = validada;
	}

	public String getRespuestaAdmin() {
		return respuestaAdmin;
	}

	public void setRespuestaAdmin(String respuestaAdmin) {
		this.respuestaAdmin = respuestaAdmin;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public LocalDateTime getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(LocalDateTime fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getCiudadanoUsername() {
		return ciudadanoUsername;
	}

	public void setCiudadanoUsername(String ciudadanoUsername) {
		this.ciudadanoUsername = ciudadanoUsername;
	}

	public String getPuntoVerdeDireccion() {
		return puntoVerdeDireccion;
	}

	public void setPuntoVerdeDireccion(String puntoVerdeDireccion) {
		this.puntoVerdeDireccion = puntoVerdeDireccion;
	}

	public String getRolUsuarioEntrega() {
		return rolUsuarioEntrega;
	}

	public void setRolUsuarioEntrega(String rolUsuarioEntrega) {
		this.rolUsuarioEntrega = rolUsuarioEntrega;
	}

	public String getValidadoPor() {
		return validadoPor;
	}

	public void setValidadoPor(String validadoPor) {
		this.validadoPor = validadoPor;
	}
}
