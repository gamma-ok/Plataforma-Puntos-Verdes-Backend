package pe.com.puntosverdes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "recompensas")
public class Recompensa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@Column(length = 2000)
	private String descripcion;

	@Min(1)
	@Column(nullable = false)
	private int puntosNecesarios;

	private int stock = 0;
	private boolean activo = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creado_por_id")
	private Usuario creadoPor;

	private LocalDateTime fechaCreacion = LocalDateTime.now();
	private LocalDateTime fechaActualizacion = LocalDateTime.now();

	public Recompensa() {
	}

	public Recompensa(String nombre, String descripcion, int puntosNecesarios, int stock, Usuario creadoPor) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.puntosNecesarios = puntosNecesarios;
		this.stock = stock;
		this.activo = true;
		this.creadoPor = creadoPor;
		this.fechaCreacion = LocalDateTime.now();
		this.fechaActualizacion = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.fechaActualizacion = LocalDateTime.now();
	}

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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Usuario getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Usuario creadoPor) {
		this.creadoPor = creadoPor;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}
