package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TraceabilityDto;

import java.util.List;

public interface TraceabilityHandler {

    List<TraceabilityDto> getHistory();

}
