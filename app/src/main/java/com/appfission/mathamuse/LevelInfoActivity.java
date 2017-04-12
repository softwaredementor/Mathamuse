package com.appfission.mathamuse;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfission.utils.Constants;

/**
 * Created by srikanthmannepalle on 3/8/17.
 */

public class LevelInfoActivity extends BaseScoreActivity {

    private TextView totalGameScoreView;
    private TextView totalStarsCountView;
    private SharedPreferences sharedPreferences;
    private ImageView imageNoobView;
    private ImageView imageRookieView;
    private ImageView imageSemiProView;
    private ImageView imageProView;
    private ImageView imageVeterenView;
    private ImageView imageExpertView;
    private ImageView imageMasterView;
    private ImageView imageLegendView;
    private ImageView imageUnstoppableView;
    private ImageView imageGodlikeView;
    private ImageView imageRebelView;
    private ImageView imageTalentedView;
    private ImageView imageJugglerView;
    private ImageView imageMagicianView;

    private String TAG = LevelInfoActivity.class.getName();

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        //retrieve the score and set the gamescore and the stars count
        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        long totalScoreValue = sharedPreferences.getLong(Constants.TOTAL_GAME_SCORE, 0);
        long totalStarsCountValue = sharedPreferences.getLong(Constants.TOTAL_STARS_COUNT, 0);

        totalGameScoreView = (TextView) findViewById(R.id.totalGameScore);
        totalGameScoreView.setText(String.valueOf(totalScoreValue));
        totalStarsCountView = (TextView) findViewById(R.id.totalStarsCount);
        totalStarsCountView.setText(String.valueOf(totalStarsCountValue));

        //code for placing ads
    }

    @Override
    protected void onStart() {
        highlightTheCorrectLevel();
        super.onStart();
    }

    private void highlightTheCorrectLevel() {
        long totalScore = sharedPreferences.getLong(Constants.TOTAL_GAME_SCORE, 0);
        long totalStars = sharedPreferences.getLong(Constants.TOTAL_STARS_COUNT, 0);
        Log.d(getClass().getName(), "Totalscore = " + totalScore + " and total Stars = " + totalStars);
        int currentLevelValue = calculateCurrentLevel(totalScore, totalStars);
        Log.d(getClass().getName(), "Outside switch : currentLevelValue = " + currentLevelValue);

        switch (currentLevelValue) {
            case 0 : imageNoobView = (ImageView) findViewById(R.id.imageNoob);
                imageNoobView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 1 : imageRookieView = (ImageView) findViewById(R.id.imageRookie);
                imageRookieView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 2 : imageSemiProView = (ImageView) findViewById(R.id.imageSemiPro);
                imageSemiProView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 3 : imageProView = (ImageView) findViewById(R.id.imagePro);
                imageProView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 4 : imageJugglerView = (ImageView) findViewById(R.id.imageJuggler);
                imageJugglerView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 5 : imageRebelView = (ImageView) findViewById(R.id.imageRebel);
                imageRebelView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 6 : imageTalentedView = (ImageView) findViewById(R.id.imageTalented);
                imageTalentedView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 7 : imageVeterenView = (ImageView) findViewById(R.id.imageVeteren);
                imageVeterenView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 8 : imageMasterView = (ImageView) findViewById(R.id.imageMaster);
                imageMasterView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 9 : imageMagicianView = (ImageView) findViewById(R.id.imageMagician);
                imageMagicianView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 10 : imageExpertView = (ImageView) findViewById(R.id.imageExpert);
                imageExpertView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 11 : imageLegendView = (ImageView) findViewById(R.id.imageLegend);
                imageLegendView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 12 : imageUnstoppableView = (ImageView) findViewById(R.id.imageUnstoppable);
                imageUnstoppableView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
            case 13 : imageGodlikeView = (ImageView) findViewById(R.id.imageGodlike);
                imageGodlikeView.setBackgroundResource(R.drawable.image_border);
                Log.d(getClass().getName(), "currentLevelValue = " + currentLevelValue);
                break;
        }
    }
}
