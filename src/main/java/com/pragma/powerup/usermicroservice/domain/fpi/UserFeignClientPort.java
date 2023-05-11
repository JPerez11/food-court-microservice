package com.pragma.powerup.usermicroservice.domain.fpi;

import com.pragma.powerup.usermicroservice.domain.model.UserModel;

public interface UserFeignClientPort {

    UserModel getUserById(Long id);

}
