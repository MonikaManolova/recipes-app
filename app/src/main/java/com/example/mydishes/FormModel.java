package com.example.mydishes;

import android.content.Context;
import android.widget.EditText;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class FormModel {
    ArrayList<String> items;
    MainAdapter mainAdapter;

    public FormModel(MainAdapter mainAdapter, ArrayList<String> items){
        this.items = items;
        this.mainAdapter = mainAdapter;
    }

    //Design Horizontal Layout
    public void designHorizontalLayout(RecyclerView recyclerView, Context context, String type){

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Set Adapter
        mainAdapter = new MainAdapter(context, items, type);
        recyclerView.setAdapter(mainAdapter);
    }

    public void addIngredient(EditText ingredient){

        if(!ingredient.getText().toString().equals("")){

            items.add(ingredient.getText().toString());
            mainAdapter.notifyDataSetChanged();

            ingredient.getText().clear();
        }
    }
}
