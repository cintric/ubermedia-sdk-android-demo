package com.ubermedia;

import android.content.Context;
import android.util.Log;

import com.mopub.mobileads.CustomEventInterstitial;
import com.mopub.mobileads.MoPubErrorCode;

import java.util.Map;

import ubermedia.com.ubermedia.UMInterstitialListener;
import ubermedia.com.ubermedia.UberMedia;

public class MoPubInterstitialAdapter extends CustomEventInterstitial implements UMInterstitialListener {

    private final String CLASS_TAG = "UberMedia";

    private Context mParentContext;
    private String mAdUnit;

    private CustomEventInterstitialListener mInterstitialListener;

    @Override
    protected void loadInterstitial(Context context, CustomEventInterstitialListener customEventInterstitialListener,
                                    Map<String, Object> localExtras, Map<String, String> serverExtras) {
        Log.d(CLASS_TAG, "MOPUB CUSTOM INTERSTITIAL ADAPTER WAS CALLED");

        mInterstitialListener = customEventInterstitialListener;
        mParentContext = context;

        String adUnit = "default_ad_unit";
        String adUnitKey = "ad_unit_id";

        if (serverExtras.containsKey(adUnitKey)) {
            adUnit = serverExtras.get(adUnitKey).toString();
        }

        Log.d(CLASS_TAG, "Ad Unit Received: " + adUnit);
        mAdUnit = adUnit;

        onInterstitialLoaded();
    }

    @Override
    protected void showInterstitial() {
        Log.d(CLASS_TAG, "Showing Adapter Interstitial");
        UberMedia.getAdapterInterstitialView(mParentContext, mAdUnit, this).show();
        onInterstitialShown();
    }

    @Override
    protected void onInvalidate() {
        Log.d(CLASS_TAG, "Adapter - onInvalidate");
    }

    @Override
    public void onInterstitialLoaded() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialLoaded");
        mInterstitialListener.onInterstitialLoaded();
    }

    @Override
    public void onInterstitialShown() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialShown");
        mInterstitialListener.onInterstitialShown();
    }

    @Override
    public void onInterstitialClicked() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialClicked");
        mInterstitialListener.onInterstitialClicked();
    }

    @Override
    public void onInterstitialDismissed() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialDismissed");
        mInterstitialListener.onInterstitialDismissed();
    }

    @Override
    public void onInterstitialFailed() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialFailed");
        mInterstitialListener.onInterstitialFailed(MoPubErrorCode.INTERNAL_ERROR);
    }
}
