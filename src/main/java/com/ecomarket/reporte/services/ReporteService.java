package com.ecomarket.reporte.services;

import com.ecomarket.reporte.dto.ReporteVentasDTO;
import com.ecomarket.reporte.dto.VentaDTO;
import com.ecomarket.reporte.model.LogReporte;
import com.ecomarket.reporte.repository.LogReporteRepository;
import com.ecomarket.reporte.dto.DetalleVentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LogReporteRepository logReporteRepository;

    public ReporteVentasDTO generarReporteVentas(LocalDate desde, LocalDate hasta) {
        String url = "http://localhost:8084/api/ventas"; // Asegúrate que devuelve todas las ventas

        VentaDTO[] ventas = restTemplate.getForObject(url, VentaDTO[].class);

        long totalVentas = 0;
        double totalRecaudado = 0;
        int totalProductosVendidos = 0;

        if (ventas != null) {
            List<VentaDTO> lista = Arrays.asList(ventas);

            for (VentaDTO venta : lista) {
                if (venta.getFechaHora().toLocalDate().isBefore(desde) ||
                    venta.getFechaHora().toLocalDate().isAfter(hasta)) {
                    continue;
                }

                totalVentas++;
                totalRecaudado += venta.getTotal();

                for (DetalleVentaDTO detalle : venta.getDetalles()) {
                    totalProductosVendidos += detalle.getCantidad();
                }
            }
        }

        ReporteVentasDTO reporte = new ReporteVentasDTO();
        reporte.setTotalVentas(totalVentas);
        reporte.setTotalRecaudado(totalRecaudado);
        reporte.setTotalProductosVendidos(totalProductosVendidos);

        LogReporte log = new LogReporte();
        log.setTipoReporte("VENTAS");
        log.setParametros("Desde: " + desde + ", Hasta: " + hasta);
        log.setFechaGeneracion(LocalDateTime.now());
        logReporteRepository.save(log);

        return reporte;
    }

    public ReporteVentasDTO generarReportePorUsuario(String email, LocalDate desde, LocalDate hasta) {
        VentaDTO[] ventas = restTemplate.getForObject("http://localhost:8084/api/ventas", VentaDTO[].class);
        ReporteVentasDTO reporte = new ReporteVentasDTO();
    
        if (ventas != null) {
            for (VentaDTO venta : ventas) {
                if (!venta.getEmailUsuario().equalsIgnoreCase(email)) continue;
    
                if (venta.getFechaHora().toLocalDate().isBefore(desde) ||
                    venta.getFechaHora().toLocalDate().isAfter(hasta)) continue;
    
                reporte.setTotalVentas(reporte.getTotalVentas() + 1);
                reporte.setTotalRecaudado(reporte.getTotalRecaudado() + venta.getTotal());
    
                for (DetalleVentaDTO d : venta.getDetalles()) {
                    reporte.setTotalProductosVendidos(reporte.getTotalProductosVendidos() + d.getCantidad());
                }
            }
        }
    
        return reporte;
    }

    public ReporteVentasDTO generarReportePorProducto(Long idProducto, LocalDate desde, LocalDate hasta) {
        VentaDTO[] ventas = restTemplate.getForObject("http://localhost:8084/api/ventas", VentaDTO[].class);
        ReporteVentasDTO reporte = new ReporteVentasDTO();
    
        if (ventas != null) {
            for (VentaDTO venta : ventas) {
                if (venta.getFechaHora().toLocalDate().isBefore(desde) ||
                    venta.getFechaHora().toLocalDate().isAfter(hasta)) continue;
    
                for (DetalleVentaDTO d : venta.getDetalles()) {
                    if (d.getIdProducto().equals(idProducto)) {
                        reporte.setTotalVentas(reporte.getTotalVentas() + 1);
                        reporte.setTotalProductosVendidos(reporte.getTotalProductosVendidos() + d.getCantidad());
                        reporte.setTotalRecaudado(reporte.getTotalRecaudado() + d.getCantidad() * d.getPrecioUnitario());
                    }
                }
            }
        }
    
        return reporte;
    }
}