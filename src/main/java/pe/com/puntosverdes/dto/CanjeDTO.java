package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class CanjeDTO {
	private Long id;
	private String recompensaNombre;
	private String usuarioNombre;
	private int puntosUsados;
	private LocalDateTime fechaCanje;

	public CanjeDTO(Long id, String recompensaNombre, String usuarioNombre, int puntosUsados,
			LocalDateTime fechaCanje) {
		this.id = id;
		this.recompensaNombre = recompensaNombre;
		this.usuarioNombre = usuarioNombre;
		this.puntosUsados = puntosUsados;
		this.fechaCanje = fechaCanje;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecompensaNombre() {
		return recompensaNombre;
	}

	public void setRecompensaNombre(String recompensaNombre) {
		this.recompensaNombre = recompensaNombre;
	}

	public String getUsuarioNombre() {
		return usuarioNombre;
	}

	public void setUsuarioNombre(String usuarioNombre) {
		this.usuarioNombre = usuarioNombre;
	}

	public int getPuntosUsados() {
		return puntosUsados;
	}

	public void setPuntosUsados(int puntosUsados) {
		this.puntosUsados = puntosUsados;
	}

	public LocalDateTime getFechaCanje() {
		return fechaCanje;
	}

	public void setFechaCanje(LocalDateTime fechaCanje) {
		this.fechaCanje = fechaCanje;
	}
}
