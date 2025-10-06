package pe.com.puntosverdes.exception;

public class UsuarioNotFoundException extends RuntimeException {

	public UsuarioNotFoundException() {
		super("El usuario con ese username no existe en la base de datos.");
	}

	public UsuarioNotFoundException(String mensaje) {
		super(mensaje);
	}
}
