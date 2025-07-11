package com.orderfood.DTO;

import com.orderfood.model.MealType;
import lombok.Data;

@Data
public class MealTypeDTO {

    private Long id;
    private String typeName;
    private String image;
    private String imageName;
    private String description;

    public MealTypeDTO(MealType mealType) {
        this(mealType.getId(), mealType.getTypeName(), mealType.getImage(), mealType.getImageName(), mealType.getDescription());
    }

    public MealTypeDTO(Long id, String typeName, String image, String imageName, String description) {
        this.id = id;
        this.typeName = typeName;
        this.image = image;
        this.imageName = imageName;
        this.description = description;
    }
}
