package com.appfission.mathamuse;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.appfission.utils.Constants;

import java.util.Calendar;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public class SettingsActivity extends Activity {

    private Spinner difficultySpinner;
    private RadioGroup gameModeRadioGroup;
    private RadioButton gameModeRadioButton;
    private Spinner durationSpinner;
    private Switch soundSwitch;
    private Switch vibrationSwitch;
    private Switch notificationsSwitch;

    private String difficultyLevelValue;
    private String gameModeValue;
    private String durationValue;
    private boolean isSoundEnabled = true;
    private boolean isVibrationEnabled = true;
    private boolean isNotificationsEnabled = true;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);

        //read preferences
        String defaultDifficultyValue = Constants.EASY;
        difficultyLevelValue = sharedPreferences.getString(Constants.DIFFICULTY_LEVEL_KEY, defaultDifficultyValue);
        String gameMode = Constants.TIMED_MODE;
        gameModeValue = sharedPreferences.getString(Constants.GAME_MODE_KEY, gameMode);
        String duration = Constants.MINUTE_1;
        durationValue = sharedPreferences.getString(Constants.DURATION_KEY, duration);
        boolean soundEnabled = Constants.TRUE;
        isSoundEnabled = sharedPreferences.getBoolean(Constants.SOUND_ENABLED_KEY, soundEnabled);
        boolean vibrationEnabled = Constants.TRUE;
        isVibrationEnabled = sharedPreferences.getBoolean(Constants.VIBRATION_ENABLED_KEY, vibrationEnabled);
        boolean notificationsEnabled = Constants.TRUE;
        isNotificationsEnabled = sharedPreferences.getBoolean(Constants.NOTIFICATIONS_ENABLED_KEY, notificationsEnabled);

        //initialize controls
        initializeControls();

        //code for placing ads
    }

    private void initializeControls() {

        difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);
        ArrayAdapter spinnerArrayAdapter = (ArrayAdapter) difficultySpinner.getAdapter();
        int difficultySpinnerPosition = spinnerArrayAdapter.getPosition(difficultyLevelValue);
        difficultySpinner.setSelection(difficultySpinnerPosition);

        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                difficultyLevelValue = difficultySpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "Level set to : " + difficultyLevelValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gameModeRadioGroup = (RadioGroup) findViewById(R.id.gameModeRadioGroup);
        if (gameModeValue.equals(Constants.PRACTICE_MODE)) {
            gameModeRadioGroup.check(R.id.practiceMode);
        } else {
            gameModeRadioGroup.check(R.id.timedMode);
        }
        gameModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                gameModeRadioButton = (RadioButton) findViewById(i);
                gameModeValue = gameModeRadioButton.getText().toString();
                Toast.makeText(getBaseContext(), gameModeValue + " mode selected", Toast.LENGTH_SHORT).show();
                if (gameModeRadioButton.getText().equals("Timed")) {
                    durationSpinner.setEnabled(true);
                } else {
                    durationSpinner.setEnabled(false);
                }
            }
        });

        //set the duration spinner value
        durationSpinner = (Spinner) findViewById(R.id.durationSpinner);
        ArrayAdapter durationSpinnerArrayAdapter = (ArrayAdapter) durationSpinner.getAdapter();
        int durationSpinnerPosition = durationSpinnerArrayAdapter.getPosition(durationValue);
        durationSpinner.setSelection(durationSpinnerPosition);

        //enable or disable duration spinner as per game mode
        if(gameModeValue.equals(Constants.TIMED_MODE)) {
            durationSpinner.setEnabled(true);
        } else {
            durationSpinner.setEnabled(false);
        }

        //change duration spinner value on event handling
        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                durationValue = durationSpinner.getSelectedItem().toString();
                if(gameModeValue.equals(Constants.TIMED_MODE)) {
                    Toast.makeText(getApplicationContext(), "Play duration set to " + durationValue, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        soundSwitch.setChecked(isSoundEnabled);
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isSoundEnabled = b;
                if(isSoundEnabled) {
                    Toast.makeText(getApplicationContext(), "Sound is enabled" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Sound is disabled" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        vibrationSwitch = (Switch) findViewById(R.id.vibrationSwitch);
        vibrationSwitch.setChecked(isVibrationEnabled);
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isVibrationEnabled = b;
                if(isVibrationEnabled) {
                    Toast.makeText(getApplicationContext(), "Vibration is enabled" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Vibration is disabled" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        notificationsSwitch = (Switch) findViewById(R.id.notificationsSwitch);
        notificationsSwitch.setChecked(isNotificationsEnabled);
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isNotificationsEnabled = b;
                if(isNotificationsEnabled) {
                    Toast.makeText(getApplicationContext(), "Notifications are enabled" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Notifications are disabled" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void saveSettingsData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.DIFFICULTY_LEVEL_KEY, difficultyLevelValue);
        editor.putString(Constants.GAME_MODE_KEY, gameModeValue);
        editor.putString(Constants.DURATION_KEY, durationValue);
        editor.putBoolean(Constants.SOUND_ENABLED_KEY, isSoundEnabled);
        editor.putBoolean(Constants.VIBRATION_ENABLED_KEY, isVibrationEnabled);
        editor.putBoolean(Constants.NOTIFICATIONS_ENABLED_KEY, isNotificationsEnabled);
        if (isNotificationsEnabled) {
            initializeNotifications();
        } else {
            cancelNotifications();
        }
        editor.commit();
    }

    private void initializeNotifications() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 06);
        calendar.set(Calendar.MINUTE, 00);

        Intent notificationsIntent = new Intent(getApplicationContext(), Notifications_receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelNotifications() {

        Intent notificationsIntent = new Intent(getApplicationContext(), Notifications_receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
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
    protected void onPause() {
        saveSettingsData();
        super.onPause();
    }

}
