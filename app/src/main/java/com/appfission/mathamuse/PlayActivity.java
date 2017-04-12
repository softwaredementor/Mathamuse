package com.appfission.mathamuse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.appfission.utils.Constants;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */
//For categories screen
public class PlayActivity extends Activity {

    private Button arithmeticMenuButton;
    private Button logarithmMenuButton;
    private Button exponentMenuButton;
    private Button percentageMenubutton;
    private Button equationsMenuButton;
    private Button fractionsMenuButton;
    private Button multiplicationTablesbutton;
    private Button statisticsMenuButton;
    private Button trignometryMenuButton;
    private Button trueFalseMenuButton;

    private  Bundle paramBundle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //load settings and initialize bundle
        loadSettingsForGamePlay();

        arithmeticMenuButton = (Button) findViewById(R.id.arithmeticMenuButton);
        arithmeticMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_ARITHMETIC);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        logarithmMenuButton   = (Button) findViewById(R.id.logarithmMenuButton);
        logarithmMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_LOGARITHM);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        exponentMenuButton = (Button) findViewById(R.id.exponentMenuButton);
        exponentMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_EXPONENTS);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        percentageMenubutton = (Button) findViewById(R.id.percentageMenuButton);
        percentageMenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_PERCENTAGES);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        equationsMenuButton = (Button) findViewById(R.id.equationsMenuButton);
        equationsMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_EQUATIONS);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        fractionsMenuButton = (Button) findViewById(R.id.fractionsMenuButton);
        fractionsMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_FRACTIONS);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        multiplicationTablesbutton = (Button) findViewById(R.id.multiplicationTablesMenuButton);
        multiplicationTablesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_MULTIPLICATIONS);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        statisticsMenuButton = (Button) findViewById(R.id.statisticsMenuButton);
        statisticsMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_STATISTICS);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        trignometryMenuButton = (Button) findViewById(R.id.trignometryMenuButton);
        trignometryMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_TRIGONOMETRY);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        trueFalseMenuButton = (Button) findViewById(R.id.trueFalseMenuButton);
        trueFalseMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), GameInfoActivity.class);
                paramBundle.putString(Constants.CATEGORY_NAME, Constants.CATEGORY_TRUEORFALSE);
                startGameInfoActivity.putExtras(paramBundle);
                startActivity(startGameInfoActivity);
            }
        });

        //code for placing ads
    }

    private void loadSettingsForGamePlay() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        String difficultyLevelValue = sharedPreferences.getString(Constants.DIFFICULTY_LEVEL_KEY, Constants.EASY);
        String gameModeValue = sharedPreferences.getString(Constants.GAME_MODE_KEY, Constants.PRACTICE_MODE);
        String durationValue = sharedPreferences.getString(Constants.DURATION_KEY, Constants.MINUTE_1);
        boolean isSoundEnabled = sharedPreferences.getBoolean(Constants.SOUND_ENABLED_KEY, Constants.FALSE);
        boolean isVibrationEnabled = sharedPreferences.getBoolean(Constants.VIBRATION_ENABLED_KEY, Constants.FALSE);
        boolean isNotificationsEnabled = sharedPreferences.getBoolean(Constants.NOTIFICATIONS_ENABLED_KEY, Constants.FALSE);

        //Create a bundle
        paramBundle = new Bundle();
        paramBundle.putString(Constants.DIFFICULTY_LEVEL_KEY, difficultyLevelValue);
        paramBundle.putString(Constants.GAME_MODE_KEY, gameModeValue);
        paramBundle.putString(Constants.DURATION_KEY, durationValue);
        paramBundle.putBoolean(Constants.SOUND_ENABLED_KEY, isSoundEnabled);
        paramBundle.putBoolean(Constants.VIBRATION_ENABLED_KEY, isVibrationEnabled);
        paramBundle.putBoolean(Constants.NOTIFICATIONS_ENABLED_KEY, isNotificationsEnabled);
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
