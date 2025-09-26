package pe.com.puntosverdes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recompensas")
public class Recompensa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private String descripcion;

	@Column(nullable = false)
	private int puntosNecesarios;

	private boolean activo = true;

	public Recompensa() {
	}

	public Recompensa(String nombre, String descripcion, int puntosNecesarios) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.puntosNecesarios = puntosNecesarios;
		this.activo = true;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPuntosNecesarios() {
		return puntosNecesarios;
	}

	public void setPuntosNecesarios(int puntosNecesarios) {
		this.puntosNecesarios = puntosNecesarios;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
