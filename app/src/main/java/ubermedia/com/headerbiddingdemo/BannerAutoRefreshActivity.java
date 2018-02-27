package ubermedia.com.headerbiddingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import ubermedia.com.ubermedia.UberMedia;

public class BannerAutoRefreshActivity extends AppCompatActivity implements MoPubView.BannerAdListener {
    private final String CLASS_TAG = "UberMedia";

    /*     Insert your own ClearBid and MoPub ad units here    */
    private final String adUnit = "";
    private final String moPubAdUnit = "";

    private MoPubView mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_auto_refresh);

        // You only need to call this method once in the app, usually from the MainActivity
        UberMedia.initializeUberMediaSDK(this, "insert api key", "insert secret key");
        UberMedia.requestLocationPermission(this);

        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

        //UberMedia.DisableLogging();

        String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
        Log.d(CLASS_TAG, targetKeywords);

        mBannerView = (MoPubView) findViewById(R.id.bannerAutoRefreshView);
        mBannerView.setAdUnitId(moPubAdUnit);
        mBannerView.setKeywords(targetKeywords);
        mBannerView.setBannerAdListener(BannerAutoRefreshActivity.this);
        mBannerView.setAutorefreshEnabled(true);

        mBannerView.loadAd();
    }

    @Override
    public void onBannerLoaded(MoPubView banner) {
        Log.d(CLASS_TAG, "onBannerLoaded");

        // Cache a new ad
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

        String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
        mBannerView.setKeywords(targetKeywords);
        Log.d(CLASS_TAG, targetKeywords);
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
        Log.d(CLASS_TAG, "onBannerFailed: " + errorCode.toString());

        // Cache a new ad
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{320, 50});

        String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
        mBannerView.setKeywords(targetKeywords);
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
