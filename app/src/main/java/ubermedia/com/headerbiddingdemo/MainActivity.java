package ubermedia.com.headerbiddingdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ubermedia.com.ubermedia.UberMedia;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UberMedia.initializeUberMediaSDK(this, "test-1-api-key", "test-1-secret-key");
    }
}


