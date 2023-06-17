package com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.dto.TwilioRequestDto;
import com.pragma.powerup.usermicroservice.domain.model.TwilioModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TwilioRequestMapper {

    TwilioRequestDto toRequest(TwilioModel twilioModel);

}
