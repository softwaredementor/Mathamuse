package com.appfission.mathamuse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.appfission.helper.AccomplishmentsOutbox;
import com.appfission.utils.Constants;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.BaseGameUtils;

public class MainActivity extends AppCompatActivity
        implements MainMenuFragment.Listener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String NUMBER_OF_TIMES_RUN_KEY = "NUMBER_OF_TIMES_RUN_KEY";

    private Button settingsButton= null;
    private Button playButton = null;
    private Button exitButton = null;
    private Button highScoresButton = null;
    private ShareActionProvider mShareActionProvider;
    private int numberOfAppInstanceRuns = 0;
    private AccomplishmentsOutbox mOutbox;
    private MainMenuFragment mMainMenuFragment;
    private boolean isGamePlayInvoked;
    private long totalGameScore;
    private long totalStarScore;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // Client used to interact with Google APIs
    public static GoogleApiClient mGoogleApiClient;

    // Are we currently resolving a connection failure?
    private boolean mResolvingConnectionFailure = false;

    // Has the user clicked the sign-in button?
    private boolean mSignInClicked = false;

    // Automatically start the sign-in flow when the Activity starts
    private boolean mAutoStartSignInFlow = true;

    // request codes we use when invoking an external activity
    private static final int RC_RESOLVE = 5000;
    private static final int RC_UNUSED = 5001;
    private static final int RC_SIGN_IN = 9001;

    // tag for debug logging
    final boolean ENABLE_DEBUG = true;
    final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //code for placing ads
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.appId));

        //read preferences
        //initialize the sharedpreference and the editor
        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        int defaultValue = 0;
        numberOfAppInstanceRuns = sharedPreferences.getInt(NUMBER_OF_TIMES_RUN_KEY, defaultValue);

        if(numberOfAppInstanceRuns == 0) {
            //initialize total score and total stars to 0
            editor.putLong(Constants.TOTAL_GAME_SCORE, 0);
            editor.putLong(Constants.TOTAL_STARS_COUNT, 0);
            editor.putLong(Constants.CURRENT_LEVEL, 0);
            Toast.makeText(getApplicationContext(), "Welcome user", Toast.LENGTH_SHORT).show();
        }
        numberOfAppInstanceRuns++;

        //write preferences
        editor.putInt(NUMBER_OF_TIMES_RUN_KEY, numberOfAppInstanceRuns);
        editor.commit();

        // Create the Google API Client with access to Games
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        // create fragments
        mMainMenuFragment = new MainMenuFragment();

        // listen to fragment events
        mMainMenuFragment.setListener(this);

        // add initial fragment (welcome fragment)
        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container, mMainMenuFragment)
                .commit();

        // achievements and scores we're pending to push to the cloud
        // (waiting for the user to sign in, for instance)
        mOutbox = new AccomplishmentsOutbox(getApplicationContext(), mGoogleApiClient);
        //update score card and star count
        loadAndUpdateScoreOfOverallBoard();
        loadAndUpdateStarLeaderboard();

        // load outbox from file
        mOutbox.loadLocal(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu1, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // change color for settings icon
        Drawable settingsIcon = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        settingsIcon.mutate();
        settingsIcon.setColorFilter(getResources().getColor(R.color.color_slight_grey), PorterDuff.Mode.SRC_IN);

        // change color for sharing icon
        Drawable shareIcon = menu.getItem(1).getIcon(); // change 0 with 1,2 ...
        shareIcon.mutate();
        shareIcon.setColorFilter(getResources().getColor(R.color.color_slight_grey), PorterDuff.Mode.SRC_IN);


        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.action_share) {
            sendTextContent();
            return true;
        } if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(startSettingsActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    //create and share the text
    private void sendTextContent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! My math skills improved using Mathamuse app. Here is the link to install it : https://play.google.com/store/apps/details?id=com.appfission.mathamuse");
        //change the app link
        //alpha testing : https://play.google.com/apps/testing/com.appfission.mathamuse
        //actual rollout :  https://play.google.com/store/apps/details?id=com.appfission.mathamuse
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, "Share this via"));
        }
    }

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "From Mainscreen onStart(): connecting");
        mGoogleApiClient.connect();
        Log.d(TAG, "From Mainscreen onStart(): connected");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "From Mainscreen onStop(): disconnecting");
        /**
         * Not disconnecting it here
         if (mGoogleApiClient.isConnected()) {
         mGoogleApiClient.disconnect();
         }*/
    }

    @Override
    public void onStartGameRequested() {
        if (isSignedIn()) {
            mMainMenuFragment.setShowSignInButton(false);
        } else {
            mMainMenuFragment.setShowSignInButton(true);
        }
        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
        startActivity(intent);
    }

    @Override
    public void onHighScoresButtonClicked() {
        Log.d(TAG, "Starting high scores screen");
        Intent startHighScoresActivity = new Intent(getApplicationContext(), HighScoresActivity.class);
        startActivity(startHighScoresActivity);
    }

    @Override
    public void onShowAchievementsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.achievements_not_available)).show();
        }
    }

    @Override
    public void onShowLeaderboardsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient),
                    RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, getString(R.string.leaderboards_not_available)).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, R.string.signin_other_error);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected(): connected to Google APIs");
        // Show sign-out button on main menu
        mMainMenuFragment.setShowSignInButton(false);

        // Set the greeting appropriately on main menu
        Player p = Games.Players.getCurrentPlayer(mGoogleApiClient);
        String displayName;
        if (p == null) {
            Log.w(TAG, "mGamesClient.getCurrentPlayer() is NULL!");
            displayName = "???";
        } else {
            displayName = p.getDisplayName();
        }
        mMainMenuFragment.setGreeting("Hello, " + displayName);

        // if we have accomplishments to push, push them
        if (!mOutbox.isEmpty()) {
            Log.d(TAG, "Pushing locally saved acomplishments");
            mOutbox.pushAccomplishments();
            Toast.makeText(this, getString(R.string.your_progress_will_be_uploaded),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onShowRatingsRequested() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            Log.d(TAG, "Google play app not found!");
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended(): attempting to connect");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed(): attempting to resolve");
        if (mResolvingConnectionFailure) {
            Log.d(TAG, "onConnectionFailed(): already resolving");
            return;
        }

        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;
            if (!BaseGameUtils.resolveConnectionFailure(this, mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        // Sign-in failed, so show sign-in button on main menu
        mMainMenuFragment.setGreeting(getString(R.string.signed_out_greeting));
        mMainMenuFragment.setShowSignInButton(true);
    }

    @Override
    public void onSignInButtonClicked() {

        // start the sign-in flow
        mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    @Override
    public void onSignOutButtonClicked() {
        mSignInClicked = false;
        Games.signOut(mGoogleApiClient);
        if (mGoogleApiClient.isConnected()) {
            Log.d(TAG, "mGoogleApiClient is connected, disconnecting now..");
            mGoogleApiClient.disconnect();
        }

        mMainMenuFragment.setGreeting(getString(R.string.signed_out_greeting));
        mMainMenuFragment.setShowSignInButton(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void loadAndUpdateScoreOfOverallBoard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(mGoogleApiClient, getString(R.string.leaderboard_overall), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                if (isScoreResultValid(scoreResult)) {
                    // here you can get the score like this
                    totalGameScore = scoreResult.getScore().getRawScore();
                    Log.d(TAG, "Retrieved score for overall leaderboard = " + totalGameScore);
                    //Update the overall score and star count.
                    // This will be used by the game info screen and the levels activity screen
                    editor.putLong(Constants.TOTAL_GAME_SCORE, totalGameScore);
                    editor.commit();
                }
            }
        });
    }

    private void loadAndUpdateStarLeaderboard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(mGoogleApiClient, getString(R.string.leaderboard_star_achievers), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                if (isScoreResultValid(scoreResult)) {
                    // here you can get the score like this
                    totalStarScore = scoreResult.getScore().getRawScore();
                    Log.d(TAG, "Retrieved score for star leaderboard = " + totalStarScore);
                    editor.putLong(Constants.TOTAL_STARS_COUNT, totalStarScore);
                    editor.commit();
                }
            }
        });
    }

    private boolean isScoreResultValid(final Leaderboards.LoadPlayerScoreResult scoreResult) {
        return scoreResult != null && GamesStatusCodes.STATUS_OK == scoreResult.getStatus().getStatusCode() && scoreResult.getScore() != null;
    }
}
