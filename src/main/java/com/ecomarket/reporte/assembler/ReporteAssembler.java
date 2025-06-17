package com.ecomarket.reporte.assembler;

import com.ecomarket.reporte.controller.ReporteControllerV2;
import com.ecomarket.reporte.dto.ReporteVentasDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteAssembler implements RepresentationModelAssembler<ReporteVentasDTO, EntityModel<ReporteVentasDTO>> {

    @Override
    public EntityModel<ReporteVentasDTO> toModel(ReporteVentasDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ReporteControllerV2.class).obtenerReporteVentasV2(null, null)).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).reportePorUsuarioV2("usuario@example.com", null, null)).withRel("ventas-por-usuario"),
                linkTo(methodOn(ReporteControllerV2.class).reportePorProductoV2(1L, null, null)).withRel("ventas-por-producto")
        );
    }
}