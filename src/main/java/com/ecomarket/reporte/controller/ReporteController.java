package com.ecomarket.reporte.controller;

import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Reporte de ventas por rango de fechas
    @GetMapping("/ventas")
    public ResponseEntity<ReporteVentasDTO> obtenerReporteVentas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return ResponseEntity.ok(reporteService.generarReporteVentas(desde, hasta));
    }

    @GetMapping("/ventas/usuario/{email}")
    public ResponseEntity<ReporteVentasDTO> reportePorUsuario(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return ResponseEntity.ok(reporteService.generarReportePorUsuario(email, desde, hasta));
    }

    @GetMapping("/ventas/producto/{idProducto}")
    public ResponseEntity<ReporteVentasDTO> reportePorProducto(
            @PathVariable Long idProducto,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return ResponseEntity.ok(reporteService.generarReportePorProducto(idProducto, desde, hasta));
    }
}