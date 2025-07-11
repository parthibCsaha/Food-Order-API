package com.orderfood.serviceImpl;

import com.orderfood.DTO.MealTypeDTO;
import com.orderfood.model.MealType;
import com.orderfood.repository.MealTypeRepository;
import com.orderfood.service.MealTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealTypeServiceImpl implements MealTypeService {

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Override
    public List<MealTypeDTO> getAllMealTypes() {
        List<MealType> allMealTypes = mealTypeRepository.findAll();
        List<MealTypeDTO> allMealTypesDTO = new ArrayList<>();
        for (MealType mealType : allMealTypes) {
            if (!mealType.isDeleted()) {
                allMealTypesDTO.add(new MealTypeDTO(mealType));
            }
        }
        return allMealTypesDTO;
    }

    @Override
    public String isValidInput(MealType mealType) {
        if (mealType.getTypeName() == null  || mealType.getTypeName().trim().isEmpty() || mealType.getDescription() == null ||
                mealType.getDescription().trim().isEmpty()) {
            return "invalid";
        }
        return "valid";
    }

    @Override
    public String save(MealType mealType) {
        try {
            mealTypeRepository.save(mealType);
            return "success";
        }
        catch (Exception e) {
            return "fail";
        }
    }

    @Override
    public String editMealType(MealType mealType) {
        MealType updateMealType = mealTypeRepository.findById(mealType.getId()).get();
        if (isValidInput(mealType).equals("invalid")) {
            return "invalid";
        }
        updateMealType.setTypeName(mealType.getTypeName());
        updateMealType.setDescription(mealType.getDescription());
        mealTypeRepository.save(updateMealType);
        return "success";
    }

    @Override
    public MealType findOne(Long id) {
        return mealTypeRepository.findById(id).get();
    }

    @Override
    public String delete(Long mealTypeId) {
        return "";
    }
}
