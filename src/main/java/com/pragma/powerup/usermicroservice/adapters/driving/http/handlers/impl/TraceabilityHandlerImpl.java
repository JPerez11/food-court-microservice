package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TraceabilityDto;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.TraceabilityDtoMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.TraceabilityHandler;
import com.pragma.powerup.usermicroservice.domain.api.TraceabilityServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraceabilityHandlerImpl implements TraceabilityHandler {

    private final TraceabilityServicePort traceabilityServicePort;
    private final TraceabilityDtoMapper traceabilityDtoMapper;

    @Override
    public List<TraceabilityDto> getHistory() {
        return traceabilityDtoMapper.toDtoList(traceabilityServicePort.getHistory());
    }
}
