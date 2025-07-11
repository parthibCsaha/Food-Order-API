package com.orderfood.controller;

import com.google.gson.Gson;
import com.orderfood.DTO.MealDTO;
import com.orderfood.model.Meal;
import com.orderfood.service.MealService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "api/meal")
public class MealController {

    @Autowired
    MealService mealService;

    @GetMapping(value = "/getAllMeals")
    public ResponseEntity<List<MealDTO>> getAllMeals() {
        List<MealDTO> allMealDTOList = mealService.findAll();
        return new ResponseEntity<>(allMealDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/getMealsByMealTypeId/{id}")
    public ResponseEntity<List<MealDTO>> getMealsByMealTypeId(@PathVariable Long id){
        List<MealDTO> mealsByMealTypeId = mealService.getMealsByMealTypeId(id);
        return new ResponseEntity<>(mealsByMealTypeId, HttpStatus.OK);
    }

    @PostMapping(value = "/createMeal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createMeal(@RequestParam("image") MultipartFile image, HttpServletRequest request) {
        Gson gson = new Gson();
        Meal meal = gson.fromJson(request.getParameter("meal"), Meal.class);

        String responseToClient = mealService.isValidInput(meal);
        if (!responseToClient.equals("valid")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            try {
                meal.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                meal.setImageName(image.getOriginalFilename());
                responseToClient = mealService.save(meal);
                return new ResponseEntity<>(responseToClient, HttpStatus.OK);
            }
            catch (Exception e) {
                responseToClient = "failed";
                return new ResponseEntity<>(responseToClient, HttpStatus.OK);
            }
        }
    }

    @PutMapping(value = "/updateMeal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateMeal(@RequestBody Meal meal) {
        String response = mealService.editMeal(meal);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteMeal/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable Long id) {
        String response = mealService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
