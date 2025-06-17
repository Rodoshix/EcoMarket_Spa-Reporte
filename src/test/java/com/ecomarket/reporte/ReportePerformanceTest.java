package com.ecomarket.reporte;

import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.services.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReportePerformanceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    private static final Logger log = LoggerFactory.getLogger(ReportePerformanceTest.class);

    @Test
    void rendimiento_endpointReporteVentas() throws Exception {
        // Datos simulados
        ReporteVentasDTO dto = new ReporteVentasDTO();
        dto.setTotalVentas(3);
        dto.setTotalRecaudado(42000.0);
        dto.setTotalProductosVendidos(8);

        // Mock del servicio
        when(reporteService.generarReporteVentas(
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 15))
        ).thenReturn(dto);

        // Medición de tiempo
        long inicio = System.currentTimeMillis();

        mockMvc.perform(get("/api/reportes/ventas")
                        .param("desde", "2025-06-01")
                        .param("hasta", "2025-06-15"))
                .andExpect(status().isOk());

        long fin = System.currentTimeMillis();
        long duracion = fin - inicio;

        log.error("Tiempo de respuesta (ERROR): {} ms", duracion);
    }
}