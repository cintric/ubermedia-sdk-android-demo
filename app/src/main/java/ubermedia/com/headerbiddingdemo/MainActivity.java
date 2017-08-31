package ubermedia.com.headerbiddingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.admarvel.android.ads.AdMarvelView;

import ubermedia.com.ubermedia.UberMedia;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String adUnit = "admarvel-adapter-placement-test1";
        final String partnerId = "";
        final String siteId = "";

        UberMedia.initializeUberMediaSDK(this, "test-1-api-key", "test-1-secret-key");
        UberMedia.requestLocationPermission(this);
        UberMedia.preCacheAdPlacement(getApplicationContext(), adUnit);

        AdMarvelView adMarvelView = (AdMarvelView) findViewById(R.id.ad);
        adMarvelView.requestNewAd(UberMedia.getTargetParams(adUnit), partnerId, siteId);
    }
}