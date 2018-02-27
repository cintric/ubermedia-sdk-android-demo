package ubermedia.com.headerbiddingdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import ubermedia.com.ubermedia.UberMedia;

public class BannerRefreshTimerActivity extends AppCompatActivity implements MoPubView.BannerAdListener {
    private final String CLASS_TAG = "UberMedia";

    private final Handler mHandler = new Handler();
    private Runnable mAdRefreshRunnable;

    private final int mAdRefreshRate = 15000;
    private final int mTimeBeforeFirstAdIsShown = 7000;

    /*     Insert your own ClearBid and MoPub ad units here    */
    private final String adUnit = "";
    private final String moPubAdUnit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_refresh_timer);

        // You only need to call this method once in the app, usually from the MainActivity
        UberMedia.initializeUberMediaSDK(this, "insert api key", "insert secret key");
        UberMedia.requestLocationPermission(this);

        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

        //UberMedia.DisableLogging();

        startRefreshTimer();
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

                MoPubView moPubView = (MoPubView) findViewById(R.id.bannerTimerView);
                moPubView.setAdUnitId(moPubAdUnit);
                moPubView.setKeywords(targetKeywords);
                moPubView.setBannerAdListener(BannerRefreshTimerActivity.this);

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
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
        Log.d(CLASS_TAG, "onBannerFailed: " + errorCode.toString());

        // Cache a new ad
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

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