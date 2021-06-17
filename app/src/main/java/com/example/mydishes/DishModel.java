package com.example.mydishes;

import java.io.Serializable;
import java.util.ArrayList;

public class DishModel implements Serializable {
    int id;
    String title;
    ArrayList<String> ingredients;
    String cooking_time;
    String description;
    boolean isAlcoholic;

    public DishModel(int id, String title, ArrayList<String> ingredients, String cooking_time, String description){
        this.title = title;
        this.ingredients = ingredients;
        this.cooking_time = cooking_time;
        this.description = description;
        this.id = id;
    }

    public DishModel(int id, String title, ArrayList<String> ingredients, String cooking_time, String description, boolean isAlcoholic){
        this.title = title;
        this.ingredients = ingredients;
        this.cooking_time = cooking_time;
        this.description = description;
        this.isAlcoholic = isAlcoholic;
        this.id = id;
    }

    //getters and setters

    public String getTitle() { return title; }

    public ArrayList<String> getIngredients() { return ingredients; }

    public String getCooking_time() { return cooking_time; }

    public String getDescription() { return description; }

    public Boolean getIsAlcoholic() { return  isAlcoholic; }

    public int getId() { return id; }

//    public void setId(int id) { this.id = id; }
}
