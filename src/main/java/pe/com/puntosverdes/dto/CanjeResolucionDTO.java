package pe.com.puntosverdes.dto;

public class CanjeResolucionDTO {

    private boolean aceptado;        // true = aprobado, false = rechazado
    private String motivo;           // motivo en caso de rechazo
    private String respuestaAdmin;   // comentario o mensaje del admin
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
