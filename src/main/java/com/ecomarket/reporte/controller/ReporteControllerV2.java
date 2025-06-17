package com.ecomarket.reporte.controller;

import com.ecomarket.reporte.assembler.ReporteAssembler;
import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v2/reportes")
public class ReporteControllerV2 {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteAssembler reporteAssembler;

    @GetMapping("/ventas")
    public EntityModel<ReporteVentasDTO> obtenerReporteVentasV2(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        ReporteVentasDTO dto = reporteService.generarReporteVentas(desde, hasta);
        return reporteAssembler.toModel(dto);
    }

    @GetMapping("/ventas/usuario/{email}")
    public EntityModel<ReporteVentasDTO> reportePorUsuarioV2(
            @PathVariable String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        ReporteVentasDTO dto = reporteService.generarReportePorUsuario(email, desde, hasta);
        return reporteAssembler.toModel(dto);
    }

    @GetMapping("/ventas/producto/{idProducto}")
    public EntityModel<ReporteVentasDTO> reportePorProductoV2(
            @PathVariable Long idProducto,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        ReporteVentasDTO dto = reporteService.generarReportePorProducto(idProducto, desde, hasta);
        return reporteAssembler.toModel(dto);
    }
}