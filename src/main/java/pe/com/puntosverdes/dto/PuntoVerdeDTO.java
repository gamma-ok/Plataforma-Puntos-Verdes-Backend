package pe.com.puntosverdes.dto;

public class PuntoVerdeDTO {
	private Long id;
	private String nombre;
	private String direccion;
	private Double latitud;
	private Double longitud;
	private boolean activo;
	private String creadoPor;

	public PuntoVerdeDTO() {
	}

	public PuntoVerdeDTO(Long id, String nombre, String direccion, Double latitud, Double longitud, boolean activo,
			String creadoPor) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
		this.activo = activo;
		this.creadoPor = creadoPor;
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}
}
