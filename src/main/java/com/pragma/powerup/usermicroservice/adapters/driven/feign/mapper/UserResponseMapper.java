package com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserResponseMapper {

    @Mapping(target = "roleModel", source = "role")
    UserModel toUserModel(UserResponseDto userResponse);

}
