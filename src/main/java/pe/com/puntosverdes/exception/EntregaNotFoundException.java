package pe.com.puntosverdes.exception;

public class EntregaNotFoundException extends RuntimeException {
    public EntregaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
