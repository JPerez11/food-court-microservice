package com.pragma.powerup.usermicroservice.domain.fpi;

import com.pragma.powerup.usermicroservice.domain.model.TwilioModel;

public interface TwilioFeignClientPort {

    void sendMessage(TwilioModel twilioModel);

}
