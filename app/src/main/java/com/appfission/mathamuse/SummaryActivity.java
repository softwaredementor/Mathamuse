package com.appfission.mathamuse;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.appfission.helper.Result;
import com.appfission.helper.ResultsAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

/**
 * Created by srikanthmannepalle on 3/4/17.
 */

public class SummaryActivity extends Activity {

    private ListView mListView;
    private ResultsAdapter mResultsAdapter;
    private ArrayList<Result> passedResults;
    private Result[] mResultsData;
    private String TAG = SummaryActivity.class.getName();
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_view);
        Bundle passedParam = getIntent().getExtras();
        Log.d(TAG, "Item list size = " + passedParam.size());
        passedResults = passedParam.getParcelableArrayList("SummaryResults");
        Log.d(TAG, "Item Results size = " + passedResults.size());

        // The default array size returned was 9. Below is the most important step to size
        // the created array from list properly. Else nullpointer excepion comes.
        mResultsData = passedResults.toArray(new Result[passedResults.size()]);
        Log.d(TAG, "Item transformed Array size = " + mResultsData.length);

        this.mListView = (ListView) findViewById(R.id.myListView);
        this.mResultsAdapter = new ResultsAdapter(getApplicationContext(), R.layout.summary_entries, mResultsData);

        if (null != mListView) {
            mListView.setAdapter(mResultsAdapter);

        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("PLACE", mResultsData[position].getmQuestionNumber());
            }
        });

        //Ad section
        interstitialAd = new InterstitialAd(this);
        //Test ad
//        interstitialAd.setAdUnitId(getResources().getString(R.string.testAd));
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_2));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        if (interstitialAd.isLoaded()) {
            Log.d(TAG, "interstitial ad loaded");
            interstitialAd.show();
        } else {
            Log.d(TAG, "Interstitial ad was not loaded properly");
        }
        super.onStart();
    }

    private void requestNewInterstitial() {
        Log.d(TAG, "Device id = " + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID))
                .build();

        interstitialAd.loadAd(adRequest);
        if (interstitialAd.isLoaded()) {
            Log.d(TAG, "interstitial ad loaded");
            interstitialAd.show();
        } else {
            Log.d(TAG, "Interstitial ad was not loaded properly");
        }
    }
}
