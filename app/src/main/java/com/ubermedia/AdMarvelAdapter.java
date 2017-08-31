package com.ubermedia;

import android.content.Context;
import android.util.Log;

import com.admarvel.android.ads.omwsdkconnector.OMWCustomBanner;
import com.admarvel.android.ads.omwsdkconnector.OMWCustomBannerListener;

import java.util.Map;

import ubermedia.com.ubermedia.UberMedia;

public class AdMarvelAdapter implements OMWCustomBanner {

    @Override
    public void requestBannerAd(Context context, OMWCustomBannerListener omwCustomBannerListener, Map<String, String> serverParam, Map<String, Object> targetParam) {
        Log.d("ADMARVEL", "ADMARVEL CUSTOM ADAPTER WAS CALLED");

        String adUnit = "default_ad_unit";
        String adUnitKey = "UMPLC";

        if (serverParam.containsKey(adUnitKey)) {
            adUnit = serverParam.get(adUnitKey).toString();
        }

        omwCustomBannerListener.onBannerAdReceived(UberMedia.getAdapterBannerView(context, adUnit));
        omwCustomBannerListener.onBannerAdExpand();
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
}