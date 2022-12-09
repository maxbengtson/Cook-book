package com.example.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.sqlite.R;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList item_id, item_title, item_ingredients, item_measurement;

    Animation translate_anim;

    Adapter(Activity activity,
            Context context,
            ArrayList item_id,
            ArrayList item_title,
            ArrayList item_ingredients,
            ArrayList item_measurement){
        this.activity = activity;
        this.context = context;
        this.item_id = item_id;
        this.item_title = item_title;
        this.item_ingredients = item_ingredients;
        this.item_measurement = item_measurement;
    }

    @NonNull
    @Override  // "Blåser upp" informationen vi vill visa i vår Viewholder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }
        //Håller informationen vi vill använda/visa i vår recycler
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_id.setText(String.valueOf(item_id.get(position)));
        holder.item_title.setText(String.valueOf(item_title.get(position)));
        holder.item_ingredients.setText(String.valueOf(item_ingredients.get(position)));
        holder.item_measurement.setText(String.valueOf(item_measurement.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //På klick, går från Mainactivity till UpdateActivity. sen för över de nya propertiesen.
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(item_id.get(position)));
                intent.putExtra("title", String.valueOf(item_title.get(position)));
                intent.putExtra("ingredients", String.valueOf(item_ingredients.get(position)));
                intent.putExtra("chef", String.valueOf(item_measurement.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_id, item_title, item_ingredients, item_measurement;
        LinearLayout mainLayout;
        // Det är här vi väljer vad vi vill visa i vår recycler view
        public ViewHolder(@NonNull View view) {
            super(view);
            item_id = view.findViewById(R.id.item_id);
            item_title = view.findViewById(R.id.item_title);
            item_ingredients = view.findViewById(R.id.item_ingredients);
            item_measurement = view.findViewById(R.id.item_measurement);
            mainLayout = view.findViewById(R.id.mainLayout);
            //Animate RecyclerView.
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
