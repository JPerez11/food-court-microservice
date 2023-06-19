package com.pragma.powerup.usermicroservice.domain.fpi;

import com.pragma.powerup.usermicroservice.domain.models.TwilioModel;

public interface TwilioFeignClientPort {

    void sendMessage(TwilioModel twilioModel);

}
