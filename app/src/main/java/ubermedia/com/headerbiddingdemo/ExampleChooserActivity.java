package ubermedia.com.headerbiddingdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExampleChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_chooser);

        ListView adExamplesListView = (ListView) findViewById(R.id.adExamplesList);

        String[] listItems = new String[]{
                "Banner - Refresh Timer",
                "Banner - MoPub Auto Refresh",
                "Mrec - Auto Refresh",
                "Interstitial - Refresh Timer"
        };

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        adExamplesListView.setAdapter(adapter);

        final Context context = this;
        adExamplesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent bannerRefreshTimerIntent = new Intent(context, BannerRefreshTimerActivity.class);
                        context.startActivity(bannerRefreshTimerIntent);
                        break;
                    case 1:
                        Intent bannerAutoRefreshIntent = new Intent(context, BannerAutoRefreshActivity.class);
                        context.startActivity(bannerAutoRefreshIntent);
                        break;
                    case 2:
                        Intent mrecAutoRefreshIntent = new Intent(context, MrecAutoRefresh.class);
                        context.startActivity(mrecAutoRefreshIntent);
                        break;
                    case 3:
                        Intent interstitialIntent = new Intent(context, InterstitialActivity.class);
                        context.startActivity(interstitialIntent);
                        break;
                    default:
                        break;
                }
            }

        });
    }
}
