package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantRepository;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.spi.RestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements RestaurantPersistencePort {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void createRestaurant(RestaurantModel restaurantModel) {
        restaurantRepository.save(restaurantEntityMapper.toRestaurantEntity(restaurantModel));
    }

    @Override
    public RestaurantModel getRestaurantById(Long id) {
        return restaurantEntityMapper
                .toRestaurantModel(restaurantRepository
                        .findById(id)
                        .orElse(null)
                );
    }

    @Override
    public List<RestaurantModel> getAllRestaurants(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return restaurantEntityMapper.toRestaurantModelList(
                restaurantRepository.findAllByOrderByName(pageable).getContent());
    }

    @Override
    public boolean existsRestaurantByTaxIdNumber(String taxIdNumber) {
        return restaurantRepository.existsByTaxIdNumber(taxIdNumber);
    }
}
