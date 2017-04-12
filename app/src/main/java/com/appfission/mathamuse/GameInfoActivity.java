package com.appfission.mathamuse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfission.utils.Constants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public class GameInfoActivity extends BaseScoreActivity {

    private Button readyGameInfoButton;
    private ImageView currentLevelImage;
    private ImageView nextLevelImage;
    private TextView currentLevelText;
    private TextView nextLevelText;
    private TextView gameInfoContent;
    private TextView starsNeededValue;
    private TextView pointsNeededValue;
    private InterstitialAd interstitialAd;

    private String categoryName;
    private static String TAG = GameInfoActivity.class.getName();

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameinfo);

        final Bundle passedParamBundle = getIntent().getExtras();
        categoryName = passedParamBundle.getString(Constants.CATEGORY_NAME);

        //set the game info content textview
        gameInfoContent = (TextView) findViewById(R.id.gameInfoContent);
        gameInfoContent.setMovementMethod(new ScrollingMovementMethod());
        readyGameInfoButton = (Button) findViewById(R.id.readyGameInfoButton);
        readyGameInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startInputActivity = new Intent(getApplicationContext(), InputActivity.class);
                startInputActivity.putExtras(passedParamBundle);
                startActivity(startInputActivity);
            }
        });

        sharedPreferences =  getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //code for placing ads
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
    protected void onStart() {
        if (interstitialAd.isLoaded()) {
            Log.d(TAG, "interstitial ad loaded");
            interstitialAd.show();
        } else {
            Log.d(TAG, "Interstitial ad was not loaded properly");
        }
        //set the content according to the screen.
        setGameInfoContent();
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

    private void setGameInfoContent() {
        if (categoryName.equals(Constants.CATEGORY_ARITHMETIC)) {
            gameInfoContent.setText(R.string.arithmeticGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_LOGARITHM)) {
            gameInfoContent.setText(R.string.logarithmGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_FRACTIONS)) {
            gameInfoContent.setText(R.string.fractionsGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_PERCENTAGES)) {
            gameInfoContent.setText(R.string.percentagesGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_EQUATIONS)) {
            gameInfoContent.setText(R.string.equationsGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_EXPONENTS)) {
            gameInfoContent.setText(R.string.exponentsGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_TRIGONOMETRY)) {
            gameInfoContent.setText(R.string.trigonometryGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_STATISTICS)) {
            gameInfoContent.setText(R.string.statisticsGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_TRUEORFALSE)) {
            gameInfoContent.setText(R.string.trueFalseGameInfo);
        } else if (categoryName.equals(Constants.CATEGORY_MULTIPLICATIONS)) {
            gameInfoContent.setText(R.string.multiplicationTablesGameInfo);
        }

        setImagesAndPoints();
    }

    //get the current level, show the next level
    //calculate the score needed and stars need for next level
    //handle boundary cases : first time initialization, current level = godlike, current level = master
    private void setImagesAndPoints() {

        long totalScore = sharedPreferences.getLong(Constants.TOTAL_GAME_SCORE, 0);
        long totalStars = sharedPreferences.getLong(Constants.TOTAL_STARS_COUNT, 0);
        String currentLevelValue = calculateCurrentLevelName(totalScore, totalStars);
        String nextLevelValue = calculateNextLevelName(totalScore, totalStars);

        //get current level image placeholder and set its URI
        currentLevelText = (TextView) findViewById(R.id.currenLevelText);
        currentLevelText.setText(currentLevelValue);
        currentLevelImage = (ImageView) findViewById(R.id.currenLevelImage);
        currentLevelImage.setImageResource(getResources().getIdentifier(currentLevelValue, "drawable", getPackageName()));

        //get next level image placeholder and set its URI
        nextLevelText = (TextView) findViewById(R.id.nextLevelText);
        nextLevelText.setText(nextLevelValue);
        nextLevelImage = (ImageView) findViewById(R.id.nextLevelImage);
        if (null!= nextLevelValue && !nextLevelValue.equals("")) {
            nextLevelImage.setImageResource(getResources().getIdentifier(nextLevelValue, "drawable", getPackageName()));
        } else {
            nextLevelImage.setImageResource(getResources().getIdentifier("blankimage", "drawable", getPackageName()));
        }

        String[] pointsAndStarsValues = calculateRequiredScoreAndStars(totalScore, totalStars);
        long requiredPoints, requiredStars;

        if (null != pointsAndStarsValues[0] && pointsAndStarsValues[0].equals("")) {
            Log.d(TAG, "Godlevel reached");
            requiredPoints = 0l;
        } else {
            requiredPoints = Long.parseLong(pointsAndStarsValues[0]);
        }
        if (null != pointsAndStarsValues[1] && pointsAndStarsValues[1].equals("")) {
            Log.d(TAG, "Godlevel reached");
            requiredStars = 0l;
        } else {
            requiredStars = Long.parseLong(pointsAndStarsValues[1]);
        }

        pointsNeededValue = (TextView) findViewById(R.id.pointsNeededValue);
        if (requiredPoints > 0) {
            pointsNeededValue.setText(pointsAndStarsValues[0]);
        } else {
            pointsNeededValue.setText("Achieved");
        }

        starsNeededValue = (TextView) findViewById(R.id.starsNeededValue);
        if (requiredStars > 0) {
            starsNeededValue.setText(pointsAndStarsValues[1]);
        } else {
            starsNeededValue.setText("Achieved");
        }
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
}
