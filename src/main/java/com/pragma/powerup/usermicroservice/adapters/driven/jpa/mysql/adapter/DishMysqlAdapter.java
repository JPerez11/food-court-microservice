package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.CategoryEntityMapper;
import com.pragma.powerup.usermicroservice.domain.exceptions.CategoryNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.DishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.RestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.CategoryRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.DishRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RestaurantRepository;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.utils.ExtractAuthorization;
import com.pragma.powerup.usermicroservice.domain.model.CategoryModel;
import com.pragma.powerup.usermicroservice.domain.model.DishModel;
import com.pragma.powerup.usermicroservice.domain.model.RestaurantModel;
import com.pragma.powerup.usermicroservice.domain.spi.DishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public class DishMysqlAdapter implements DishPersistencePort {

    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishEntityMapper dishEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void createDish(DishModel dishModel) {
        dishRepository.save(dishEntityMapper.toDishEntity(dishModel));
    }

    @Override
    public DishModel getDishById(Long id) {
        return dishEntityMapper.toDishModel(
                dishRepository.findById(id)
                        .orElse(null)
        );
    }

    @Override
    public List<DishModel> getPaginatedDishesByCategory(Long id, int page, int size, String category) {
        if (!categoryRepository.existsByNameIgnoreCase(category)) {
            throw new CategoryNotFoundException();
        }
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<DishEntity> dishEntityPage =
                dishRepository.findAllByCategoryEntityNameContainingIgnoreCaseAndRestaurantEntityId(category, id,
                        pageable);
        if (dishEntityPage.isEmpty()) {
            throw new NoDataFoundException();
        }
        return dishEntityMapper.toDishModelList(dishEntityPage.getContent());
    }

    @Override
    public DishModel updateDish(Long id, DishModel dishDb) {
        return dishEntityMapper.toDishModel(
                dishRepository.save(dishEntityMapper.toDishEntity(dishDb)));
    }

    // Method to validate domain
    @Override
    public RestaurantModel getRestaurantById(Long id) {
        return restaurantEntityMapper.toRestaurantModel(
                restaurantRepository.findById(id).orElse(null)
        );
    }

    @Override
    public Long getAuthenticatedUserId() {
        return ExtractAuthorization.getAuthenticatedUserId();
    }

    @Override
    public CategoryModel getCategoryById(Long id) {
        return categoryEntityMapper.toCategoryModel(
                categoryRepository.findById(id).orElse(null)
        );
    }
}
