package com.example.sqlite;

import android.annotation.SuppressLint;
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


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context context;
    Activity activity;
    private final ArrayList item_id;
    private final ArrayList item_recipe;
    private final ArrayList item_ingredients;
    private final ArrayList item_chef;

    Animation translate_anim;

    Adapter(Activity activity,
            Context context,
            ArrayList item_id,
            ArrayList item_recipe,
            ArrayList item_ingredients,
            ArrayList item_chef){
        this.activity = activity;
        this.context = context;
        this.item_id = item_id;
        this.item_recipe = item_recipe;
        this.item_ingredients = item_ingredients;
        this.item_chef = item_chef;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.item_recipe.setText(String.valueOf(item_recipe.get(position)));
        holder.item_chef.setText(String.valueOf(item_chef.get(position)));
        holder.mainLayout.setOnClickListener(v -> {
            //På klick, går från Mainactivity till UpdateActivity. sen för över de nya propertiesen.
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(item_id.get(position)));
            intent.putExtra("recipe", String.valueOf(item_recipe.get(position)));
            intent.putExtra("ingredients", String.valueOf(item_ingredients.get(position)));
            intent.putExtra("chef", String.valueOf(item_chef.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return item_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_recipe, item_chef;
        LinearLayout mainLayout;
        // Det är här vi väljer vad vi vill visa i vår recycler view
        public ViewHolder(@NonNull View view) {
            super(view);

            item_recipe = view.findViewById(R.id.item_title);
            item_chef = view.findViewById(R.id.item_measurement);
            mainLayout = view.findViewById(R.id.mainLayout);
            //Animate RecyclerView.
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
