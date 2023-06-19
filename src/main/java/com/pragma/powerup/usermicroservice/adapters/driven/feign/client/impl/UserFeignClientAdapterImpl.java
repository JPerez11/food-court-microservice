package com.pragma.powerup.usermicroservice.adapters.driven.feign.client.impl;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.UserFeignClientAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.UserResponseMapper;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.models.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFeignClientAdapterImpl implements UserFeignClientPort {

    private final UserFeignClientAdapter userFeignClientAdapter;
    private final UserResponseMapper userResponseMapper;

    @Override
    public UserModel getUserById(Long id) {
        ResponseEntity<UserResponseDto> responseEntity = userFeignClientAdapter.getUserById(id);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return null;
        }
        UserResponseDto userResponse = responseEntity.getBody();
        return userResponseMapper.toUserModel(userResponse);
    }
}
