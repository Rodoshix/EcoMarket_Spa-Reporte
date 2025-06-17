package com.ecomarket.reporte.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity
@Data
@Schema(description = "Entidad que representa un log de generación de reportes")
public class LogReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado del log", example = "1")
    private Long id;

    @Schema(description = "Tipo de reporte generado", example = "VENTAS_POR_USUARIO")
    private String tipoReporte;

    @Schema(description = "Parámetros utilizados en la generación del reporte", example = "desde=2025-06-01&hasta=2025-06-15")
    private String parametros;

    @Schema(description = "Fecha y hora de generación del reporte", example = "2025-06-16T10:45:00")
    private LocalDateTime fechaGeneracion;
}