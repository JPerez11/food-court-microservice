package com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TraceabilityDto;
import com.pragma.powerup.usermicroservice.domain.models.TraceabilityModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TraceabilityDtoMapper {

    TraceabilityModel toModel(TraceabilityDto traceabilityDto);
    TraceabilityDto toDto(TraceabilityModel traceabilityModel);
    List<TraceabilityModel> toModelList(List<TraceabilityDto> traceabilityDtoList);
    List<TraceabilityDto> toDtoList(List<TraceabilityModel> traceabilityModelList);

}
