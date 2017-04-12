package com.appfission.mathamuse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appfission.utils.Constants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public class HighScoresActivity extends Activity {

    private Button levelsInfoButton;

    private long arithmeticEasy ;
    private long arithmeticMedium ;
    private long arithmeticHard ;
    private long logarithmsEasy ;
    private long logarithmsMedium ;
    private long logarithmsHard ;
    private long fractionsEasy ;
    private long fractionsMedium ;
    private long fractionsHard ;
    private long percentagesEasy ;
    private long percentagesMedium ;
    private long percentagesHard ;
    private long equationsEasy ;
    private long equationsMedium ;
    private long equationsHard ;
    private long exponentsEasy ;
    private long exponentsMedium ;
    private long exponentsHard ;
    private long trigonometryEasy ;
    private long trigonometryMedium ;
    private long trigonometryHard ;
    private long statisticsEasy ;
    private long statisticsMedium ;
    private long statisticsHard ;
    private long trueOrFalseEasy ;
    private long trueOrFalseMedium ;
    private long trueOrFalseHard ;
    private long multiplicationsEasy;
    private long multiplicationsMedium;
    private long multiplicationsHard;

    private TextView arithmeticEasyScoreView;
    private TextView arithmeticMediumScoreView;
    private TextView arithmeticHardScoreView;
    private TextView logarithmsEasyScoreView;
    private TextView logarithmsMediumScoreView;
    private TextView logarithmsHardScoreView;
    private TextView fractionsEasyScoreView;
    private TextView fractionsMediumScoreView;
    private TextView fractionsHardScoreView;
    private TextView percentagesEasyScoreView;
    private TextView percentagesMediumScoreView;
    private TextView percentagesHardScoreView;
    private TextView equationsEasyScoreView;
    private TextView equationsMediumScoreView;
    private TextView equationsHardScoreView;
    private TextView exponentsEasyScoreView;
    private TextView exponentsMediumScoreView;
    private TextView exponentsHardScoreView;
    private TextView trigonometryEasyScoreView;
    private TextView trigonometryMediumScoreView;
    private TextView trigonometryHardScoreView;
    private TextView statisticsEasyScoreView;
    private TextView statisticsMediumScoreView;
    private TextView statisticsHardScoreView;
    private TextView trueOrFalseEasyScoreView;
    private TextView trueOrFalseMediumScoreView;
    private TextView trueOrFalseHardScoreView;
    private TextView multiplicationsEasyScoreView;
    private TextView multiplicationsMediumScoreView;
    private TextView multiplicationsHardScoreView;
    private InterstitialAd interstitialAd;
    private String TAG = HighScoresActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        //Read and get all the scores
        getAllCategoryScores();

        //Initialize the textview controls with correct read values
        setButtonControls();

        //Ad section
        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId(getResources().getString(R.string.testAd));
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_1));
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

    private void getAllCategoryScores() {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
            //read or set default score
            long defaultValue = 0;
            arithmeticEasy = sharedPreferences.getLong(Constants.CATEGORY_ARITHMETIC_EASY, defaultValue);
            arithmeticMedium = sharedPreferences.getLong(Constants.CATEGORY_ARITHMETIC_MEDIUM, defaultValue);
            arithmeticHard = sharedPreferences.getLong(Constants.CATEGORY_ARITHMETIC_HARD, defaultValue);
            logarithmsEasy = sharedPreferences.getLong(Constants.CATEGORY_LOGARITHM_EASY, defaultValue);
            logarithmsMedium = sharedPreferences.getLong(Constants.CATEGORY_LOGARITHM_MEDIUM, defaultValue);
            logarithmsHard = sharedPreferences.getLong(Constants.CATEGORY_LOGARITHM_HARD, defaultValue);
            fractionsEasy = sharedPreferences.getLong(Constants.CATEGORY_FRACTIONS_EASY, defaultValue);
            fractionsMedium = sharedPreferences.getLong(Constants.CATEGORY_FRACTIONS_MEDIUM, defaultValue);
            fractionsHard = sharedPreferences.getLong(Constants.CATEGORY_FRACTIONS_HARD, defaultValue);
            percentagesEasy = sharedPreferences.getLong(Constants.CATEGORY_PERCENTAGES_EASY, defaultValue);
            percentagesMedium = sharedPreferences.getLong(Constants.CATEGORY_PERCENTAGES_MEDIUM, defaultValue);
            percentagesHard = sharedPreferences.getLong(Constants.CATEGORY_PERCENTAGES_HARD, defaultValue);
            equationsEasy = sharedPreferences.getLong(Constants.CATEGORY_EQUATIONS_EASY, defaultValue);
            equationsMedium = sharedPreferences.getLong(Constants.CATEGORY_EQUATIONS_MEDIUM, defaultValue);
            equationsHard = sharedPreferences.getLong(Constants.CATEGORY_EQUATIONS_HARD, defaultValue);
            exponentsEasy = sharedPreferences.getLong(Constants.CATEGORY_EXPONENTS_EASY, defaultValue);
            exponentsMedium = sharedPreferences.getLong(Constants.CATEGORY_EXPONENTS_MEDIUM, defaultValue);
            exponentsHard = sharedPreferences.getLong(Constants.CATEGORY_EXPONENTS_HARD, defaultValue);
            trigonometryEasy = sharedPreferences.getLong(Constants.CATEGORY_TRIGONOMETRY_EASY, defaultValue);
            trigonometryMedium = sharedPreferences.getLong(Constants.CATEGORY_TRIGONOMETRY_MEDIUM, defaultValue);
            trigonometryHard = sharedPreferences.getLong(Constants.CATEGORY_TRIGONOMETRY_HARD, defaultValue);
            statisticsEasy = sharedPreferences.getLong(Constants.CATEGORY_STATISTICS_EASY, defaultValue);
            statisticsMedium = sharedPreferences.getLong(Constants.CATEGORY_STATISTICS_MEDIUM, defaultValue);
            statisticsHard = sharedPreferences.getLong(Constants.CATEGORY_STATISTICS_HARD, defaultValue);
            trueOrFalseEasy = sharedPreferences.getLong(Constants.CATEGORY_TRUEORFALSE_EASY, defaultValue);
            trueOrFalseMedium = sharedPreferences.getLong(Constants.CATEGORY_TRUEORFALSE_MEDIUM, defaultValue);
            trueOrFalseHard = sharedPreferences.getLong(Constants.CATEGORY_TRUEORFALSE_HARD, defaultValue);
            multiplicationsEasy = sharedPreferences.getLong(Constants.CATEGORY_MULTIPLICATIONS_EASY, defaultValue);
            multiplicationsMedium = sharedPreferences.getLong(Constants.CATEGORY_MULTIPLICATIONS_MEDIUM, defaultValue);
            multiplicationsHard = sharedPreferences.getLong(Constants.CATEGORY_MULTIPLICATIONS_HARD, defaultValue);
        }

    private void setButtonControls() {

        arithmeticEasyScoreView = (TextView) findViewById(R.id.arithmeticEasyScore);
        arithmeticEasyScoreView.setText(String.valueOf(arithmeticEasy));
        arithmeticMediumScoreView = (TextView) findViewById(R.id.arithmeticMediumScore);
        arithmeticMediumScoreView.setText(String.valueOf(arithmeticMedium));
        arithmeticHardScoreView = (TextView) findViewById(R.id.arithmeticHardScore);
        arithmeticHardScoreView.setText(String.valueOf(arithmeticHard));

        logarithmsEasyScoreView = (TextView) findViewById(R.id.logarithmsEasyScore);
        logarithmsEasyScoreView.setText(String.valueOf(logarithmsEasy));
        logarithmsMediumScoreView = (TextView) findViewById(R.id.logarithmsMediumScore);
        logarithmsMediumScoreView.setText(String.valueOf(logarithmsMedium));
        logarithmsHardScoreView = (TextView) findViewById(R.id.logarithmsHardScore);
        logarithmsHardScoreView.setText(String.valueOf(logarithmsHard));

        fractionsEasyScoreView = (TextView) findViewById(R.id.fractionsEasyScore);
        fractionsEasyScoreView.setText(String.valueOf(fractionsEasy));
        fractionsMediumScoreView = (TextView) findViewById(R.id.fractionsMediumScore);
        fractionsMediumScoreView.setText(String.valueOf(fractionsMedium));
        fractionsHardScoreView = (TextView) findViewById(R.id.fractionsHardScore);
        fractionsHardScoreView.setText(String.valueOf(fractionsHard));

        percentagesEasyScoreView = (TextView) findViewById(R.id.percentagesEasyScore);
        percentagesEasyScoreView.setText(String.valueOf(percentagesEasy));
        percentagesMediumScoreView = (TextView) findViewById(R.id.percentagesMediumScore);
        percentagesMediumScoreView.setText(String.valueOf(percentagesMedium));
        percentagesHardScoreView = (TextView) findViewById(R.id.percentagesHardScore);
        percentagesHardScoreView.setText(String.valueOf(percentagesHard));

        equationsEasyScoreView = (TextView) findViewById(R.id.equationsEasyScore);
        equationsEasyScoreView.setText(String.valueOf(equationsEasy));
        equationsMediumScoreView = (TextView) findViewById(R.id.equationsMediumScore);
        equationsMediumScoreView.setText(String.valueOf(equationsMedium));
        equationsHardScoreView = (TextView) findViewById(R.id.equationsHardScore);
        equationsHardScoreView.setText(String.valueOf(equationsHard));

        exponentsEasyScoreView = (TextView) findViewById(R.id.exponentsEasyScore);
        exponentsEasyScoreView.setText(String.valueOf(exponentsEasy));
        exponentsMediumScoreView = (TextView) findViewById(R.id.exponentsMediumScore);
        exponentsMediumScoreView.setText(String.valueOf(exponentsMedium));
        exponentsHardScoreView = (TextView) findViewById(R.id.exponentsHardScore);
        exponentsHardScoreView.setText(String.valueOf(exponentsHard));

        trigonometryEasyScoreView = (TextView) findViewById(R.id.trigonometryEasyScore);
        trigonometryEasyScoreView.setText(String.valueOf(trigonometryEasy));
        trigonometryMediumScoreView = (TextView) findViewById(R.id.trigonometryMediumScore);
        trigonometryMediumScoreView.setText(String.valueOf(trigonometryMedium));
        trigonometryHardScoreView = (TextView) findViewById(R.id.trigonometryHardScore);
        trigonometryHardScoreView.setText(String.valueOf(trigonometryHard));

        statisticsEasyScoreView = (TextView) findViewById(R.id.statisticsEasyScore);
        statisticsEasyScoreView.setText(String.valueOf(statisticsEasy));
        statisticsMediumScoreView = (TextView) findViewById(R.id.statisticsMediumScore);
        statisticsMediumScoreView.setText(String.valueOf(statisticsMedium));
        statisticsHardScoreView = (TextView) findViewById(R.id.statisticsHardScore);
        statisticsHardScoreView.setText(String.valueOf(statisticsHard));

        trueOrFalseEasyScoreView = (TextView) findViewById(R.id.trueOrFalseEasyScore);
        trueOrFalseEasyScoreView.setText(String.valueOf(trueOrFalseEasy));
        trueOrFalseMediumScoreView = (TextView) findViewById(R.id.trueOrFalseMediumScore);
        trueOrFalseMediumScoreView.setText(String.valueOf(trueOrFalseMedium));
        trueOrFalseHardScoreView = (TextView) findViewById(R.id.trueOrFalseHardScore);
        trueOrFalseHardScoreView.setText(String.valueOf(trueOrFalseHard));

        multiplicationsEasyScoreView = (TextView) findViewById(R.id.multiplicationsEasyScore);
        multiplicationsEasyScoreView.setText(String.valueOf(multiplicationsEasy));
        multiplicationsMediumScoreView = (TextView) findViewById(R.id.multiplicationsMediumScore);
        multiplicationsMediumScoreView.setText(String.valueOf(multiplicationsMedium));
        multiplicationsHardScoreView = (TextView) findViewById(R.id.multiplicationsHardScore);
        multiplicationsHardScoreView.setText(String.valueOf(multiplicationsHard));

        levelsInfoButton = (Button) findViewById(R.id.levelsInfoReport);
        levelsInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startLevelsInfoActivity = new Intent(getApplicationContext(), LevelInfoActivity.class);
                startActivity(startLevelsInfoActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.action_share) {
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
    }
}
