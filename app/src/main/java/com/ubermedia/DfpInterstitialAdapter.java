package com.ubermedia;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitial;
import com.google.android.gms.ads.mediation.customevent.CustomEventInterstitialListener;

import ubermedia.com.ubermedia.UMInterstitialListener;
import ubermedia.com.ubermedia.UberMedia;

public class DfpInterstitialAdapter implements CustomEventInterstitial,UMInterstitialListener {
    private final String CLASS_TAG = "UberMedia";


    private CustomEventInterstitialListener mInterstitialLisener;

    private Context mParentContext;
    private String mAdUnit;

    @Override
    public void requestInterstitialAd(Context context,
                                      CustomEventInterstitialListener customEventInterstitialListener,
                                      String adUnit,
                                      MediationAdRequest mediationAdRequest,
                                      Bundle bundle) {
        Log.d(CLASS_TAG, "DFP CUSTOM INTERSTITIAL ADAPTER WAS CALLED");

        mInterstitialLisener = customEventInterstitialListener;
        mParentContext = context;

        Log.d(CLASS_TAG, "Ad Unit Received: " + adUnit);
        mAdUnit = adUnit;


        onInterstitialLoaded();

    }

    @Override
    public void showInterstitial() {
        Log.d(CLASS_TAG, "Showing Adapter Interstitial");
        UberMedia.getAdapterInterstitialView(mParentContext, mAdUnit, this).show();
    }

    @Override
    public void onDestroy() {
        Log.d(CLASS_TAG, "Adapter - onDestroy");

    }

    @Override
    public void onPause() {
        Log.d(CLASS_TAG, "Adapter - onPause");

    }

    @Override
    public void onResume() {
        Log.d(CLASS_TAG, "Adapter - onResume");

    }

    @Override
    public void onInterstitialLoaded() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialLoaded");
        mInterstitialLisener.onAdLoaded();
    }

    @Override
    public void onInterstitialShown() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialShown");
    }

    @Override
    public void onInterstitialClicked() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialClicked");
        mInterstitialLisener.onAdClicked();
    }

    @Override
    public void onInterstitialDismissed() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialDismissed");
        mInterstitialLisener.onAdClosed();
    }

    @Override
    public void onInterstitialFailed() {
        Log.d(CLASS_TAG, "Adapter - onInterstitialFailed");
        mInterstitialLisener.onAdFailedToLoad(3);
    }
}
