package com.pragma.powerup.usermicroservice.domain.usecases;

import com.pragma.powerup.usermicroservice.domain.api.TraceabilityServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.CustomerWithoutOrdersException;
import com.pragma.powerup.usermicroservice.domain.fpi.TraceabilityFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.models.OrderModel;
import com.pragma.powerup.usermicroservice.domain.models.TraceabilityModel;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;

import java.util.List;

public class TraceabilityUseCase implements TraceabilityServicePort {

    private final TraceabilityFeignClientPort traceabilityFeignClientPort;
    private final OrderPersistencePort orderPersistencePort;

    public TraceabilityUseCase(TraceabilityFeignClientPort traceabilityFeignClientPort,
                               OrderPersistencePort orderPersistencePort) {
        this.traceabilityFeignClientPort = traceabilityFeignClientPort;
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public List<TraceabilityModel> getHistory() {
        Long customerId = orderPersistencePort.getAuthenticatedUserId();
        List<OrderModel> ordersDb = orderPersistencePort.findOrderByCustomerId(customerId);
        if (ordersDb.isEmpty()) {
            throw new CustomerWithoutOrdersException();
        }
        return traceabilityFeignClientPort.getTraceability(
                customerId
        );
    }

}
