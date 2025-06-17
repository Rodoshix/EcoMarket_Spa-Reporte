package com.ecomarket.reporte.integration;

import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.services.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReporteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Test
    void integracion_reporteVentas() throws Exception {
        ReporteVentasDTO dto = new ReporteVentasDTO();
        dto.setTotalVentas(5);
        dto.setTotalRecaudado(50000.0);
        dto.setTotalProductosVendidos(12);

        when(reporteService.generarReporteVentas(LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 15)))
                .thenReturn(dto);

        mockMvc.perform(get("/api/reportes/ventas")
                        .param("desde", "2025-06-01")
                        .param("hasta", "2025-06-15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVentas").value(5))
                .andExpect(jsonPath("$.totalRecaudado").value(50000.0))
                .andExpect(jsonPath("$.totalProductosVendidos").value(12));
    }
}