package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishEntity, Long> {

    boolean existsByIdAndRestaurantEntityId(Long idDish, Long idRestaurant);
    Page<DishEntity> findAllByCategoryEntityNameContainingIgnoreCaseAndRestaurantEntityId(String categoryName, Long id,
                                                                                          Pageable pageable);

}
