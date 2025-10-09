package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EntregaDTO {
	private Long id;
	private String material;
	private Double cantidad;
	private String unidad;
	private String estado;
	private int puntosGanados;
	private String observaciones;
	private String respuestaAdmin;
	private LocalDateTime fechaEntrega;
	private LocalDateTime fechaValidacion;
	private String ciudadanoNombre;
	private String ciudadanoRol;
	private String validadoPor;
	private String rolValidador;
	private String puntoVerdeNombre;
	private String puntoVerdeDireccion;
	private String campaniaTitulo;
	private String campaniaUbicacion;

	private List<String> entregaArchivos;

	public EntregaDTO(Long id, String material, Double cantidad, String unidad, String estado, int puntosGanados,
			String observaciones, String respuestaAdmin, LocalDateTime fechaEntrega, LocalDateTime fechaValidacion,
			String ciudadanoNombre, String ciudadanoRol, String validadoPor, String rolValidador,
			String puntoVerdeNombre, String puntoVerdeDireccion, String campaniaTitulo, String campaniaUbicacion,
			List<String> entregaArchivos) {
		this.id = id;
		this.material = material;
		this.cantidad = cantidad;
		this.unidad = unidad;
		this.estado = estado;
		this.puntosGanados = puntosGanados;
		this.observaciones = observaciones;
		this.respuestaAdmin = respuestaAdmin;
		this.fechaEntrega = fechaEntrega;
		this.fechaValidacion = fechaValidacion;
		this.ciudadanoNombre = ciudadanoNombre;
		this.ciudadanoRol = ciudadanoRol;
		this.validadoPor = validadoPor;
		this.rolValidador = rolValidador;
		this.puntoVerdeNombre = puntoVerdeNombre;
		this.puntoVerdeDireccion = puntoVerdeDireccion;
		this.campaniaTitulo = campaniaTitulo;
		this.campaniaUbicacion = campaniaUbicacion;
		this.entregaArchivos = entregaArchivos;
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

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

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

	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public LocalDateTime getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(LocalDateTime fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getCiudadanoNombre() {
		return ciudadanoNombre;
	}

	public void setCiudadanoNombre(String ciudadanoNombre) {
		this.ciudadanoNombre = ciudadanoNombre;
	}

	public String getCiudadanoRol() {
		return ciudadanoRol;
	}

	public void setCiudadanoRol(String ciudadanoRol) {
		this.ciudadanoRol = ciudadanoRol;
	}

	public String getValidadoPor() {
		return validadoPor;
	}

	public void setValidadoPor(String validadoPor) {
		this.validadoPor = validadoPor;
	}

	public String getRolValidador() {
		return rolValidador;
	}

	public void setRolValidador(String rolValidador) {
		this.rolValidador = rolValidador;
	}

	public String getPuntoVerdeNombre() {
		return puntoVerdeNombre;
	}

	public void setPuntoVerdeNombre(String puntoVerdeNombre) {
		this.puntoVerdeNombre = puntoVerdeNombre;
	}

	public String getPuntoVerdeDireccion() {
		return puntoVerdeDireccion;
	}

	public void setPuntoVerdeDireccion(String puntoVerdeDireccion) {
		this.puntoVerdeDireccion = puntoVerdeDireccion;
	}

	public String getCampaniaTitulo() {
		return campaniaTitulo;
	}

	public void setCampaniaTitulo(String campaniaTitulo) {
		this.campaniaTitulo = campaniaTitulo;
	}

	public String getCampaniaUbicacion() {
		return campaniaUbicacion;
	}

	public void setCampaniaUbicacion(String campaniaUbicacion) {
		this.campaniaUbicacion = campaniaUbicacion;
	}

	public List<String> getEntregaArchivos() {
		return entregaArchivos;
	}

	public void setEntregaArchivos(List<String> entregaArchivos) {
		this.entregaArchivos = entregaArchivos;
	}
}
