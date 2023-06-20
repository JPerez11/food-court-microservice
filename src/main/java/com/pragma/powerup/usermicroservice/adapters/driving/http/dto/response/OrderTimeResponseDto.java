package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderTimeResponseDto {

    Long id;
    Long customerId;
    String startTime;
    String endTime;
    String timePeriod;

}
