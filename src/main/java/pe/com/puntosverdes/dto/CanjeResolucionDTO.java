package pe.com.puntosverdes.dto;

public class CanjeResolucionDTO {

	private boolean aceptado;
	private String motivo;
	private String respuestaAdmin;
	private String resueltoPor;

	public boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getRespuestaAdmin() {
		return respuestaAdmin;
	}

	public void setRespuestaAdmin(String respuestaAdmin) {
		this.respuestaAdmin = respuestaAdmin;
	}

	public String getResueltoPor() {
		return resueltoPor;
	}

	public void setResueltoPor(String resueltoPor) {
		this.resueltoPor = resueltoPor;
	}
}
