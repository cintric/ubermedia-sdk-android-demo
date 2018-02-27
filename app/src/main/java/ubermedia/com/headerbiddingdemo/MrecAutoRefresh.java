package ubermedia.com.headerbiddingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import ubermedia.com.ubermedia.UberMedia;

public class MrecAutoRefresh extends AppCompatActivity implements MoPubView.BannerAdListener {
    private final String CLASS_TAG = "UberMedia";

    /*     Insert your own ClearBid and MoPub ad units here    */
    private final String adUnit = "";
    private final String moPubAdUnit = "";

    private MoPubView mMrecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrec_auto_refresh);

        // You only need to call this method once in the app, usually from the MainActivity
        UberMedia.initializeUberMediaSDK(this, "insert api key", "insert secret key");
        UberMedia.requestLocationPermission(this);

        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{300, 250});

        //UberMedia.DisableLogging();

        String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
        Log.d(CLASS_TAG, targetKeywords);

        mMrecView = (MoPubView) findViewById(R.id.mrecTimerView);
        mMrecView.setAdUnitId(moPubAdUnit);
        mMrecView.setKeywords(targetKeywords);
        mMrecView.setBannerAdListener(MrecAutoRefresh.this);
        mMrecView.setAutorefreshEnabled(true);

        mMrecView.loadAd();
    }

    @Override
    public void onBannerLoaded(MoPubView banner) {
        Log.d(CLASS_TAG, "onBannerLoaded");

        // Cache a new ad
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{300, 250});

        String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
        mMrecView.setKeywords(targetKeywords);
        Log.d(CLASS_TAG, targetKeywords);
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
        Log.d(CLASS_TAG, "onBannerFailed: " + errorCode.toString());

        // Cache a new ad
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{300, 250});

        String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
        mMrecView.setKeywords(targetKeywords);
        Log.d(CLASS_TAG, targetKeywords);

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
