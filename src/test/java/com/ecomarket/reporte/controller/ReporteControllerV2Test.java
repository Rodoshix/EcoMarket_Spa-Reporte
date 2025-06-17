package com.ecomarket.reporte.controller;

import com.ecomarket.reporte.assembler.ReporteAssembler;
import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.services.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteControllerV2.class)
@ActiveProfiles("test")
public class ReporteControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @MockBean
    private ReporteAssembler reporteAssembler;

    private ReporteVentasDTO crearReporteMock() {
        ReporteVentasDTO dto = new ReporteVentasDTO();
        dto.setTotalVentas(5);
        dto.setTotalRecaudado(50000.0);
        dto.setTotalProductosVendidos(12);
        return dto;
    }

    @Test
    void obtenerReporteVentasV2_debeRetornarReporteConLinks() throws Exception {
        LocalDate desde = LocalDate.of(2025, 6, 1);
        LocalDate hasta = LocalDate.of(2025, 6, 15);
        ReporteVentasDTO dto = crearReporteMock();

        when(reporteService.generarReporteVentas(desde, hasta)).thenReturn(dto);
        when(reporteAssembler.toModel(dto)).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v2/reportes/ventas")
                        .param("desde", "2025-06-01")
                        .param("hasta", "2025-06-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVentas").value(5));
    }

    @Test
    void reportePorUsuarioV2_debeRetornarReporteConLinks() throws Exception {
        String email = "cliente@correo.cl";
        LocalDate desde = LocalDate.of(2025, 6, 1);
        LocalDate hasta = LocalDate.of(2025, 6, 15);
        ReporteVentasDTO dto = crearReporteMock();

        when(reporteService.generarReportePorUsuario(email, desde, hasta)).thenReturn(dto);
        when(reporteAssembler.toModel(dto)).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v2/reportes/ventas/usuario/{email}", email)
                        .param("desde", "2025-06-01")
                        .param("hasta", "2025-06-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVentas").value(5));
    }

    @Test
    void reportePorProductoV2_debeRetornarReporteConLinks() throws Exception {
        Long idProducto = 10L;
        LocalDate desde = LocalDate.of(2025, 6, 1);
        LocalDate hasta = LocalDate.of(2025, 6, 15);
        ReporteVentasDTO dto = crearReporteMock();

        when(reporteService.generarReportePorProducto(idProducto, desde, hasta)).thenReturn(dto);
        when(reporteAssembler.toModel(dto)).thenReturn(EntityModel.of(dto));

        mockMvc.perform(get("/api/v2/reportes/ventas/producto/{idProducto}", idProducto)
                        .param("desde", "2025-06-01")
                        .param("hasta", "2025-06-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVentas").value(5));
    }
}