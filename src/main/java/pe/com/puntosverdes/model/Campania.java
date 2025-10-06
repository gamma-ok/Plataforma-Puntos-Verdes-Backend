package pe.com.puntosverdes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campanias")
public class Campania {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	@Column(length = 2000)
	private String descripcion;

	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;

	private String ubicacion;

	private boolean activa = true;

	private int puntosExtra = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creado_por_id")
	@JsonIgnore
	private Usuario creadoPor;

	@OneToMany(mappedBy = "campania", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Entrega> entregas = new HashSet<>();

	public Campania() {
	}

	public Campania(String titulo, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin,
			String ubicacion, int puntosExtra, Usuario creadoPor) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.ubicacion = ubicacion;
		this.puntosExtra = puntosExtra;
		this.creadoPor = creadoPor;
		this.activa = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public int getPuntosExtra() {
		return puntosExtra;
	}

	public void setPuntosExtra(int puntosExtra) {
		this.puntosExtra = puntosExtra;
	}

	public Usuario getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(Usuario creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Set<Entrega> getEntregas() {
		return entregas;
	}

	public void setEntregas(Set<Entrega> entregas) {
		this.entregas = entregas;
	}
}
