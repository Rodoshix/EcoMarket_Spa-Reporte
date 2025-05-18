package com.ecomarket.reporte.dto;

import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Long idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
}