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

import ubermedia.com.ubermedia.UberMedia;

public class MainActivity extends AppCompatActivity {
    private final String CLASS_TAG = "UberMedia";

    private final String adUnit = "";
    private PublisherAdView mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UberMedia.initializeUberMediaSDK(this, "insert api key", "insert secret key");
        UberMedia.requestLocationPermission(this);

        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{300, 250});

        //UberMedia.DisableLogging();

        Log.d(CLASS_TAG, "Refreshing Ad");

        String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
        Log.d(CLASS_TAG, targetKeywords);

        mBannerView = (PublisherAdView) findViewById(R.id.adview);

        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();

        mBannerView.loadAd(adRequest);

        mBannerView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.d(CLASS_TAG, "onBannerLoaded");

                // Cache a new ad
                UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{300, 250});

                String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
                Log.d(CLASS_TAG, targetKeywords);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                Log.d(CLASS_TAG, "onBannerFailed: " + errorCode + "");

                // Cache a new ad
                UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit, new int[]{300, 250});

                String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
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
    }


}
