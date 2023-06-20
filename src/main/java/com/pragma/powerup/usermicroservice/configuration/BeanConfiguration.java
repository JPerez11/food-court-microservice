package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.TraceabilityFeignClientAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.TwilioFeignClientAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.UserFeignClientAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.impl.TraceabilityFeignClientAdapterImpl;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.impl.TwilioFeignClientAdapterImpl;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.client.impl.UserFeignClientAdapterImpl;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.TraceabilityDtoMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.TwilioRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.mapper.UserResponseMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.OrderDishMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.OrderMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.RestaurantEmployeeMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.CategoryEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.DishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderDishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.OrderEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RestaurantEmployeeEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.CategoryRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.DishRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderDishRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.OrderRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantEmployeeRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantRepository;
import com.pragma.powerup.usermicroservice.domain.api.DishServicePort;
import com.pragma.powerup.usermicroservice.domain.api.OrderDishServicePort;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantEmployeeServicePort;
import com.pragma.powerup.usermicroservice.domain.api.RestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.api.TraceabilityServicePort;
import com.pragma.powerup.usermicroservice.domain.fpi.TraceabilityFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.fpi.TwilioFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.fpi.UserFeignClientPort;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.OrderDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.OrderPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantEmployeePersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import com.pragma.powerup.usermicroservice.domain.usecases.DishUseCase;
import com.pragma.powerup.usermicroservice.domain.usecases.OrderDishUseCase;
import com.pragma.powerup.usermicroservice.domain.usecases.OrderUseCase;
import com.pragma.powerup.usermicroservice.domain.usecases.RestaurantEmployeeUseCase;
import com.pragma.powerup.usermicroservice.domain.usecases.RestaurantUseCase;
import com.pragma.powerup.usermicroservice.domain.usecases.TraceabilityUseCase;
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
    private final CategoryEntityMapper categoryEntityMapper;
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderDishRepository orderDishRepository;
    private final OrderDishEntityMapper orderDishEntityMapper;
    private final RestaurantEmployeeRepository restaurantEmployeeRepository;
    private final RestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    private final TwilioFeignClientAdapter twilioFeignClientAdapter;
    private final TwilioRequestMapper twilioRequestMapper;
    private final TraceabilityFeignClientAdapter traceabilityFeignClientAdapter;
    private final TraceabilityDtoMapper traceabilityDtoMapper;

    @Bean
    public RestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userFeignClientPort());
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
        return new DishMysqlAdapter(dishRepository, categoryRepository, restaurantRepository, dishEntityMapper,
                restaurantEntityMapper, categoryEntityMapper);
    }
    @Bean
    public OrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderRepository, restaurantRepository, orderEntityMapper);
    }
    @Bean
    public OrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), userFeignClientPort(),
                twilioFeignClientPort(), traceabilityFeignClientPort());
    }
    @Bean
    public OrderDishPersistencePort orderDishPersistencePort() {
        return new OrderDishMysqlAdapter(orderDishRepository, orderRepository, dishRepository,
                restaurantEmployeeRepository, orderDishEntityMapper, orderEntityMapper);
    }
    @Bean
    public OrderDishServicePort orderDishServicePort() {
        return new OrderDishUseCase(orderDishPersistencePort());
    }
    @Bean
    public RestaurantEmployeePersistencePort restaurantEmployeePersistencePort() {
        return new RestaurantEmployeeMysqlAdapter(restaurantEmployeeRepository, restaurantRepository, restaurantEmployeeEntityMapper);
    }
    @Bean
    public RestaurantEmployeeServicePort restaurantEmployeeServicePort() {
        return new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort(), userFeignClientPort());
    }
    @Bean
    public UserFeignClientPort userFeignClientPort() {
        return new UserFeignClientAdapterImpl(userFeignClientAdapter, userResponseMapper);
    }
    @Bean
    public TwilioFeignClientPort twilioFeignClientPort() {
        return new TwilioFeignClientAdapterImpl(twilioFeignClientAdapter, twilioRequestMapper);
    }
    @Bean
    public TraceabilityFeignClientPort traceabilityFeignClientPort() {
        return new TraceabilityFeignClientAdapterImpl(traceabilityFeignClientAdapter, traceabilityDtoMapper);
    }
    @Bean
    public TraceabilityServicePort traceabilityServicePort() {
        return new TraceabilityUseCase(traceabilityFeignClientPort(), orderPersistencePort());
    }
}
