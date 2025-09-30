package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class EntregaHistorialDTO {
	private LocalDateTime fechaEntrega;
	private String material;
	private double cantidad;
	private int puntosGanados;
	private String ubicacion;

	public EntregaHistorialDTO(LocalDateTime fechaEntrega, String material, double cantidad, int puntosGanados,
			String ubicacion) {
		this.fechaEntrega = fechaEntrega;
		this.material = material;
		this.cantidad = cantidad;
		this.puntosGanados = puntosGanados;
		this.ubicacion = ubicacion;
	}

	// Getters y setters
	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
}
