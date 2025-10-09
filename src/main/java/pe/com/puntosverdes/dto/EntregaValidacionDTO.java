package pe.com.puntosverdes.dto;

public class EntregaValidacionDTO {
	private String estado;
	private int puntosGanados;
	private String observaciones;
	private String respuestaAdmin;

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
}
