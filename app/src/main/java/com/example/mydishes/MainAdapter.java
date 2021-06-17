package com.example.mydishes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<String> items;
    Context context;
    String type;

    public MainAdapter(Context context, ArrayList<String> items, String type){
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(type == "Meal" ?
                        R.layout.row_item :
                        type == "Drink" ?
                        R.layout.row_item_blue :
                        R.layout.row_item_simple, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(items.get(position));

        if(holder.btn_close != null)
        holder.btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                items.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button btn_close;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_close = itemView.findViewById(R.id.btn_close);
            textView = itemView.findViewById(R.id.text_ingredient);
        }
    }
}
