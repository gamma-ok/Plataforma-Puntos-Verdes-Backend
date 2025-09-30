package pe.com.puntosverdes.dto;

import java.time.LocalDateTime;

public class UltimaEntregaDTO {
    private String material;
    private double cantidad;
    private LocalDateTime fechaEntrega;

    public UltimaEntregaDTO(String material, double cantidad, LocalDateTime fechaEntrega) {
        this.material = material;
        this.cantidad = cantidad;
        this.fechaEntrega = fechaEntrega;
    }

    // Getters y setters
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
