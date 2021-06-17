package com.example.mydishes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    //Initialize Variables
    private BottomNavigationView bottomNavigationView;

    FloatingActionButton btn_add, btn_add_meal, btn_add_drink;
    Animation rotate_open_anim, rotate_close_anim, from_bottom_anim, to_bottom_anim;

    boolean isClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign Variables
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        btn_add = findViewById(R.id.btn_add);
        btn_add_meal = findViewById(R.id.btn_add_meal);
        btn_add_drink = findViewById(R.id.btn_add_drink);

        rotate_open_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        rotate_close_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        from_bottom_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        to_bottom_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);

        boolean isMeal = getIntent().getBooleanExtra("isMeal", true);

        if(isMeal){
            bottomNavigationView.setSelectedItemId(R.id.meals);
        }else{
            bottomNavigationView.setSelectedItemId(R.id.drinks);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility();
                setAnimation();
                isClicked = !isClicked;
            }
        });

        btn_add_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect(FormMeal.class);
                setVisibility();
                setAnimation();
                isClicked = !isClicked;
            }
        });

        btn_add_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect(FormDrink.class);
                setVisibility();
                setAnimation();
                isClicked = !isClicked;
            }
        });
    }

    private void redirect(Class activity) {
        startActivity(new Intent(getApplicationContext(), activity));
        overridePendingTransition(0,0);
    }

    private void setVisibility() {

        if(!isClicked){
            btn_add_drink.setVisibility(View.INVISIBLE);
            btn_add_meal.setVisibility(View.INVISIBLE);

            btn_add_drink.setClickable(false);
            btn_add_meal.setClickable(false);
        }else{
            btn_add_drink.setVisibility(View.VISIBLE);
            btn_add_meal.setVisibility(View.VISIBLE);

            btn_add_drink.setClickable(true);
            btn_add_meal.setClickable(true);
        }
    }

    private void setAnimation() {

        if(!isClicked){
            btn_add_meal.startAnimation(to_bottom_anim);
            btn_add_drink.startAnimation(to_bottom_anim);
            btn_add.startAnimation(rotate_close_anim);
        }else{
            btn_add_meal.startAnimation(from_bottom_anim);
            btn_add_drink.startAnimation(from_bottom_anim);
            btn_add.startAnimation(rotate_open_anim);
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.meals:
                    fragment = new MealsFragment();
                    break;
                case R.id.drinks:
                    fragment = new DrinksFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            return true;
        }
    };
}