package pe.com.puntosverdes.dto;

public class DashboardResumenDTO {

	private long totalEntregasValidadas;
	private long totalCanjesAprobados;
	private long totalCampaniasActivas;
	private long totalRecompensasActivas;

	public DashboardResumenDTO(long totalEntregasValidadas, long totalCanjesAprobados, long totalCampaniasActivas,
			long totalRecompensasActivas) {
		this.totalEntregasValidadas = totalEntregasValidadas;
		this.totalCanjesAprobados = totalCanjesAprobados;
		this.totalCampaniasActivas = totalCampaniasActivas;
		this.totalRecompensasActivas = totalRecompensasActivas;
	}

	// Getters y Setters
	public long getTotalEntregasValidadas() {
		return totalEntregasValidadas;
	}

	public void setTotalEntregasValidadas(long totalEntregasValidadas) {
		this.totalEntregasValidadas = totalEntregasValidadas;
	}

	public long getTotalCanjesAprobados() {
		return totalCanjesAprobados;
	}

	public void setTotalCanjesAprobados(long totalCanjesAprobados) {
		this.totalCanjesAprobados = totalCanjesAprobados;
	}

	public long getTotalCampaniasActivas() {
		return totalCampaniasActivas;
	}

	public void setTotalCampaniasActivas(long totalCampaniasActivas) {
		this.totalCampaniasActivas = totalCampaniasActivas;
	}

	public long getTotalRecompensasActivas() {
		return totalRecompensasActivas;
	}

	public void setTotalRecompensasActivas(long totalRecompensasActivas) {
		this.totalRecompensasActivas = totalRecompensasActivas;
	}
}
