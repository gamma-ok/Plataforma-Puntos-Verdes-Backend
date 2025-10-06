package pe.com.puntosverdes.dto;

import java.util.List;

public class EntregaEvidenciaDTO {
	private Long entregaId;
	private List<String> evidencias;

	public EntregaEvidenciaDTO(Long entregaId, List<String> evidencias) {
		this.entregaId = entregaId;
		this.evidencias = evidencias;
	}

	public Long getEntregaId() {
		return entregaId;
	}

	public void setEntregaId(Long entregaId) {
		this.entregaId = entregaId;
	}

	public List<String> getEvidencias() {
		return evidencias;
	}

	public void setEvidencias(List<String> evidencias) {
		this.evidencias = evidencias;
	}
}
