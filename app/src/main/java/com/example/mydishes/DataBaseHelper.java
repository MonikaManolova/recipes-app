package com.example.mydishes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String MEALS_TABLE = "MEALS_TABLE";
    public static final String DRINKS_TABLE = "DRINKS_TABLE";
    public static final String DISH_TITLE = "DISH_TITLE";
    public static final String MEAL_ID = "MEAL_ID";
    public static final String DRINK_ID = "DRINK_ID";
    public static final String DISH_COOKING_TIME = "DISH_COOKING_TIME";
    public static final String DISH_DESCRIPTION = "DISH_DESCRIPTION";
    public static final String DISH_INGREDIENTS = "INGREDIENTS";
    public static final String IS_ALCOHOLIC = "IS_ALCOHOLIC";

    public DataBaseHelper(@Nullable Context context) {

        super(context, "dishes.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String mealsTable = "CREATE TABLE " + MEALS_TABLE +
                " (" + MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DISH_TITLE + " TEXT, " + DISH_COOKING_TIME + " TEXT, " +
                DISH_DESCRIPTION + " TEXT, " + DISH_INGREDIENTS + " TEXT)";

        String drinksTable = "CREATE TABLE " + DRINKS_TABLE +
                " (" + DRINK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DISH_TITLE + " TEXT, " + DISH_COOKING_TIME + " TEXT, " +
                DISH_DESCRIPTION + " TEXT, " + DISH_INGREDIENTS + " TEXT, " + IS_ALCOHOLIC + " TEXT)";

        db.execSQL(mealsTable);
        db.execSQL(drinksTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MEALS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DRINKS_TABLE);
        onCreate(db);
    }

    public boolean addMeal(DishModel dishModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        
        convertIngredients(dishModel.getIngredients());
        
        cv.put(DISH_TITLE, dishModel.getTitle());
        cv.put(DISH_COOKING_TIME, dishModel.getCooking_time());
        cv.put(DISH_DESCRIPTION, dishModel.getDescription());
        cv.put(DISH_INGREDIENTS, convertIngredients(dishModel.getIngredients()));

        //Add data to the database
        long insert = db.insert(MEALS_TABLE, null, cv);

        if(insert == -1){
            return false;
        }
        return true;
    }

    public boolean addDrink(DishModel dishModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DISH_TITLE, dishModel.getTitle());
        cv.put(DISH_COOKING_TIME, dishModel.getCooking_time());
        cv.put(DISH_DESCRIPTION, dishModel.getDescription());
        cv.put(DISH_INGREDIENTS, convertIngredients(dishModel.getIngredients()));
        cv.put(IS_ALCOHOLIC, dishModel.getIsAlcoholic());

        //Add data to the database
        long insert = db.insert(DRINKS_TABLE, null, cv);

        if(insert == -1){
            return false;
        }
        return true;
    }

    private String convertIngredients(ArrayList<String> listOfIngredients) {
        //Convert ArrayList of Ingredients to String
        String ingredients = "";
        for (String ingredient : listOfIngredients){
            ingredients +=  ingredient + ", ";
        }
        return ingredients;
    }

    public ArrayList<DishModel> getMeals(){
        ArrayList<DishModel> dishModels = new ArrayList<>();

        //Get data from the database
        String queryString = "SELECT * FROM " + MEALS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String cookingTime = cursor.getString(2);
                String description = cursor.getString(3);
                String ingredients = cursor.getString(4);

                ArrayList<String> ingredientsTOArr = new ArrayList<String>(Arrays.asList(ingredients.split(", ")));

                DishModel dishModel = new DishModel(id, title, ingredientsTOArr, cookingTime, description);
                dishModels.add(dishModel);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dishModels;
    }

    public ArrayList<DishModel> getDrinks(){
        ArrayList<DishModel> dishModels = new ArrayList<>();

        //Get data from the database
        String queryString = "SELECT * FROM " + DRINKS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String cookingTime = cursor.getString(2);
                String description = cursor.getString(3);
                String ingredients = cursor.getString(4);
                boolean isAlcoholic = cursor.getInt(5) == 1;

                ArrayList<String> ingredientsTOArr = new ArrayList<String>(Arrays.asList(ingredients.split(", ")));

                DishModel dishModel = new DishModel(id, title, ingredientsTOArr, cookingTime, description, isAlcoholic);
                dishModels.add(dishModel);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dishModels;
    }

    public boolean deleteMeal(DishModel dishModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + MEALS_TABLE + " WHERE " + MEAL_ID + " = " + dishModel.getId();

         Cursor cursor = db.rawQuery(queryString, null);

         if(cursor.moveToFirst()){
             return true;
         }else{
             return false;
         }
    }

    public boolean deleteDrink(DishModel dishModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + DRINKS_TABLE + " WHERE " + DRINK_ID + " = " + dishModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateMeal(DishModel dishModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DISH_TITLE, dishModel.getTitle());
        cv.put(DISH_COOKING_TIME, dishModel.getCooking_time());
        cv.put(DISH_DESCRIPTION, dishModel.getDescription());
        cv.put(DISH_INGREDIENTS, convertIngredients(dishModel.getIngredients()));

        int update = db.update(MEALS_TABLE, cv, MEAL_ID + " = " + dishModel.getId(), null);

        if(update == -1){
            return false;
        }
        return true;
    }

    public boolean updateDrink(DishModel dishModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DISH_TITLE, dishModel.getTitle());
        cv.put(DISH_COOKING_TIME, dishModel.getCooking_time());
        cv.put(DISH_DESCRIPTION, dishModel.getDescription());
        cv.put(DISH_INGREDIENTS, convertIngredients(dishModel.getIngredients()));
        cv.put(IS_ALCOHOLIC, dishModel.getIsAlcoholic());

        int update = db.update(DRINKS_TABLE, cv, DRINK_ID + " = " + dishModel.getId() , null);

        if(update == -1){
            return false;
        }
        return true;
    }
}
