package com.example.mydishes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class FormDrink extends AppCompatActivity {

    //Initialize variables
    EditText et_ingredients, et_title, et_cooking_time, et_description;
    Button button, btn_add_ingredients;
    ImageButton btn_back;
    RecyclerView recyclerView;
    Switch switch_isAlcoholic;

    MainAdapter mainAdapter;
    FormModel formModel;

    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_drink);

        //Assign values
        et_ingredients = findViewById(R.id.et_ingredients);
        et_title = findViewById(R.id.et_title);
        et_cooking_time = findViewById(R.id.et_cooking_time);
        et_description = findViewById(R.id.et_description);

        button = findViewById(R.id.btn_submit);
        btn_add_ingredients = findViewById(R.id.btn_add_ingredients);
        btn_back = findViewById(R.id.btn_back);
        switch_isAlcoholic = findViewById(R.id.switch_isAlcoholic);

        recyclerView = findViewById(R.id.recyclerView);

        items = new ArrayList<>();

        //Initialize for Edit
        DishModel dish = (DishModel) getIntent().getSerializableExtra("dish");

        if(dish != null){
            et_title.setText(dish.getTitle());
            et_cooking_time.setText(dish.getCooking_time());
            et_description.setText(dish.getDescription());
            items = dish.getIngredients();
            switch_isAlcoholic.setChecked(dish.getIsAlcoholic());
            button.setText("Apply Changes");
        }

        formModel = new FormModel(mainAdapter, items);
        formModel.designHorizontalLayout(recyclerView, FormDrink.this, "Drink");

        //Add ingredient method
        btn_add_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formModel.addIngredient(et_ingredients);
            }
        });

        //Submit form
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DishModel dishModel;

                String title1 = et_title.getText().toString();
                String cookingTime1 = et_cooking_time.getText().toString();
                String description1 = et_description.getText().toString();

                if(!title1.equals("") && !cookingTime1.equals("") && !description1.equals("") && items.size() > 0){
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(FormDrink.this);
                    boolean isSuccess;

                    if(dish != null){
                        dishModel = new DishModel(dish.getId(), title1, items, cookingTime1, description1, switch_isAlcoholic.isChecked());
                        isSuccess = dataBaseHelper.updateDrink(dishModel);
                    }else{
                        dishModel = new DishModel(0, title1, items, cookingTime1, description1, switch_isAlcoholic.isChecked());
                        isSuccess = dataBaseHelper.addDrink(dishModel);
                    }

                    if(isSuccess){
                        Toast.makeText(getApplicationContext(), dish != null ? "Successfully Updated!": "Successfully Added!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Sorry, try again later.", Toast.LENGTH_SHORT).show();
                    }

                    //Redirect to another activity
                    Intent intent = new Intent(FormDrink.this, MainActivity.class);
                    intent.putExtra("isMeal", false);
                    overridePendingTransition(0,0);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Please, Fill the form!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}