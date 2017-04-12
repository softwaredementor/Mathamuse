package com.appfission.mathamuse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appfission.helper.AccomplishmentsOutbox;
import com.google.android.gms.games.Games;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.appfission.helper.AccomplishmentsOutbox.mGoogleApiClient;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public class ScoreActivity extends AppCompatActivity {

    private static String TAG = ScoreActivity.class.getName();
    private static File file;

    private Bundle extras;

    private Button summaryButton;

    private TextView gameName;
    private TextView difficultyLevel;
    private TextView totalQuestionsTextView;
    private TextView totalCorrectAnswers;
    private TextView totalIncorrectAnswers;
    private TextView maxStreakCountView;
    private TextView totalScore;
    private RatingBar totalStars;

    private int totalQuestionsValue;
    private int totalCorrectAnswersValue;
    private int totalIncorrectAnswersValue;
    private int maxStreakCount;
    private long totalScoreValue;
    private int totalStarsValue;
    private View rootView;
    private Bitmap bitmapImage;
    private ShareActionProvider mShareActionProvider;
    AccomplishmentsOutbox mOutbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mOutbox = new AccomplishmentsOutbox(AccomplishmentsOutbox.context, mGoogleApiClient);

        //initialize controls
        initializeControls();

        //code for placing ads

        //check for the achievements
        if(mGoogleApiClient.isConnected()) {
            Log.d(TAG, "Google api client is connected. Can show pop-ups");
            Games.setViewForPopups(mGoogleApiClient, getWindow().getDecorView().findViewById(android.R.id.content));
        }
    }

    private void initializeControls() {
        gameName = (TextView) findViewById(R.id.gameName);
        difficultyLevel = (TextView) findViewById(R.id.gameDifficultyLevel);
        totalQuestionsTextView = (TextView) findViewById(R.id.totalQuestionsValue);
        totalCorrectAnswers = (TextView) findViewById(R.id.correctAnswersValue);
        totalIncorrectAnswers = (TextView) findViewById(R.id.incorrectAnswersValue);
        maxStreakCountView = (TextView) findViewById(R.id.maxStreakCountValue);
        totalScore = (TextView) findViewById(R.id.totalScoreValue);
        totalStars = (RatingBar) findViewById(R.id.rating);
        summaryButton = (Button) findViewById(R.id.summaryReport);

        //Get the passed params
        extras = getIntent().getExtras();
        if (null != extras) {
            gameName.setText(extras.getString("CategoryName"));
            difficultyLevel.setText(extras.getString("DifficultyLevel"));
            totalQuestionsTextView.setText(extras.getString("TotalQuestions"));
            totalCorrectAnswers.setText(extras.getString("TotalCorrectAnswers"));
            totalIncorrectAnswers .setText(extras.getString("TotalIncorrectAnswers"));
            maxStreakCountView.setText(extras.getString("MaxStreakCount"));
            totalScoreValue = Long.parseLong(extras.getString("TotalScore"));
            totalStarsValue = Integer.parseInt(extras.getString("TotalStars"));
            totalScore.setText(extras.getString("TotalScore"));
            totalStars.setRating(Float.parseFloat(extras.getString("TotalStars")));
            Log.d(TAG, "Total score = " + totalScoreValue);
            Log.d(TAG, "Total stars = " + totalStarsValue);
        }

        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startGameInfoActivity = new Intent(getApplicationContext(), SummaryActivity.class);
                startGameInfoActivity.putExtras(extras);
                startActivity(startGameInfoActivity);
            }
        });

        // check for achievements
        checkForAchievements();

        // push those accomplishments to the cloud, if signed in
        mOutbox.pushAccomplishments();
    }

    private void setRootView() {
        //initialize the root view for capturing the image
        //First, get root view from current activity:
        rootView = getWindow().getDecorView();
    }

    //Second, capture the root view:
    public static Bitmap getScreenShot(View view) {

        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    //Third, store the Bitmap into the SDCard:
    public static void storeImage(Bitmap bm, String fileName){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        Log.d(TAG, "Directory path for storing the image = " + dirPath);
        file = new File(dirPath, fileName);
        try {
            Log.d(TAG, "File path for storing the image = " + file);
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception occurred " + e.getMessage());
        }
    }

    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My " + gameName + " score card :-)");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey! Check out my new achievement in Mathamuse game. Here is the link to install it : https://play.google.com/store/apps/details?id=com.appfission.mathamuse");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            Log.d(TAG, "About to share the image!");
            startActivity(Intent.createChooser(intent, "Share Screenshot via"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check for achievements and unlock the appropriate ones.
     *
     */
    void checkForAchievements() {
        // Check if each condition is met; if so, unlock the corresponding
        // achievement.
        if (((totalScoreValue >=0 && totalScoreValue <=2499) || totalScoreValue > 2499) && ((totalStarsValue >0 && totalStarsValue <=75) || totalStarsValue >75)) {
            mOutbox.mNoobAchievement = true;
            unsignedUserAchievementLoggings("Noob");
        }
        if(((totalScoreValue >=2500 && totalScoreValue <=4999) || totalScoreValue > 4999) && ((totalScoreValue >75 && totalScoreValue <=150) || totalScoreValue >150)) {                   mOutbox.mRookieAchievement = true;
            unsignedUserAchievementLoggings("Rookie");
        }
        if(((totalScoreValue >=5000 && totalScoreValue <=7999) || totalScoreValue > 7999) && ((totalScoreValue >150 && totalScoreValue <=225) || totalScoreValue >225)) {            mOutbox.mSemiproAchievement= true;
            unsignedUserAchievementLoggings("Semipro");
        }
        if(((totalScoreValue >=8000 && totalScoreValue <=10999) || totalScoreValue > 10999) && ((totalScoreValue >225 && totalScoreValue <=350) || totalScoreValue >350)) {
            mOutbox.mProAchievement = true;
            unsignedUserAchievementLoggings("Pro");
        }
        if(((totalScoreValue >=11000 && totalScoreValue <=14999) || totalScoreValue > 14999) && ((totalScoreValue >350 && totalScoreValue <=450) || totalScoreValue >450)) {
            mOutbox.mJugglerAchievement = true;
            unsignedUserAchievementLoggings("Juggler");
        }
        if(((totalScoreValue >=15000 && totalScoreValue <=18999) || totalScoreValue > 18999) && ((totalScoreValue >450 && totalScoreValue <=550) || totalScoreValue >550)) {
            mOutbox.mRebelAchievement = true;
            unsignedUserAchievementLoggings("Rebel");
        }
        if(((totalScoreValue >=19000 && totalScoreValue <=22999) || totalScoreValue > 22999) && ((totalScoreValue >550 && totalScoreValue <=650) || totalScoreValue >650)) {
            mOutbox.mTalentedAchievement = true;
            unsignedUserAchievementLoggings("Talented");
        }
        if(((totalScoreValue >=23000 && totalScoreValue <=26999) || totalScoreValue > 26999) && ((totalScoreValue >650 && totalScoreValue <=750) || totalScoreValue >750)) {
            mOutbox.mVeterenAchievement = true;
            unsignedUserAchievementLoggings("Veteren");
        }
        if(((totalScoreValue >=27000 && totalScoreValue <=31999) || totalScoreValue > 31999) && ((totalScoreValue >750 && totalScoreValue <=850) || totalScoreValue >850)) {
            mOutbox.mMasterAchievement = true;
            unsignedUserAchievementLoggings("Master");
        }
        if(((totalScoreValue >=32000 && totalScoreValue <=37999) || totalScoreValue > 37999) && ((totalScoreValue >850 && totalScoreValue <=1000) || totalScoreValue >1000)) {
            mOutbox.mMagicianAchievement = true;
            unsignedUserAchievementLoggings("Magician");
        }
        if(((totalScoreValue >=38000 && totalScoreValue <=43999) || totalScoreValue > 43999) && ((totalScoreValue >1000 && totalScoreValue <=1200) || totalScoreValue >1200)) {
            mOutbox.mExpertAchievement = true;
            unsignedUserAchievementLoggings("Expert");
        }
        if(((totalScoreValue >=44000 && totalScoreValue <=49999) || totalScoreValue > 49999) && ((totalScoreValue >1200 && totalScoreValue <=1400) || totalScoreValue >1400)) {
            mOutbox.mLegendAchievement = true;
            unsignedUserAchievementLoggings("Legend");
        }
        if(((totalScoreValue >=50000 && totalScoreValue <=57999) || totalScoreValue > 57999) && ((totalScoreValue >1400 && totalScoreValue <=1650) || totalScoreValue >1650)) {
            mOutbox.mUnstoppableAchievement = true;
            unsignedUserAchievementLoggings("Unstoppable");
        }
        if(((totalScoreValue >=58000 && totalScoreValue <=64999) || totalScoreValue > 64999) && ((totalScoreValue >1650 && totalScoreValue <=2000) || totalScoreValue >2000)) {
            mOutbox.mGodlikeAchievement = true;
            unsignedUserAchievementLoggings("Godlike");
        }
    }


    void unsignedUserAchievementLoggings(String achievement) {
        // Only show toast if not signed in. If signed in, the standard Google Play
        // toasts will appear, so we don't need to show our own.
        Log.d(TAG, "Verifying achievements in case user is not signed in!");
        if (!mOutbox.isSignedIn()) {
            Log.d(TAG, getString(R.string.achievement) + ": " + achievement + " unlocked");
        } else {
            Log.d(TAG, "User is signed in, Toast not required!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

        //THIS CODE IS NOT WORKING. MAKE IT WORK IN PHASE 2
//        // Inflate menu resource file.
//        getMenuInflater().inflate(R.menu.menu2, menu);
//
//        // Locate MenuItem with ShareActionProvider
//        MenuItem item = menu.findItem(R.id.action_share_score);
//
//        // Fetch and store ShareActionProvider
//        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//        return super.onCreateOptionsMenu(menu);
//        // Return true to display menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.action_share) {
            //get root view
            setRootView();
            bitmapImage = getScreenShot(rootView);
            storeImage(bitmapImage, gameName + "_scorecard");
            try {
                Log.d(TAG, "File path = " + file.getCanonicalPath());
            } catch (IOException e) {
                Log.d(TAG, "Exception occurred in getting filepath name");
            }
            shareImage(file);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
