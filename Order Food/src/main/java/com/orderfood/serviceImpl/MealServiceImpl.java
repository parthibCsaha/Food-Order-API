package com.orderfood.serviceImpl;

import com.orderfood.DTO.MealDTO;
import com.orderfood.model.Meal;
import com.orderfood.repository.MealRepository;
import com.orderfood.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MealServiceImpl  implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public String isValidInput(Meal meal) {
        if (meal.getPrice() < 1 || meal.getMealType() == null || meal.getName() == null || meal.getName().trim().isEmpty()) {
            return "invalid";
        }
        return "valid";
    }

    @Override
    public List<MealDTO> findAll() {
        List<Meal> allMealList = mealRepository.findAll();
        List<MealDTO> allMealDTOList = new ArrayList<>();
        MealDTO mealDTO = new MealDTO();
        for (Meal meal : allMealList) {
            if(!meal.isDeleted()) {
                mealDTO = new MealDTO(meal);
                allMealDTOList.add(mealDTO);
            }
        }
        return allMealDTOList;
    }

    @Override
    public String save(Meal meal) {
        try {
            mealRepository.save(meal);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public String delete(Long mealId) {
        try {
            Meal meal = mealRepository.findById(mealId).get();
            meal.setDeleted(true);
            mealRepository.save(meal);
            return "success";
        }
        catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public Meal findOne(Long id) {
        return mealRepository.findById(id).get();
    }

    @Override
    public String editMeal(Meal meal) {
        Meal m = mealRepository.findById(meal.getId()).get();
        if (isValidInput(meal).equals("invalid")) {
            return "invalid";
        }
        try {
            m.setPrice(meal.getPrice());
            m.setName(meal.getName());
            m.setMealType(meal.getMealType());
            mealRepository.save(m);
            return "success";
        }
        catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public List<MealDTO> getMealsByMealTypeId(Long id) {
        List<Meal> allMealList = mealRepository.findAll();

        List<MealDTO> mealsByMealTypeIdDTO = new ArrayList<>();
        MealDTO mealDTO = new MealDTO();
        for(Meal meal: allMealList) {
            if(Objects.equals(meal.getMealType().getId(), id) && !meal.isDeleted()) {
                mealDTO = new MealDTO(meal);
                mealsByMealTypeIdDTO.add(mealDTO);
            }
        }
        return mealsByMealTypeIdDTO;
    }
}
