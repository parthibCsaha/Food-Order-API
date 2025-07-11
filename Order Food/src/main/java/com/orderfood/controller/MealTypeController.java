package com.orderfood.controller;

import com.google.gson.Gson;
import com.orderfood.DTO.MealTypeDTO;
import com.orderfood.model.Meal;
import com.orderfood.model.MealType;
import com.orderfood.service.MealTypeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/mealType")
public class MealTypeController {

    @Autowired
    MealTypeService mealTypeService;

    @GetMapping(value = "/getAllMealTypes")
    public ResponseEntity<List<MealTypeDTO>> getAllMealTypeList() {
        List<MealTypeDTO> mealTypeDTOList = mealTypeService.getAllMealTypes();
        return new ResponseEntity<>(mealTypeDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/createMealType", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createMeal(@RequestParam("image") MultipartFile image, HttpServletRequest request) {
        Gson gson = new Gson();
        MealType mealType = gson.fromJson(request.getParameter("mealType"), MealType.class);
        String responseToClient = mealTypeService.isValidInput(mealType);
        if (responseToClient.equals("valid")) {
            try {
                mealType.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                mealType.setImageName(image.getOriginalFilename());
                responseToClient = mealTypeService.save(mealType);
            }
            catch (IOException e) {
                responseToClient = "fail";
            }
            return new ResponseEntity<>(responseToClient, HttpStatus.OK);
        }
        else {
            responseToClient = "invalid";
            return new ResponseEntity<>(responseToClient, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/updateMealType", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editMealType(@RequestBody MealType mealType){
        String response = mealTypeService.editMealType(mealType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping(value = "/deleteMealType/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String responseToClient = mealTypeService.delete(id);;
        return new ResponseEntity<>(responseToClient, HttpStatus.OK);
    }

}
