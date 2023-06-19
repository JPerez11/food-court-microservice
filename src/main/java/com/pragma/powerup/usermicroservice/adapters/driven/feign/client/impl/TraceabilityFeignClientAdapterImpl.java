package com.pragma.powerup.usermicroservice.adapters.driven.feign.client.impl;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.TraceabilityFeignClientAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TraceabilityDto;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.TraceabilityDtoMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.utils.ExtractAuthorization;
import com.pragma.powerup.usermicroservice.domain.fpi.TraceabilityFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.models.TraceabilityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraceabilityFeignClientAdapterImpl implements TraceabilityFeignClientPort {

    private final TraceabilityFeignClientAdapter traceabilityFeignClientAdapter;
    private final TraceabilityDtoMapper traceabilityDtoMapper;

    @Override
    public void saveHistory(TraceabilityModel traceabilityModel) {
        traceabilityFeignClientAdapter.saveHistory(
                traceabilityDtoMapper.toDto(traceabilityModel)
        );
    }

    @Override
    public List<TraceabilityModel> getTraceability(Long customerId) {
        ResponseEntity<List<TraceabilityDto>> response =
                traceabilityFeignClientAdapter.getHistory(customerId);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return Collections.emptyList();
        }
        return traceabilityDtoMapper.toModelList(response.getBody());
    }

    @Override
    public Long getAuthenticatedUserId() {
        return ExtractAuthorization.getAuthenticatedUserId();
    }
}
