package com.example.pistalix.watoolkit.sticker;


import static com.example.pistalix.watoolkit.sticker.StickerPackListActivity.EXTRA_STICKER_PACK_AUTHORITY;
import static com.example.pistalix.watoolkit.sticker.StickerPackListActivity.EXTRA_STICKER_PACK_ID;
import static com.example.pistalix.watoolkit.sticker.StickerPackListActivity.EXTRA_STICKER_PACK_NAME;
import static com.example.pistalix.watoolkit.utils.UtilKt.gone;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.pistalix.watoolkit.BuildConfig;
import com.example.pistalix.watoolkit.R;
import com.example.pistalix.watoolkit.Sticker;
import com.example.pistalix.watoolkit.StickerPack;
import com.example.pistalix.watoolkit.adapter.StickerDetailsAdapter;
import com.example.pistalix.watoolkit.databinding.ActivityStickerDetailsBinding;
import com.example.pistalix.watoolkit.utils.CommonAds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class StickerDetailsActivity extends AppCompatActivity {

    private static final int ADD_PACK = 200;
    StickerPack stickerPack;
    public static String path;
    ActivityStickerDetailsBinding binding;
    SharedPreferences sharedads;
    Dialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sticker_details);
        stickerPack = getIntent().getParcelableExtra(StickerPackListActivity.EXTRA_STICKERPACK);
        try {

            alertDialog = new Dialog(this, R.style.DialogCustomTheme);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.downloading_sticker_dialog);
            alertDialog.setCancelable(false);

            sharedads = getSharedPreferences("ads", MODE_PRIVATE);
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, binding.adsContainer);
            } else {
                gone(binding.adsContainer);
            }
            binding.toolBarLayout.setName(stickerPack.name);
            binding.toolBarLayout.backImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            List<Sticker> stickers = stickerPack.getStickers();
            ArrayList<String> strings = new ArrayList<>();
            path = getFilesDir() + "/" + "stickers_asset" + "/" + stickerPack.identifier + "/";

            for (Sticker s : stickers) {
                strings.add(s.imageFileName);
            }
            StickerDetailsAdapter adapter = new StickerDetailsAdapter(strings, this, stickerPack.identifier);
            binding.recyclerView.setAdapter(adapter);


            binding.addToWhatsapp.setOnClickListener(view -> {
                alertDialog.show();
                final String url = "https://king-vid.sgp1.cdn.digitaloceanspaces.com/Status_Saver/Sticker/" + stickerPack.identifier + "/";
                File file1 = new File(StickerPackListActivity.path + "/" + stickerPack.identifier + "/" + stickers.get(stickers.size() - 1).imageFileName);
                if (!file1.exists()) {
                    runOnUiThread(
                            () -> {
                                for (int i = 0; i < stickers.size(); i++) {
                                    Log.d("adapter", "onClick: " + stickers.get(i).imageFileName);
                                    int finalI = i;
                                    Glide.with(StickerDetailsActivity.this)
                                            .asBitmap()
                                            .apply(new RequestOptions().override(512, 512))
                                            .load(url + stickers.get(i).imageFileName)
                                            .addListener(new RequestListener<Bitmap>() {
                                                @Override
                                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                                    return false;
                                                }

                                                @Override
                                                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                                    Bitmap bitmap1 = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
                                                    Matrix matrix = new Matrix();
                                                    Canvas canvas = new Canvas(bitmap1);
                                                    canvas.drawColor(Color.TRANSPARENT);
                                                    matrix.postTranslate(
                                                            canvas.getWidth() / 2 - resource.getWidth() / 2,

                                                            canvas.getHeight() / 2 - resource.getHeight() / 2
                                                    );
                                                    canvas.drawBitmap(resource, matrix, null);
                                                    StickerPackListActivity.SaveImage(bitmap1, stickers.get(finalI).imageFileName, stickerPack.identifier);
                                                    Log.d("stickerplz", "copydone");
                                                    File lastfile = new File(StickerPackListActivity.path + "/" + stickerPack.identifier + "/" + stickers.get(stickers.size() - 1).imageFileName);
                                                    if (lastfile.exists()) {
                                                        alertDialog.dismiss();
                                                        Intent intent = new Intent();
                                                        intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
                                                        intent.putExtra(EXTRA_STICKER_PACK_ID, stickerPack.identifier);
                                                        intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
                                                        intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPack.name);
                                                        try {
                                                            startActivityForResult(intent, ADD_PACK);
                                                        } catch (ActivityNotFoundException e) {
                                                            Toast.makeText(StickerDetailsActivity.this, "error", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                    return true;
                                                }
                                            }).submit();
                                }
                            }
                    );

                } else {
                    alertDialog.dismiss();
                    Intent intent = new Intent();
                    intent.setAction("com.whatsapp.intent.action.ENABLE_STICKER_PACK");
                    intent.putExtra(EXTRA_STICKER_PACK_ID, stickerPack.identifier);
                    intent.putExtra(EXTRA_STICKER_PACK_AUTHORITY, BuildConfig.CONTENT_PROVIDER_AUTHORITY);
                    intent.putExtra(EXTRA_STICKER_PACK_NAME, stickerPack.name);
                    try {
                        startActivityForResult(intent, ADD_PACK);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(StickerDetailsActivity.this, "error", Toast.LENGTH_LONG).show();
                    }
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
