package com.pragma.powerup.usermicroservice.domain.usecases;

import com.pragma.powerup.usermicroservice.domain.api.TraceabilityServicePort;
import com.pragma.powerup.usermicroservice.domain.fpi.TraceabilityFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.models.TraceabilityModel;

import java.util.List;

public class TraceabilityUseCase implements TraceabilityServicePort {

    private final TraceabilityFeignClientPort traceabilityFeignClientPort;

    public TraceabilityUseCase(TraceabilityFeignClientPort traceabilityFeignClientPort) {
        this.traceabilityFeignClientPort = traceabilityFeignClientPort;
    }

    @Override
    public List<TraceabilityModel> getHistory() {
        return traceabilityFeignClientPort.getTraceability(
                traceabilityFeignClientPort.getAuthenticatedUserId()
        );
    }

}
