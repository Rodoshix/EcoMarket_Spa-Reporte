package com.ecomarket.reporte.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos de una venta individual")
public class VentaDTO {

    @Schema(description = "ID de la venta", example = "1001")
    private Long id;

    @Schema(description = "Correo electrónico del usuario que realizó la compra", example = "cliente@correo.cl")
    private String emailUsuario;

    @Schema(description = "Fecha y hora en que se realizó la venta", example = "2025-06-15T14:35:00")
    private LocalDateTime fechaHora;

    @Schema(description = "Total de la venta", example = "25980.0")
    private double total;

    @Schema(description = "Método de pago utilizado", example = "Tarjeta de crédito")
    private String metodoPago;

    @Schema(description = "Lista de productos vendidos en esta venta")
    private List<DetalleVentaDTO> detalles;
}