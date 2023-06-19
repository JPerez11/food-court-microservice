package com.pragma.powerup.usermicroservice.domain.fpi;

import com.pragma.powerup.usermicroservice.domain.models.UserModel;

public interface UserFeignClientPort {

    UserModel getUserById(Long id);

}
