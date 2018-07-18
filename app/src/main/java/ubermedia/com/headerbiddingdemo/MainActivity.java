package ubermedia.com.headerbiddingdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.admarvel.android.ads.AdMarvelUtils;
import com.admarvel.android.ads.AdMarvelView;

import java.util.Map;

import ubermedia.com.ubermedia.ClearBid;

public class MainActivity extends AppCompatActivity {
    private final String CLASS_TAG = "ClearBid";

    private final Handler mHandler = new Handler();
    private Runnable mAdRefreshRunnable;

    private final int mAdRefreshRate = 15000;
    private final int mTimeBeforeFirstAdIsShown = 7000;

    private final String adUnit = "";
    private final String partnerId = "";
    private final String siteId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClearBid.initializeClearBidSDK(this, "test-1-api-key", "test-1-secret-key");
        ClearBid.requestLocationPermission(this);

        ClearBid.preCacheAdPlacement(getApplicationContext(), adUnit);

        //ClearBid.DisableLogging();

        startRefreshTimer();
    }

    private void startRefreshTimer() {
        stopRefreshTimer();

        Log.d(CLASS_TAG, "Starting Ad Refresh Timer");

        mAdRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(CLASS_TAG, "Refreshing Ad");

                Map<String, Object> targetParams = ClearBid.getTargetParams(adUnit);

                for (String name: targetParams.keySet()){
                    String key = name.toString();
                    String value = targetParams.get(name).toString();

                    Log.d(CLASS_TAG, key + " " + value);
                }

                AdMarvelView adMarvelView = (AdMarvelView) findViewById(R.id.ad);
                adMarvelView.requestNewAd(ClearBid.getTargetParams(adUnit), partnerId, siteId);

                adMarvelView.setListener(new AdMarvelView.AdMarvelViewListener() {
                    @Override
                    public void onRequestAd(AdMarvelView arg0) {
                        // TODO Auto-generated method
                        Log.d(CLASS_TAG, "onRequestAd");
                    }

                    @Override
                    public void onReceiveAd(AdMarvelView arg0) {
                        // TODO Auto-generated method
                        Log.d(CLASS_TAG, "onReceiveAd");

                        // Cache a new ad
                        ClearBid.preCacheAdPlacement(getApplicationContext(), adUnit);
                    }

                    @Override
                    public
                    void onFailedToReceiveAd(AdMarvelView arg0, int arg1, AdMarvelUtils.ErrorReason arg2) {
                        // TODO Auto-generated method
                        Log.d(CLASS_TAG, arg1 + "");
                        // AdMarvel Error Codes: https://wiki.operamediaworks.com/display/AMS/Error+Codes
                        if (arg1 != 304) {
                            // Cache a new ad
                            ClearBid.preCacheAdPlacement(getApplicationContext(), adUnit);
                            
                            Log.d(CLASS_TAG, "No ad was received. Call your app waterfall here.");
                            // Call your app waterfall here
                        }
                    }

                    @Override
                    public void onExpand(AdMarvelView arg0) {
                        // TODO Auto-generated method
                        Log.d(CLASS_TAG, "onExpand");
                    }

                    @Override
                    public void onClose(AdMarvelView arg0) {
                        // TODO Auto-generated method
                        Log.d(CLASS_TAG, "onClose");
                    }

                    @Override
                    public void onClickAd(AdMarvelView arg0, String arg1) {
                        // TODO Auto-generated method
                        Log.d(CLASS_TAG, "onClickAd");
                    }
                });

                mHandler.postDelayed(this, mAdRefreshRate);
            }
        };

        // Change postDelayed to post if you don't want to wait before first ad is shown (waiting allows HB SDK to cache ad)
        mHandler.postDelayed(mAdRefreshRunnable, mTimeBeforeFirstAdIsShown);
    }

    private void stopRefreshTimer() {
        Log.d(CLASS_TAG, "Stopping Ad Refresh Timer");

        // Clear existing timer if exists
        mHandler.removeCallbacks(mAdRefreshRunnable);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        Log.d(CLASS_TAG, "onWindowFocusChanged " + Boolean.toString(hasWindowFocus));

        if (hasWindowFocus) {
            startRefreshTimer();
            return;
        }

        stopRefreshTimer();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        Log.d(CLASS_TAG, "onDetachedFromWindow");
        stopRefreshTimer();
    }
}
