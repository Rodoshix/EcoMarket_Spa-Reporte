package com.ecomarket.reporte;

import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.services.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReporteIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReporteService reporteService;

    @Test
    void integracion_reporteVentas() {
        ReporteVentasDTO dto = new ReporteVentasDTO();
        dto.setTotalVentas(5);
        dto.setTotalRecaudado(50000.0);
        dto.setTotalProductosVendidos(12);

        when(reporteService.generarReporteVentas(LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 15)))
                .thenReturn(dto);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/reportes/ventas")
                        .queryParam("desde", "2025-06-01")
                        .queryParam("hasta", "2025-06-15")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.totalVentas").isEqualTo(5)
                .jsonPath("$.totalRecaudado").isEqualTo(50000.0)
                .jsonPath("$.totalProductosVendidos").isEqualTo(12);
    }
}