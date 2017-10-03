package com.ubermedia;

import android.content.Context;
import android.util.Log;

import com.mopub.mobileads.CustomEventBanner;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import ubermedia.com.ubermedia.UMAdapterBannerView;
import ubermedia.com.ubermedia.UberMedia;

public class MoPubAdapter extends CustomEventBanner {
    private final String CLASS_TAG = "UberMedia";

    private CustomEventBannerListener mBannerListener;

    @Override
    protected void loadBanner(final Context context, final CustomEventBanner.CustomEventBannerListener customEventBannerListener,
                              final Map<String, Object> localExtras,
                              final Map<String, String> serverExtras) {

        Log.d(CLASS_TAG, "MOPUB CUSTOM ADAPTER WAS CALLED");

        String adUnit = "default_ad_unit";
        String adUnitKey = "ad_unit_id";

        if (serverExtras.containsKey(adUnitKey)) {
            adUnit = serverExtras.get(adUnitKey).toString();
        }

        Log.d(CLASS_TAG, "Ad Unit Received: " + adUnit);

        UMAdapterBannerView bannerView = UberMedia.getAdapterBannerView(context, adUnit);

        if (bannerView.CurrentBid > 0.0) {
            // Use this instead if you want to change the background color of the view
            // UMAdapterBannerView bannerView = UberMedia.getAdapterBannerView(context, adUnit, "#000");

            // Only use this if you don't want the view to automatically resize to ad - useful if you want to keep it full width
            // bannerView.adaptToAdSize(false);

            customEventBannerListener.onBannerLoaded(bannerView);
            customEventBannerListener.onBannerExpanded();
        } else {
            customEventBannerListener.onBannerFailed(MoPubErrorCode.NO_FILL);
        }

        // Ad was shown
        UberMedia.removeCacheAdPlacement(adUnit);
    }

    @Override
    protected void onInvalidate() {
    }
}