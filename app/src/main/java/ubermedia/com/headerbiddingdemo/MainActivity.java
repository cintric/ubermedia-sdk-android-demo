package ubermedia.com.headerbiddingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import java.util.Map;

import ubermedia.com.ubermedia.ClearBid;

public class MainActivity extends AppCompatActivity  {
    private final String CLASS_TAG = "ClearBid";

    private final Handler mHandler = new Handler();
    private Runnable mAdRefreshRunnable;

    private final int mAdRefreshRate = 30000;
    private final int mTimeBeforeFirstAdIsShown = 7000;

    private final String adUnit = "";

    private PublisherAdView mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClearBid.initializeClearBidSDK(this, "test-1-api-key", "test-1-api-secret");
        ClearBid.requestLocationPermission(this);

        ClearBid.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

        //ClearBid.DisableLogging();

        Log.d(CLASS_TAG, "Refreshing Ad");

        String targetKeywords = ClearBid.getTargetParamsAsString(adUnit);
        Log.d(CLASS_TAG, targetKeywords);

        mBannerView = (PublisherAdView) findViewById(R.id.adview);

        mBannerView.loadAd(getKeywordsAdRequest().build());

        mBannerView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                Log.d(CLASS_TAG, "onBannerLoaded");

                // Cache a new ad
                ClearBid.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

                String targetKeywords = ClearBid.getTargetParamsAsString(adUnit);
                Log.d(CLASS_TAG, targetKeywords);            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                Log.d(CLASS_TAG, "onBannerFailed: " + errorCode+"");

                // Cache a new ad
                ClearBid.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

                String targetKeywords = ClearBid.getTargetParamsAsString(adUnit);
                Log.d(CLASS_TAG, targetKeywords);

                Log.d(CLASS_TAG, "No ad was received. Call your app waterfall here.");
                // Call your app waterfall here
            }

            @Override
            public void onAdOpened() {
                Log.d(CLASS_TAG, "onAdOpened");

            }

            @Override
            public void onAdLeftApplication() {
                Log.d(CLASS_TAG, "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                Log.d(CLASS_TAG, "onAdLeftApplication");

            }
        });


        Button button = (Button) findViewById(R.id.launchInterstitialActivityButton);

        // Open interstitial test activity
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        InterstitialActivity.class);

                startActivity(myIntent);
            }
        });

        startRefreshTimer();
    }

    private void startRefreshTimer() {
        stopRefreshTimer();

        Log.d(CLASS_TAG, "Starting Ad Refresh Timer");

        mAdRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(CLASS_TAG, "Refreshing Ad");

                String targetKeywords = ClearBid.getTargetParamsAsString(adUnit);
                Log.d(CLASS_TAG, targetKeywords);

                mBannerView.loadAd(getKeywordsAdRequest().build());

                mHandler.postDelayed(this, mAdRefreshRate);
            }
        };

        // Change postDelayed to post if you don't want to wait before first ad is shown (waiting allows HB SDK to cache ad)
        mHandler.postDelayed(mAdRefreshRunnable, mTimeBeforeFirstAdIsShown);
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

    private void stopRefreshTimer() {
        Log.d(CLASS_TAG, "Stopping Ad Refresh Timer");

        // Clear existing timer if exists
        mHandler.removeCallbacks(mAdRefreshRunnable);
    }

    private PublisherAdRequest.Builder getKeywordsAdRequest() {
        PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        Map<String, Object> targetParams = ClearBid.getTargetParams(adUnit);
        for (Map.Entry<String, Object> entry : targetParams.entrySet()) {
            builder.addCustomTargeting(entry.getKey(), entry.getValue().toString());
        }

        return builder;
    }

}
