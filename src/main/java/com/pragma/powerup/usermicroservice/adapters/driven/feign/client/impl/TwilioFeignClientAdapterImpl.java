package com.pragma.powerup.usermicroservice.adapters.driven.feign.client.impl;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.TwilioFeignClientAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.TwilioFeignClientException;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.TwilioRequestMapper;
import com.pragma.powerup.usermicroservice.domain.fpi.TwilioFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.models.TwilioModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TwilioFeignClientAdapterImpl implements TwilioFeignClientPort {

    private final TwilioFeignClientAdapter twilioFeignClientAdapter;
    private final TwilioRequestMapper twilioRequestMapper;

    @Override
    public void sendMessage(TwilioModel twilioModel) {
        ResponseEntity<Map<String, String>> responseEntity =
                twilioFeignClientAdapter.sendMessage(
                        twilioRequestMapper.toRequest(twilioModel)
                );
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new TwilioFeignClientException();
        }
    }
}
