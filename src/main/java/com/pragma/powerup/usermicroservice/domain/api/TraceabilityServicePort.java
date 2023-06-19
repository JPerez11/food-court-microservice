package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.models.TraceabilityModel;

import java.util.List;

public interface TraceabilityServicePort {

    List<TraceabilityModel> getHistory();

}
