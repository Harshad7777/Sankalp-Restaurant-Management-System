package com.restaurant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.model.Restaurant;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.security.SecurityUtil;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Integer restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public Restaurant getCurrentRestaurant() {
        Integer resId = SecurityUtil.getCurrentRestaurantId();
        return restaurantRepository.findById(resId);
    }
}
