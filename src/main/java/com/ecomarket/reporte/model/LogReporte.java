package com.ecomarket.reporte.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Schema(description = "Entidad que representa un log de generación de reportes")
public class LogReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado del log", example = "1")
    private Long id;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    @Size(max = 100, message = "El tipo de reporte no debe superar los 100 caracteres")
    @Schema(description = "Tipo de reporte generado", example = "VENTAS_POR_USUARIO", maxLength = 100)
    private String tipoReporte;

    @NotBlank(message = "Los parámetros del reporte son obligatorios")
    @Size(max = 500, message = "Los parámetros no deben superar los 500 caracteres")
    @Schema(description = "Parámetros utilizados en la generación del reporte", example = "desde=2025-06-01&hasta=2025-06-15", maxLength = 500)
    private String parametros;

    @NotNull(message = "La fecha de generación del reporte es obligatoria")
    @Schema(description = "Fecha y hora de generación del reporte", example = "2025-06-16T10:45:00")
    private LocalDateTime fechaGeneracion;
}