package ubermedia.com.headerbiddingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ubermedia.com.ubermedia.CBInterstitial;
import ubermedia.com.ubermedia.CBInterstitialListener;
import ubermedia.com.ubermedia.ClearBid;

public class InterstitialActivity extends AppCompatActivity implements CBInterstitialListener {

    private final String CLASS_TAG = "InterstitialActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);

        ClearBid.initializeClearBidSDK(this, "test-1-api-key", "test-1-secret-key");
        ClearBid.requestLocationPermission(this);

        Button button = (Button) findViewById(R.id.showInterstitialButton);

        final CBInterstitial interstitial = new CBInterstitial(getApplicationContext(), this, this);
        interstitial.setAdUnit("internal_test_interstitial_ad_placement");
        interstitial.load();

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                interstitial.show();
            }
        });
    }

    @Override
    public void onInterstitialLoaded() {
        Log.d(CLASS_TAG, "onInterstitialLoaded");

        TextView loadingTextView = (TextView) findViewById(R.id.interstitialLoadingTextView);
        loadingTextView.setText("Interstitial Loaded. Press show to launch it.");

        // Show button
        Button button = (Button) findViewById(R.id.showInterstitialButton);
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInterstitialShown() {
        Log.d(CLASS_TAG, "onInterstitialShown");
    }

    @Override
    public void onInterstitialClicked() {
        Log.d(CLASS_TAG, "onInterstitialClicked");
    }

    @Override
    public void onInterstitialDismissed() {
        Log.d(CLASS_TAG, "onInterstitialDismissed");
    }

    @Override
    public void onInterstitialFailed() {
        Log.d(CLASS_TAG, "onInterstitialFailed");
    }
}
