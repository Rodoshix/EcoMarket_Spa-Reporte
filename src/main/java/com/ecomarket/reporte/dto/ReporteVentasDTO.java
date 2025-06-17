package com.ecomarket.reporte.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Reporte agregado de ventas")
public class ReporteVentasDTO {
    @Schema(description = "Cantidad total de ventas registradas", example = "25")
    private long totalVentas;

    @Schema(description = "Monto total recaudado", example = "134500.0")
    private double totalRecaudado;
    
    @Schema(description = "Cantidad total de productos vendidos", example = "76")
    private int totalProductosVendidos;
}