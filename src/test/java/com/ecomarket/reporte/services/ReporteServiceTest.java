package com.ecomarket.reporte.services;

import com.ecomarket.reporte.dto.DetalleVentaDTO;
import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.dto.VentaDTO;
import com.ecomarket.reporte.repository.LogReporteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class ReporteServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReporteService reporteService;

    @Mock
    private LogReporteRepository logReporteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generarReporteVentas_debeRetornarTotalesCorrectos() {
        // Detalles 1
        DetalleVentaDTO d1 = new DetalleVentaDTO();
        d1.setIdProducto(1L);
        d1.setNombreProducto("Producto A");
        d1.setCantidad(2);
        d1.setPrecioUnitario(5000.0);

        DetalleVentaDTO d2 = new DetalleVentaDTO();
        d2.setIdProducto(2L);
        d2.setNombreProducto("Producto B");
        d2.setCantidad(1);
        d2.setPrecioUnitario(10000.0);

        List<DetalleVentaDTO> detalles1 = List.of(d1, d2);

        // Detalles 2
        DetalleVentaDTO d3 = new DetalleVentaDTO();
        d3.setIdProducto(1L);
        d3.setNombreProducto("Producto A");
        d3.setCantidad(1);
        d3.setPrecioUnitario(5000.0);

        List<DetalleVentaDTO> detalles2 = List.of(d3);

        // Venta 1
        VentaDTO venta1 = new VentaDTO();
        venta1.setId(100L);
        venta1.setEmailUsuario("cliente@correo.cl");
        venta1.setFechaHora(LocalDateTime.of(2025, 6, 1, 10, 0));
        venta1.setTotal(20000.0);
        venta1.setMetodoPago("Tarjeta");
        venta1.setDetalles(detalles1);

        // Venta 2
        VentaDTO venta2 = new VentaDTO();
        venta2.setId(101L);
        venta2.setEmailUsuario("cliente2@correo.cl");
        venta2.setFechaHora(LocalDateTime.of(2025, 6, 1, 11, 0));
        venta2.setTotal(5000.0);
        venta2.setMetodoPago("Efectivo");
        venta2.setDetalles(detalles2);

        VentaDTO[] ventasMock = new VentaDTO[]{venta1, venta2};

        // Simulación del consumo a venta-service
        String urlEsperada = "http://localhost:8084/api/ventas";
        when(restTemplate.getForObject(eq(urlEsperada), eq(VentaDTO[].class)))
                .thenReturn(ventasMock);

        // Ejecutar
        ReporteVentasDTO resultado = reporteService.generarReporteVentas(
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 15)
        );

        // Verificar resultados
        assertEquals(2, resultado.getTotalVentas());
        assertEquals(25000.0, resultado.getTotalRecaudado());
        assertEquals(4, resultado.getTotalProductosVendidos());
    }

    @Test
    void generarReportePorUsuario_debeFiltrarYSumarCorrectamente() {
        // Venta válida (email y fecha dentro del rango)
        DetalleVentaDTO d1 = new DetalleVentaDTO();
        d1.setIdProducto(1L);
        d1.setNombreProducto("Producto A");
        d1.setCantidad(2);
        d1.setPrecioUnitario(5000.0);

        VentaDTO v1 = new VentaDTO();
        v1.setId(1L);
        v1.setEmailUsuario("cliente@correo.cl");
        v1.setFechaHora(LocalDateTime.of(2025, 6, 5, 10, 0));
        v1.setTotal(10000.0);
        v1.setMetodoPago("Débito");
        v1.setDetalles(List.of(d1));

        // Venta con otro usuario (debe ser ignorada)
        VentaDTO v2 = new VentaDTO();
        v2.setId(2L);
        v2.setEmailUsuario("otro@correo.cl");
        v2.setFechaHora(LocalDateTime.of(2025, 6, 7, 11, 0));
        v2.setTotal(20000.0);
        v2.setMetodoPago("Crédito");
        v2.setDetalles(List.of(d1));

        VentaDTO[] ventas = new VentaDTO[]{v1, v2};

        when(restTemplate.getForObject(eq("http://localhost:8084/api/ventas"), eq(VentaDTO[].class)))
            .thenReturn(ventas);

        ReporteVentasDTO reporte = reporteService.generarReportePorUsuario(
            "cliente@correo.cl",
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2025, 6, 15)
        );

        assertEquals(1, reporte.getTotalVentas());
        assertEquals(10000.0, reporte.getTotalRecaudado());
        assertEquals(2, reporte.getTotalProductosVendidos());
    }

    @Test
    void generarReportePorProducto_debeFiltrarYSumarCorrectamente() {
        // Venta con dos productos, uno de ellos con id = 10L
        DetalleVentaDTO d1 = new DetalleVentaDTO();
        d1.setIdProducto(10L);
        d1.setNombreProducto("Producto A");
        d1.setCantidad(3);
        d1.setPrecioUnitario(2000.0);

        DetalleVentaDTO d2 = new DetalleVentaDTO();
        d2.setIdProducto(99L);
        d2.setNombreProducto("Otro producto");
        d2.setCantidad(1);
        d2.setPrecioUnitario(1500.0);

        VentaDTO v = new VentaDTO();
        v.setId(1L);
        v.setEmailUsuario("usuario@correo.cl");
        v.setFechaHora(LocalDateTime.of(2025, 6, 10, 14, 0));
        v.setTotal(0); // no importa
        v.setMetodoPago("Transferencia");
        v.setDetalles(List.of(d1, d2));

        VentaDTO[] ventas = new VentaDTO[]{v};

        when(restTemplate.getForObject(eq("http://localhost:8084/api/ventas"), eq(VentaDTO[].class)))
            .thenReturn(ventas);

        ReporteVentasDTO reporte = reporteService.generarReportePorProducto(
            10L,
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2025, 6, 15)
        );

        assertEquals(1, reporte.getTotalVentas());
        assertEquals(6000.0, reporte.getTotalRecaudado()); // 3 * 2000
        assertEquals(3, reporte.getTotalProductosVendidos());
    }
}