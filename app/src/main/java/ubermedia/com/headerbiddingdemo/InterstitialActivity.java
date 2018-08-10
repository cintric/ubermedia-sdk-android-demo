package ubermedia.com.headerbiddingdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.util.Map;

import ubermedia.com.ubermedia.UberMedia;

public class InterstitialActivity extends AppCompatActivity {

    private final String CLASS_TAG = "UberMedia";

    private final Handler mHandler = new Handler();
    private final int mAdRefreshRate = 45000;
    private final int mTimeBeforeFirstAdIsShown = 7000;
    private final String adUnit = "";
    private final String mDFPAdUnit = "";
    private Runnable mAdRefreshRunnable;
    private PublisherInterstitialAd mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        UberMedia.preCacheInterstitialPlacement(getApplicationContext(), adUnit);

        startRefreshTimer();

        Button button = (Button) findViewById(R.id.showInterstitialButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View
                                        arg0) {
                mInterstitial.show();
            }
        });


    }

    private PublisherAdRequest.Builder getKeywordsAdRequest() {
        PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        Map<String, Object> targetParams = UberMedia.getTargetParams(adUnit);
        for (Map.Entry<String, Object> entry : targetParams.entrySet()) {
            builder.addCustomTargeting(entry.getKey(), entry.getValue().toString());
        }

        return builder;
    }

    private void startRefreshTimer() {
        stopRefreshTimer();

        Log.d(CLASS_TAG, "Starting Ad Refresh Timer");

        mAdRefreshRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d(CLASS_TAG, "Refreshing Interstitial Ad");

                String targetKeywords = UberMedia.getTargetParamsAsString(adUnit);
                Log.d(CLASS_TAG, targetKeywords);

                mInterstitial = new PublisherInterstitialAd(InterstitialActivity.this);
                mInterstitial.setAdUnitId(mDFPAdUnit);
                mInterstitial.loadAd(getKeywordsAdRequest().build());

                mInterstitial.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {

                        Log.d(CLASS_TAG, "onInterstitialLoaded");

                        if (mInterstitial.isLoaded()) {

                            setTextViewText("Interstitial Loaded. Press show to launch it.");

                            // Show button
                            Button button = (Button) findViewById(R.id.showInterstitialButton);
                            button.setVisibility(View.VISIBLE);

                            button.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View
                                                            arg0) {
                                    mInterstitial.show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Log.d(CLASS_TAG, "onInterstitialFailed");
                        setTextViewText("onInterstitialFailed");

                        UberMedia.preCacheInterstitialPlacement(getApplicationContext(), adUnit);
                    }

                    @Override
                    public void onAdOpened() {
                        Log.d(CLASS_TAG, "onInterstitialShown");
                        setTextViewText("onInterstitialShown");

                        // Hide button
                        Button button = (Button) findViewById(R.id.showInterstitialButton);
                        button.setVisibility(View.INVISIBLE);

                        UberMedia.preCacheInterstitialPlacement(getApplicationContext(), adUnit);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        Log.d(CLASS_TAG, "onInterstitialDismissed");
                        setTextViewText("onInterstitialDismissed");
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


    private void setTextViewText(String message) {
        TextView loadingTextView = (TextView) findViewById(R.id.interstitialLoadingTextView);
        loadingTextView.setText(message);
    }
}
