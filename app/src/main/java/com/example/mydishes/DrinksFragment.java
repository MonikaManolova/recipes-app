package com.example.mydishes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DrinksFragment extends Fragment {
    //Initialize
    RecyclerView recycler_drinks;
    DataBaseHelper dataBaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dtrinks, container, false);

        //Assign values
        recycler_drinks = view.findViewById(R.id.recycler_drinks);
        dataBaseHelper = new DataBaseHelper(getContext());

        ArrayList<DishModel> dishModels = dataBaseHelper.getDrinks();

        recycler_drinks.setHasFixedSize(true);
        recycler_drinks.setLayoutManager(new LinearLayoutManager(getContext()));

        ListAdapter listAdapter = new ListAdapter(dishModels, false, dataBaseHelper, getContext());
        recycler_drinks.setAdapter(listAdapter);

        return view;
    }
}