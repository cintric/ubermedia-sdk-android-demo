package ubermedia.com.headerbiddingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import ubermedia.com.ubermedia.UberMedia;

public class MainActivity extends AppCompatActivity implements MoPubView.BannerAdListener {
    private final String CLASS_TAG = "UberMedia";

    private final Handler mHandler = new Handler();
    private Runnable mAdRefreshRunnable;

    private final int mAdRefreshRate = 15000;
    private final int mTimeBeforeFirstAdIsShown = 7000;

    private final String adUnit = "";
    private final String moPubAdUnit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UberMedia.initializeUberMediaSDK(this, "e7d29a8bdbc9a7814538329a6be2c38d", "36e74c30afc1ab5d30319b4c0408e6455dab016c");
        UberMedia.requestLocationPermission(this);

        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit);

        //UberMedia.DisableLogging();

        startRefreshTimer();

        Button button = (Button) findViewById(R.id.launchInterstitialActivityButton);

        // Open interstitial test activity
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this,
                        InterstitialActivity.class);

                startActivity(myIntent);
            }
        });
    }

    private void startRefreshTimer() {
        stopRefreshTimer();

        Log.d(CLASS_TAG, "Starting Ad Refresh Timer");

        mAdRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(CLASS_TAG, "Refreshing Ad");

                String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
                Log.d(CLASS_TAG, targetKeywords);

                MoPubView moPubView = (MoPubView) findViewById(R.id.adview);
                moPubView.setAdUnitId(moPubAdUnit);
                moPubView.setKeywords(targetKeywords);
                moPubView.setBannerAdListener(MainActivity.this);

                moPubView.loadAd();

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

    @Override
    public void onBannerLoaded(MoPubView banner) {
        Log.d(CLASS_TAG, "onBannerLoaded");

        // Cache a new ad
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit);
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
        Log.d(CLASS_TAG, "onBannerFailed: " + errorCode.toString());

        // Cache a new ad
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit);

        Log.d(CLASS_TAG, "No ad was received. Call your app waterfall here.");
        // Call your app waterfall here
    }

    @Override
    public void onBannerClicked(MoPubView banner) {
        Log.d(CLASS_TAG, "onBannerClicked");
    }

    @Override
    public void onBannerExpanded(MoPubView banner) {
        Log.d(CLASS_TAG, "onBannerExpanded");
    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {
        Log.d(CLASS_TAG, "onBannerCollapsed");
    }
}