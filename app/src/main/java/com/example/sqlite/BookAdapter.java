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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList book_id, book_title, book_author, book_pages;

    Animation translate_anim;

    BookAdapter(Activity activity,
                Context context,
                ArrayList book_id,
                ArrayList book_title,
                ArrayList book_author,
                ArrayList book_pages){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.book_id.setText(String.valueOf(book_id.get(position)));
        holder.book_title.setText(String.valueOf(book_title.get(position)));
        holder.book_author.setText(String.valueOf(book_author.get(position)));
        holder.book_pages.setText(String.valueOf(book_pages.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here we are navigating from our MainActivity, where we have the recyclerView, to our UpdateActivity. We're transferring the properties of the book to the new activity.
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView book_id, book_title, book_author, book_pages;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            book_id = view.findViewById(R.id.book_id);
            book_title = view.findViewById(R.id.book_title);
            book_author = view.findViewById(R.id.book_author);
            book_pages = view.findViewById(R.id.book_pages);
            mainLayout = view.findViewById(R.id.mainLayout);
            //Animate RecyclerView.
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
