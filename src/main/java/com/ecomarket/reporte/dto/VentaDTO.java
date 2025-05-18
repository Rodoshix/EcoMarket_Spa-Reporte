package com.ecomarket.reporte.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VentaDTO {
    private Long id;
    private String emailUsuario;
    private LocalDateTime fechaHora;
    private double total;
    private String metodoPago;
    private List<DetalleVentaDTO> detalles;
}