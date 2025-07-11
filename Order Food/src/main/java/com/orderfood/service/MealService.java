package com.orderfood.service;

import com.orderfood.DTO.MealDTO;
import com.orderfood.model.Meal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MealService {

    String isValidInput(Meal meal);
    List<MealDTO> findAll();
    String save(Meal meal);
    String delete(Long mealId);
    Meal findOne(Long id);
    String editMeal(Meal meal);
    List<MealDTO> getMealsByMealTypeId(Long id);

}
