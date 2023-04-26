package com.example.pistalix.watoolkit.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pistalix.watoolkit.R;
import java.util.ArrayList;

public class StickerDetailsAdapter extends RecyclerView.Adapter<StickerDetailsAdapter.ViewHolder> {

    ArrayList<String> strings;
    Context context;
    String identifier;

    public StickerDetailsAdapter(ArrayList<String> strings, Context context, String identifier) {
        this.strings = strings;
        this.context = context;
        this.identifier = identifier;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        try {
            String url = "https://king-vid.sgp1.cdn.digitaloceanspaces.com/Status_Saver/Sticker/" + identifier + "/" + strings.get(i);
            Glide.with(context)
                    .load(Uri.parse(url)).placeholder(R.drawable.ic_placeholder)
                    .into(viewHolder.imageView);
            Log.d("adapter 2", "onBindViewHolder: " + strings.get(i));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sticker_image);
        }
    }

}
