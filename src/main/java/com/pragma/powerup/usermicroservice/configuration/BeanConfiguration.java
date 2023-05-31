package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.UserFeignClientAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.impl.UserFeignClientAdapterImpl;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.UserResponseMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.OrderMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.DishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.CategoryRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.DishRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantRepository;
import com.pragma.powerup.usermicroservice.domain.api.DishServicePort;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecase.DishUseCase;
import com.pragma.powerup.usermicroservice.domain.usecase.OrderUseCase;
import com.pragma.powerup.usermicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final DishRepository dishRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final DishEntityMapper dishEntityMapper;
    private final UserFeignClientAdapter userFeignClientAdapter;
    private final UserResponseMapper userResponseMapper;
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Bean
    public RestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), feignClientPort());
    }
    @Bean
    public RestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }
    @Bean
    public DishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort());
    }
    @Bean
    public DishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, categoryRepository, restaurantRepository, dishEntityMapper);
    }
    @Bean
    public UserFeignClientPort feignClientPort() {
        return new UserFeignClientAdapterImpl(userFeignClientAdapter, userResponseMapper);
    }
    @Bean
    public OrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderRepository, orderEntityMapper);
    }
    @Bean
    public OrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort());
    }
}
