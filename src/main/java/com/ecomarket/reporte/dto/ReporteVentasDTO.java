package com.ecomarket.reporte.dto;

import lombok.Data;

@Data
public class ReporteVentasDTO {
    private long totalVentas;
    private double totalRecaudado;
    private int totalProductosVendidos;
}