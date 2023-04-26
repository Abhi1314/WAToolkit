package com.example.pistalix.watoolkit.adapter;


import static com.example.pistalix.watoolkit.sticker.StickerPackListActivity.EXTRA_STICKERPACK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pistalix.watoolkit.R;
import com.example.pistalix.watoolkit.Sticker;
import com.example.pistalix.watoolkit.StickerPack;
import com.example.pistalix.watoolkit.sticker.StickerDetailsActivity;
import com.example.pistalix.watoolkit.sticker.StickerPackListActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    Context context;
    ArrayList<StickerPack> StickerPack;




    public StickerAdapter(Context context, ArrayList<StickerPack> StickerPack) {
        this.context = context;
        this.StickerPack = StickerPack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_sticker, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final List<Sticker> models = StickerPack.get(i).getStickers();
        Random randomcolor=new Random();
        int color = Color.argb(255, randomcolor.nextInt(255), randomcolor.nextInt(255), randomcolor.nextInt(255));
        viewHolder.name.setText(StickerPack.get(i).name);
        viewHolder.relativeLayout.setBackgroundTintList(ColorStateList.valueOf(color));
        viewHolder.cardView.setStrokeColor(color);
        final String url = "https://king-vid.sgp1.cdn.digitaloceanspaces.com/Status_Saver/Sticker/"+StickerPack.get(i).identifier+"/";
        Glide.with(context)
                .load(url + models.get(0).imageFileName)
                .placeholder(R.drawable.ic_placeholder)
                .into(viewHolder.imone);

        Glide.with(context)
                .load(url + models.get(1).imageFileName)
                .placeholder(R.drawable.ic_placeholder)

                .into(viewHolder.imtwo);

        Glide.with(context)
                .load(url + models.get(2).imageFileName)
                .placeholder(R.drawable.ic_placeholder)

                .into(viewHolder.imthree);

        if (models.size() > 3) {
            Glide.with(context)
                    .load(url + models.get(3).imageFileName)
                    .placeholder(R.drawable.ic_placeholder)

                    .into(viewHolder.imfour);
        } else {
            viewHolder.imfour.setVisibility(View.INVISIBLE);
        }

        Glide.with(context)
                .asBitmap()
                .load("https://king-vid.sgp1.cdn.digitaloceanspaces.com/Status_Saver/Sticker/" +StickerPack.get(i).identifier+"/"+ StickerPack.get(i).trayImageFile)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Bitmap bitmap1 = Bitmap.createBitmap(96, 96, Bitmap.Config.ARGB_8888);
                        Matrix matrix = new Matrix();
                        Canvas canvas = new Canvas(bitmap1);
                        canvas.drawColor(Color.TRANSPARENT);
                        matrix.postTranslate(
                                canvas.getWidth() / 2 - resource.getWidth() / 2,

                                canvas.getHeight() / 2 - resource.getHeight() / 2
                        );
                        canvas.drawBitmap(resource, matrix, null);
                        StickerPackListActivity.SaveTryImage(bitmap1,StickerPack.get(i).trayImageFile,StickerPack.get(i).identifier);
                        return false;
                    }
                })
                .submit();
        viewHolder.download.setOnClickListener(v -> context.startActivity(new Intent(context, StickerDetailsActivity.class)
                        .putExtra(EXTRA_STICKERPACK, StickerPack.get(viewHolder.getAdapterPosition()))));



    }

    @Override
    public int getItemCount() {
        return StickerPack.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imone, imtwo, imthree, imfour , download;
        MaterialCardView cardView;

        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.rv_sticker_name);
            imone = itemView.findViewById(R.id.sticker_one);
            imtwo = itemView.findViewById(R.id.sticker_two);
            imthree = itemView.findViewById(R.id.sticker_three);
            imfour = itemView.findViewById(R.id.sticker_four);
            download = itemView.findViewById(R.id.download);
            cardView=itemView.findViewById(R.id.card_view);
            relativeLayout=itemView.findViewById(R.id.relative_1);


        }
    }

}
