package com.example.mydishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class DishView extends AppCompatActivity {

    //Initialize
    ImageButton btn_back;
    Button btn_delete, btn_edit;
    TextView title, text_cooking_time, text_description, text_isAlcoholic;
    RecyclerView list_ingredients;
    ArrayList<String> ingredients;
    boolean isMeal;
    DishModel dishModel;

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getIntent().getBooleanExtra("isMeal", true) ?
                R.layout.activity_meal_view :
                R.layout.activity_drink_view);

        //Assign
        btn_back = findViewById(R.id.btn_back);
        btn_delete = findViewById(R.id.btn_delete);
        btn_edit = findViewById(R.id.btn_edit);
        title = findViewById(R.id.title);
        text_cooking_time = findViewById(R.id.text_cooking_time);
        text_description = findViewById(R.id.text_description);
        text_isAlcoholic = findViewById(R.id.text_isAlcoholic);
        list_ingredients = findViewById(R.id.list_ingredients);
        ingredients = new ArrayList<>();
        isMeal = getIntent().getBooleanExtra("isMeal", true);

        dishModel = (DishModel) getIntent().getSerializableExtra("dish");

        init();

        //Design Horizontal ListView
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                DishView.this, LinearLayoutManager.HORIZONTAL, false
        );
        list_ingredients.setLayoutManager(layoutManager);
        list_ingredients.setItemAnimator(new DefaultItemAnimator());

        //Set Adapter
        mainAdapter = new MainAdapter(DishView.this, ingredients, "Overview");
        list_ingredients.setAdapter(mainAdapter);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if(isMeal) {
                    intent = new Intent(DishView.this, FormMeal.class);
                }else{
                    intent = new Intent(DishView.this, FormDrink.class);
                }

                //Pass parameters to next activity
                intent.putExtra("dish", (Serializable) dishModel);
                //Redirect
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(DishView.this);

                Intent intent;
                intent = new Intent(DishView.this, MainActivity.class);

                if(isMeal){
                    dataBaseHelper.deleteMeal(dishModel);
                    intent.putExtra("isMeal", true);
                }else{
                    dataBaseHelper.deleteDrink(dishModel);
                    intent.putExtra("isMeal", false);
                }
                startActivity(intent);
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        title.setText(dishModel.getTitle());
        text_cooking_time.setText(dishModel.getCooking_time());
        text_description.setText(dishModel.getDescription());

        for (String ingredient : dishModel.getIngredients()){
            ingredients.add(ingredient);
        }

        if(!isMeal){
            if (dishModel.getIsAlcoholic()) {
                text_isAlcoholic.setText("Alcoholic");
            } else {
                text_isAlcoholic.setText("Non-alcoholic");
            }
        }
    }
}