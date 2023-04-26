package com.example.pistalix.watoolkit.sticker;

import static com.example.pistalix.watoolkit.utils.UtilKt.gone;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.pistalix.watoolkit.R;
import com.example.pistalix.watoolkit.Sticker;
import com.example.pistalix.watoolkit.StickerPack;
import com.example.pistalix.watoolkit.adapter.StickerAdapter;
import com.example.pistalix.watoolkit.databinding.ActivityStickerPackListBinding;
import com.example.pistalix.watoolkit.task.GetStickers;
import com.example.pistalix.watoolkit.utils.CommonAds;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StickerPackListActivity extends AppCompatActivity implements GetStickers.Callbacks {

    public static final String EXTRA_STICKER_PACK_ID = "sticker_pack_id";
    public static final String EXTRA_STICKER_PACK_AUTHORITY = "sticker_pack_authority";
    public static final String EXTRA_STICKER_PACK_NAME = "sticker_pack_name";
    public static final String EXTRA_STICKERPACK = "stickerpack";
    public static String path;
    ArrayList<StickerPack> stickerPacks = new ArrayList<>();
    List<Sticker> mStickers;
    List<String> mEmojis;
    Dialog alertDialog;
    ActivityStickerPackListBinding activityStickerPackListBinding;
    SharedPreferences sharedads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStickerPackListBinding = DataBindingUtil.setContentView(this, R.layout.activity_sticker_pack_list);
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE);
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, activityStickerPackListBinding.adsContainer);
            } else {
                gone(activityStickerPackListBinding.adsContainer);
            }
            activityStickerPackListBinding.toolBarLayout.setName("Sticker Packs");
            activityStickerPackListBinding.toolBarLayout.backImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            alertDialog =  new Dialog(this, R.style.DialogCustomTheme);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setContentView(R.layout.downloading_sticker_dialog);
            alertDialog.setCancelable(false);
            TextView tvName =alertDialog.findViewById(R.id.loadingSticker);
            tvName.setText(getString(R.string.loading));
            alertDialog.show();
            stickerPacks = new ArrayList<>();
            path = getFilesDir() + "/" + "stickers_asset";
            mStickers = new ArrayList<>();
            mEmojis = new ArrayList<>();

            mEmojis.add("");
            new GetStickers(this, this, getResources().getString(R.string.json_link)).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void SaveImage(Bitmap finalBitmap, String name, String identifier) {

        String root = path + "/" + identifier;
        File myDir = new File(root);
        myDir.mkdirs();
        File file = new File(myDir, name);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.WEBP, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void SaveTryImage(Bitmap finalBitmap, String name, String identifier) {

        String root = path + "/" + identifier;
        File myDir = new File(root + "/" + "try");
        myDir.mkdirs();

        File file = new File(myDir, name);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 40, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onListLoaded(String jsonResult, boolean jsonSwitch) {
        try {
            if (jsonResult != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(jsonResult);
                    String android_play_store_link = jsonResponse.getString("android_play_store_link");
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("sticker_packs");
                    for (int i = 0; i < jsonMainNode.length(); i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                        stickerPacks.add(new StickerPack(
                                jsonChildNode.getString("identifier"),
                                jsonChildNode.getString("name"),
                                jsonChildNode.getString("publisher"),
                                getLastBitFromUrl(jsonChildNode.getString("tray_image_file")).replace(" ", "_"),
                                jsonChildNode.getString("publisher_email"),
                                jsonChildNode.getString("publisher_website"),
                                jsonChildNode.getString("privacy_policy_website"),
                                jsonChildNode.getString("license_agreement_website")
                        ));
                        JSONArray stickers = jsonChildNode.getJSONArray("stickers");

                        for (int j = 0; j < stickers.length(); j++) {
                            JSONObject jsonStickersChildNode = stickers.getJSONObject(j);
                            mStickers.add(new Sticker(
                                    getLastBitFromUrl(jsonStickersChildNode.getString("image_file")).replace(".png", ".webp"),
                                    mEmojis
                            ));
                        }
                        Hawk.put(jsonChildNode.getString("identifier"), mStickers);
                        stickerPacks.get(i).setAndroidPlayStoreLink(android_play_store_link);
                        stickerPacks.get(i).setStickers(Hawk.get(jsonChildNode.getString("identifier"), new ArrayList<Sticker>()));
                        mStickers.clear();
                    }
                    Hawk.put("sticker_packs", stickerPacks);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StickerAdapter adapter = new StickerAdapter(this, stickerPacks);
                activityStickerPackListBinding.rvStickerList.setAdapter(adapter);
                alertDialog.dismiss();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static String getLastBitFromUrl(final String url) {
        return url.replaceFirst(".*/([^/?]+).*", "$1");
    }
}
