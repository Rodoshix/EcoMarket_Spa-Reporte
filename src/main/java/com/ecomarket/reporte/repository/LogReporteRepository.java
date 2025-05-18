package com.ecomarket.reporte.repository;

import com.ecomarket.reporte.model.LogReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogReporteRepository extends JpaRepository<LogReporte, Long> {
}