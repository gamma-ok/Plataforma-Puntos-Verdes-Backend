package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class EntregaListadoDTO {
    private Long id;
    private String material;
    private Double cantidad;
    private String estado;
    private String ciudadanoUsername;
    private String recolectorUsername;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaValidacion;

    public EntregaListadoDTO(Long id, String material, Double cantidad, String estado, String ciudadanoUsername,
                             String recolectorUsername, LocalDateTime fechaEntrega, LocalDateTime fechaValidacion) {
        this.id = id;
        this.material = material;
        this.cantidad = cantidad;
        this.estado = estado;
        this.ciudadanoUsername = ciudadanoUsername;
        this.recolectorUsername = recolectorUsername;
        this.fechaEntrega = fechaEntrega;
        this.fechaValidacion = fechaValidacion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCiudadanoUsername() { return ciudadanoUsername; }
    public void setCiudadanoUsername(String ciudadanoUsername) { this.ciudadanoUsername = ciudadanoUsername; }

    public String getRecolectorUsername() { return recolectorUsername; }
    public void setRecolectorUsername(String recolectorUsername) { this.recolectorUsername = recolectorUsername; }

    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public LocalDateTime getFechaValidacion() { return fechaValidacion; }
    public void setFechaValidacion(LocalDateTime fechaValidacion) { this.fechaValidacion = fechaValidacion; }
}
