package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.DishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.CategoryRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.DishRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantRepository;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pragma.powerup.usermicroservice.configuration.utils.Constants.MAX_PAGE_SIZE;

@Transactional
@RequiredArgsConstructor
public class DishMysqlAdapter implements DishPersistencePort {

    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public void createDish(DishModel dishModel) {
        DishEntity dishEntity = dishEntityMapper.toDishEntity(dishModel);
        dishEntity.setCategoryEntity(
                categoryRepository.findById(dishModel.getCategoryModel().getId())
                        .orElseThrow(CategoryNotFoundException::new)
        );
        dishEntity.setRestaurantEntity(
                restaurantRepository.findById(dishModel.getRestaurantModel().getId())
                        .orElseThrow(RestaurantNotFoundException::new)
        );
        dishRepository.save(dishEntity);
    }

    @Override
    public DishModel getDishById(Long id) {
        return dishEntityMapper.toDishModel(
                dishRepository.findById(id)
                        .orElseThrow(DishNotFoundException::new)
        );
    }

    @Override
    public List<DishModel> getAllDishes(int page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        Page<DishEntity> dishEntityPage = dishRepository.findAll(pagination);
        if (dishEntityPage.isEmpty()) {
            throw new NoDataFoundException();
        }
        return dishEntityMapper.toDishModelList(dishEntityPage.getContent());
    }

    @Override
    public DishModel updateDish(Long id, DishModel dishModel) {
        DishEntity dishEntityDb = dishRepository.findById(id)
                .orElseThrow(DishNotFoundException::new);
        dishEntityDb.setDescription( dishModel.getDescription() );
        dishEntityDb.setPrice( dishModel.getPrice() );

        return dishEntityMapper.toDishModel(
                dishRepository.save(dishEntityDb));
    }
}
