package com.ubermedia;

import android.content.Context;
import android.util.Log;

import com.admarvel.android.ads.omwsdkconnector.OMWCustomBanner;
import com.admarvel.android.ads.omwsdkconnector.OMWCustomBannerListener;

import java.util.Map;

import ubermedia.com.ubermedia.CBAdapterBannerView;
import ubermedia.com.ubermedia.CBListener;
import ubermedia.com.ubermedia.ClearBid;

public class AdMarvelAdapter implements OMWCustomBanner, CBListener {

    private final String CLASS_TAG = "ClearBid";
    private OMWCustomBannerListener mAdListener;

    @Override
    public void requestBannerAd(Context context, OMWCustomBannerListener omwCustomBannerListener, Map<String, String> serverParam, Map<String, Object> targetParam) {
        Log.d(CLASS_TAG, "ADMARVEL CUSTOM ADAPTER WAS CALLED");

        mAdListener = omwCustomBannerListener;

        String adUnit = "default_ad_unit";
        String adUnitKey = "UMPLC";

        if (serverParam.containsKey(adUnitKey)) {
            adUnit = serverParam.get(adUnitKey).toString();
        }

        Log.d(CLASS_TAG, "Ad Unit Received: " + adUnit);

        CBAdapterBannerView bannerView = ClearBid.getAdapterBannerView(context, adUnit, this);

        // Only use this if you don't want the view to automatically resize to ad - useful if you want to keep it full width
        // bannerView.adaptToAdSize(false);

        omwCustomBannerListener.onBannerAdReceived(bannerView);
        omwCustomBannerListener.onBannerAdExpand();

        // Ad was shown
        ClearBid.removeCacheAdPlacement(adUnit);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public boolean isAdNetworkDisabled() {
        return false;
    }

    @Override
    public void onAdClicked() {
        Log.d(CLASS_TAG, "onAdClicked");

        if (mAdListener != null) {
            mAdListener.onBannerAdClicked();
        }
    }
}