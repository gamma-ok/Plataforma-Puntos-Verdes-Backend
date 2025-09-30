package pe.com.puntosverdes.dto;

public class EntregaValidacionDTO {
    private boolean validada;
    private int puntosGanados;
    private String respuestaAdmin;
    private String observaciones;

    public boolean isValidada() {
        return validada;
    }

    public void setValidada(boolean validada) {
        this.validada = validada;
    }

    public int getPuntosGanados() {
        return puntosGanados;
    }

    public void setPuntosGanados(int puntosGanados) {
        this.puntosGanados = puntosGanados;
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
}
