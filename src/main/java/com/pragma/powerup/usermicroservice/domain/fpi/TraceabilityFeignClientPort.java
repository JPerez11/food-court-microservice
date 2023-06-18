package com.pragma.powerup.usermicroservice.domain.fpi;

import com.pragma.powerup.usermicroservice.domain.model.TraceabilityModel;

import java.util.List;

public interface TraceabilityFeignClientPort {

    void saveHistory(TraceabilityModel traceabilityModel);
    List<TraceabilityModel> getTraceability(Long customerId);

}
