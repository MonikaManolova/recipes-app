package com.example.mydishes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

public class MealsFragment extends Fragment {

    public  MealsFragment(){}

    //Initialize
    RecyclerView list_dishes;
    DataBaseHelper dataBaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals, container, false);

        //Assign values
        list_dishes = view.findViewById(R.id.recycler);
        dataBaseHelper = new DataBaseHelper(getContext());

        ArrayList<DishModel> dishModels = dataBaseHelper.getMeals();

        list_dishes.setHasFixedSize(true);
        list_dishes.setLayoutManager(new LinearLayoutManager(getContext()));

        ListAdapter listAdapter = new ListAdapter(dishModels, true, dataBaseHelper, getContext());
        list_dishes.setAdapter(listAdapter);

        return view;
    }
}