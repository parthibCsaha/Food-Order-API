package com.orderfood.service;

import com.orderfood.DTO.MealTypeDTO;
import com.orderfood.model.MealType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MealTypeService {

    List<MealTypeDTO> getAllMealTypes();
    String isValidInput(MealType mealType);
    String save(MealType mealType);
    String editMealType(MealType mealType);
    MealType findOne(Long id);
    String delete(Long mealTypeId);

}
