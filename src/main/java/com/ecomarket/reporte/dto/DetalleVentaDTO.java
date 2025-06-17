package com.ecomarket.reporte.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Detalle de un producto dentro de una venta")
public class DetalleVentaDTO {
    @Schema(description = "ID del producto vendido", example = "101")
    private Long idProducto;

    @Schema(description = "Nombre del producto", example = "Detergente ecológico")
    private String nombreProducto;

    @Schema(description = "Cantidad de unidades vendidas", example = "2")
    private int cantidad;

    @Schema(description = "Precio por unidad", example = "4590.0")
    private double precioUnitario;
}