package com.example.mydishes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<DishModel> dishModels;
    boolean isMeal;
    DataBaseHelper dataBaseHelper;
    Context context;

    public ListAdapter(ArrayList<DishModel> mealModels, boolean isMeal, DataBaseHelper dataBaseHelper, Context context){
        this.dishModels = mealModels;
        this.isMeal = isMeal;
        this.dataBaseHelper = dataBaseHelper;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_layout, parent, false);

        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DishView.class);
                //Pass data to the next view
                intent.putExtra("isMeal", isMeal);
                intent.putExtra("dish", (Serializable) dishModels.get(position));

                //Redirect
                context.startActivity(intent);
            }
        });

        //Set Image for Meal/Drink, based on even/odd position in the list.
        if(isMeal){
                holder.imageView.setImageResource(
                        position % 2 == 0 ?
                                R.drawable.katie_smith_uqs1802d0cq_unsplash :
                                R.drawable.gareth_hubbard_qpcsuerqbac_unsplash
                );
        }else{
            holder.text_title.setTextColor(Color.rgb(23,130,228));
            holder.layout_border.setBackground(ContextCompat.getDrawable(context, R.color.main_drinks));
            holder.imageView.setImageResource(
                    position % 2 == 0 ?
                            R.drawable.drink1 :
                            R.drawable.drink2
            );
        }

        holder.text_title.setText(dishModels.get(position).getTitle());
        holder.text_cooking_time.setText(dishModels.get(position).getCooking_time());
    }

    @Override
    public int getItemCount() { return dishModels.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView text_cooking_time, text_title;
        LinearLayout layout_border;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                text_title = itemView.findViewById(R.id.text_title);
                text_cooking_time = itemView.findViewById(R.id.text_cooking_time);
                layout_border = itemView.findViewById(R.id.layout_border);
        }
    }
}
