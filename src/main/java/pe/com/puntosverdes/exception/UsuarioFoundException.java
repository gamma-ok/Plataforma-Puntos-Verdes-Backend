package pe.com.puntosverdes.exception;

public class UsuarioFoundException extends RuntimeException {

    public UsuarioFoundException() {
        super("El usuario con ese username ya existe en la base de datos.");
    }

    public UsuarioFoundException(String mensaje) {
        super(mensaje);
    }
}
