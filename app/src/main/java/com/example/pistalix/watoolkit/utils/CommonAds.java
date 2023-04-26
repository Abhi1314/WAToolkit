package com.example.pistalix.watoolkit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.pistalix.watoolkit.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class CommonAds
{
    public static Context maincon;
    public static InterstitialAd mInterstitialAd;
    public static int adCounter = 0;
    public static int adDisplayCounter = 2;

    public static RewardedAd mRewardedAd;
    public static boolean rewardCheck = false;

    public static void loadBannerAds(Activity activity, FrameLayout adsContainer)
    {
        AdView adView = new AdView(activity);
        AdSize adSize = getAdSize(activity);
        adView.setAdSize(adSize);
        adView.setAdUnitId(activity.getString(R.string.banner_ads1));
        adsContainer.removeAllViews();
        adsContainer.addView(adView);
        adView.loadAd(new AdRequest.Builder().build());

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
            {
                AdView adView2 = new AdView(activity);
                AdSize adSize2 = getAdSize(activity);
                adView2.setAdSize(adSize2);
                adView2.setAdUnitId(activity.getString(R.string.banner_ads2));
                adsContainer.removeAllViews();
                adsContainer.addView(adView2);
                adView2.loadAd(new AdRequest.Builder().build());

                adView2.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
                    {
                        AdView adView3 = new AdView(activity);
                        AdSize adSize3 = getAdSize(activity);
                        adView3.setAdSize(adSize3);
                        adView3.setAdUnitId(activity.getString(R.string.banner_ads3));
                        adsContainer.removeAllViews();
                        adsContainer.addView(adView3);
                        adView3.loadAd(new AdRequest.Builder().build());
                    }
                });
            }
        });
    }

    public static AdSize getAdSize(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public static void loadInterstitialAds(Context context)
    {
        maincon = context;
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, context.getString(R.string.interstitial_ads1), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
                loadInter2(context);
            }
        });
    }

    public static void loadInter2(Context context)
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, context.getString(R.string.interstitial_ads2), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
                loadInter3(context);
            }
        });
    }

    public static void loadInter3(Context context)
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, context.getString(R.string.interstitial_ads3), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mInterstitialAd = null;
            }
        });
    }

    public static void showInterstitialAds(Activity activity, Intent intent, boolean isFinished)
    {
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent()
            {
                mInterstitialAd = null;
                loadInterstitialAds(maincon);
                if (intent != null)
                    activity.startActivity(intent);
                if (isFinished) {
                    if (activity != null && !activity.isFinishing())
                        activity.finish();
                }
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                mInterstitialAd = null;
            }

            @Override
            public void onAdShowedFullScreenContent() {
                mInterstitialAd = null;
            }
        });

        mInterstitialAd.show(activity);
    }

    public static void loadNativeAd(Context con, FrameLayout adplaceholder)
    {
        AdLoader adLoader = new AdLoader.Builder(con, con.getString(R.string.native_ads1))
                .forNativeAd(NativeAd -> {
                    RelativeLayout rllayout = (RelativeLayout) LayoutInflater.from(con).inflate(R.layout.native_ad_layout, null);
                    populateNativeAdView(NativeAd, (NativeAdView) rllayout.findViewById(R.id.unified));
                    adplaceholder.removeAllViews();
                    adplaceholder.addView(rllayout);
                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
                    {
                        AdLoader adLoader1 = new AdLoader.Builder(con, con.getString(R.string.native_ads2))
                                .forNativeAd(NativeAd1 -> {
                                    RelativeLayout rllayout = (RelativeLayout) LayoutInflater.from(con).inflate(R.layout.native_ad_layout, null);
                                    populateNativeAdView(NativeAd1, (NativeAdView) rllayout.findViewById(R.id.unified));
                                    adplaceholder.removeAllViews();
                                    adplaceholder.addView(rllayout);
                                }).withAdListener(new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
                                    {
                                        AdLoader adLoader2 = new AdLoader.Builder(con, con.getString(R.string.native_ads3))
                                                .forNativeAd(NativeAd2 -> {
                                                    RelativeLayout rllayout = (RelativeLayout) LayoutInflater.from(con).inflate(R.layout.native_ad_layout, null);
                                                    populateNativeAdView(NativeAd2, (NativeAdView) rllayout.findViewById(R.id.unified));
                                                    adplaceholder.removeAllViews();
                                                    adplaceholder.addView(rllayout);
                                                }).build();

                                        adLoader2.loadAd(new AdRequest.Builder().build());
                                    }
                                }).build();

                        adLoader1.loadAd(new AdRequest.Builder().build());
                    }
                }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView)
    {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));

        try {
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nativeAd.getBody() == null) {
            (adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }

    public static void loadNativeAdFull(Context con, FrameLayout adplaceholder)
    {
        AdLoader adLoader = new AdLoader.Builder(con, con.getString(R.string.native_ads1))
                .forNativeAd(NativeAd -> {
                    RelativeLayout rllayout = (RelativeLayout) LayoutInflater.from(con).inflate(R.layout.fullnative_ad_layout, null);
                    populateNativeAdViewfull(NativeAd, (NativeAdView) rllayout.findViewById(R.id.unified));
                    adplaceholder.removeAllViews();
                    adplaceholder.addView(rllayout);
                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
                    {
                        AdLoader adLoader1 = new AdLoader.Builder(con, con.getString(R.string.native_ads2))
                                .forNativeAd(NativeAd1 -> {
                                    RelativeLayout rllayout = (RelativeLayout) LayoutInflater.from(con).inflate(R.layout.fullnative_ad_layout, null);
                                    populateNativeAdViewfull(NativeAd1, (NativeAdView) rllayout.findViewById(R.id.unified));
                                    adplaceholder.removeAllViews();
                                    adplaceholder.addView(rllayout);
                                }).withAdListener(new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
                                    {
                                        AdLoader adLoader2 = new AdLoader.Builder(con, con.getString(R.string.native_ads3))
                                                .forNativeAd(NativeAd2 -> {
                                                    RelativeLayout rllayout = (RelativeLayout) LayoutInflater.from(con).inflate(R.layout.fullnative_ad_layout, null);
                                                    populateNativeAdViewfull(NativeAd2, (NativeAdView) rllayout.findViewById(R.id.unified));
                                                    adplaceholder.removeAllViews();
                                                    adplaceholder.addView(rllayout);
                                                }).build();

                                        adLoader2.loadAd(new AdRequest.Builder().build());
                                    }
                                }).build();

                        adLoader1.loadAd(new AdRequest.Builder().build());
                    }
                }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public static void populateNativeAdViewfull(NativeAd nativeAd, NativeAdView adView)
    {
        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.GONE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }

    public static void loadRewardAds(Context context)
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, context.getString(R.string.reward_ads1), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mRewardedAd = null;
                loadRewardAds2(context);
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
            }
        });
    }

    public static void loadRewardAds2(Context context)
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, context.getString(R.string.reward_ads2), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mRewardedAd = null;
                loadRewardAds3(context);
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
            }
        });
    }

    public static void loadRewardAds3(Context context)
    {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, context.getString(R.string.reward_ads3), adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                mRewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
            }
        });
    }
}
