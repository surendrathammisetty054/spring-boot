/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.dinemaster.service;

import com.example.dinemaster.model.Restaurant;
import com.example.dinemaster.model.Chef;
import com.example.dinemaster.repository.RestaurantJpaRepository;
import com.example.dinemaster.repository.ChefJpaRepository;
import com.example.dinemaster.repository.ChefRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class ChefJpaService implements ChefRepository {
    @Autowired
    private ChefJpaRepository chefJpaRepository;

    @Autowired
    private RestaurantJpaService restaurantJpaService;

    @Override
    public ArrayList<Chef> getChefs() {
        List<Chef> chefList = chefJpaRepository.findAll();
        ArrayList<Chef> chefs = new ArrayList<>(chefList);
        return chefs;
    }

    @Override
    public Chef getChefById(int id) {
        try {
            Chef chef = chefJpaRepository.findById(id).get();
            return chef;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Chef addChef(Chef chef) {
        int id = chef.getRestaurant().getId();

        Restaurant restaurant = restaurantJpaService.getRestaurantById(id);
        chef.setRestaurant(restaurant);

        chefJpaRepository.save(chef);

        return chef;

    }

    @Override
    public Chef updateChef(int id, Chef chef) {
        try {
            Chef newChef = chefJpaRepository.findById(id).get();
            if (chef.getFirstName() != null) {
                newChef.setFirstName(chef.getFirstName());
            }
            if (chef.getLastName() != null) {
                newChef.setLastName(chef.getLastName());
            }
            if (chef.getExpertise() != null) {
                newChef.setExpertise(chef.getExpertise());
            }
            if (chef.getExperienceYears() != 0) {
                newChef.setExperienceYears(chef.getExperienceYears());
            }
            if (chef.getRestaurant() != null) {
                int restaurantId = chef.getRestaurant().getId();
                Restaurant newRestaurant = restaurantJpaService.getRestaurantById(restaurantId);
                newChef.setRestaurant(newRestaurant);
            }

            chefJpaRepository.save(newChef);
            return newChef;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteChef(int id) {
        try {
            chefJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }

    @Override
    public Restaurant getChefRestaurant(int id) {
        try {
            Chef chef = chefJpaRepository.findById(id).get();
            Restaurant restaurant = chef.getRestaurant();
            return restaurant;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}