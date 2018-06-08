package com.ubermedia;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;

import ubermedia.com.ubermedia.UMAdapterBannerView;
import ubermedia.com.ubermedia.UMListener;
import ubermedia.com.ubermedia.UberMedia;

public class DfpBannerAdapter implements CustomEventBanner, UMListener {
    private final String CLASS_TAG = "UberMedia";


    private CustomEventBannerListener mAdListener;

    @Override
    public void requestBannerAd(Context context,
                                CustomEventBannerListener customEventBannerListener,
                                String adUnit,
                                AdSize adSize,
                                MediationAdRequest mediationAdRequest,
                                Bundle bundle) {
        Log.d(CLASS_TAG, "DFP CUSTOM ADAPTER WAS CALLED");


        mAdListener = customEventBannerListener;

        String adUnitKey = "ad_unit_id";

        Log.d(CLASS_TAG, "Ad Unit Received: " + adUnit);

        UMAdapterBannerView bannerView = UberMedia.getAdapterBannerView(context, adUnit, this);
        UberMedia.removeCacheAdPlacement(adUnit);

        Log.d(CLASS_TAG, bannerView.CurrentBid + "");

        if (bannerView.CurrentBid > 0.0) {

            mAdListener.onAdLoaded(bannerView);

        } else {

            mAdListener.onAdFailedToLoad(3);

        }

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
    public void onAdClicked() {

        Log.d(CLASS_TAG, "onAdClicked");

        if (mAdListener != null) {
            mAdListener.onAdClicked();
        }

    }
}
