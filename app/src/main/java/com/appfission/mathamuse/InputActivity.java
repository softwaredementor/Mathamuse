package com.appfission.mathamuse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appfission.helper.AccomplishmentsOutbox;
import com.appfission.helper.Result;
import com.appfission.utils.Constants;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.appfission.helper.AccomplishmentsOutbox.mGoogleApiClient;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public class InputActivity extends Activity {

    private TextView scoreTextView;
    private TextView timerTextView;

    private CountDownTimer countDownTimer;
    private DecimalFormat decimalFormat;
    private String questionText;
    private String answerText;
    private String correctAnswer;
    private String categoryName;
    private String difficultyLevel;
    private String duration;
    private String gameMode;
    private String TAG = InputActivity.class.getName();
    private int scoreValue = 0;
    private int totalCorrectAnswers = 0;
    private int totalIncorrectAnswers = 0;
    private long countDowntimerValue = 15000;
    private long totalEasymodeScore;
    private long totalMediummodeScore;
    private long totalHardmodeScore;
    private long totalGameScore;
    private long totalStarScore;

    private  boolean isSoundEnabled;
    private  boolean isVibrationEnabled;
    private  boolean isNotificationsEnabled;
    private boolean isAnswerCorrect;
    private int maxStreakCount = 0, streakCount = 0;
    private int questionCount = 0;
    private long totalScore;
    private long totalStars;

    private ArrayList<Result> results;

    private TextView answerTextView;
    private TextView questionTextView;
    private TextView questionCountTextView;

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonDel;
    private Button buttonClr;
    private Button buttonEnter;
    private Button buttonDecimal;
    private Button buttonMinus;
    private Button buttonTrue;
    private Button buttonFalse;

    private MediaPlayer correctAnswerSoundPlayer;
    private MediaPlayer incorrectAnswerSoundPlayer;
    private Vibrator vibrator;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        decimalFormat = new DecimalFormat("#0.00");
        correctAnswerSoundPlayer = MediaPlayer.create(this, R.raw.correctanswer);
        incorrectAnswerSoundPlayer = MediaPlayer.create(this, R.raw.incorrectanswer);
        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        results = new ArrayList<>(0);

        //initialize the sharedpreference and the editor
        sharedPreferences = getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Unpack the passed bundle and do initializations
        //load sound, vibration, notifications settings
        unpackPassedBundleAndInitialize();

        //code for placing ads


        //Initialize the score to 0
        scoreTextView = (TextView) findViewById(R.id.scoreText);
        scoreTextView.setText("Score : " + String.valueOf(scoreValue));
        questionCountTextView = (TextView) findViewById(R.id.questionNumber);
        questionCountTextView.setText("Ques : 1");

        //Setting for countdowntimer
        timerTextView = (TextView) findViewById(R.id.timerText);

        //don't call the timer if it's practice mode
        if(gameMode.equals(Constants.TIMED_MODE)) {
            runTimer();
        }

        //initiate button handlers
        initializeButtonHandlers();

        //disable all non true-false controls for this menu
        if (categoryName.equals(Constants.CATEGORY_TRUEORFALSE)) {
            disableAllNonTrueFalseControls();
        } else {
            disableTrueFalseControls();
        }

        //set the question
        questionGenerator();

    }

    private void unpackPassedBundleAndInitialize() {
        Bundle passedParam = getIntent().getExtras();
        categoryName = passedParam.getString(Constants.CATEGORY_NAME);
        gameMode = passedParam.getString(Constants.GAME_MODE_KEY, Constants.PRACTICE_MODE);
        difficultyLevel = passedParam.getString(Constants.DIFFICULTY_LEVEL_KEY);
        duration = passedParam.getString(Constants.DURATION_KEY, Constants.MINUTE_1);
        countDowntimerValue = Long.parseLong(duration.split("min")[0])*60000;
        isSoundEnabled = passedParam.getBoolean(Constants.SOUND_ENABLED_KEY, Constants.TRUE);
        isVibrationEnabled = passedParam.getBoolean(Constants.VIBRATION_ENABLED_KEY, Constants.TRUE);
        isNotificationsEnabled = passedParam.getBoolean(Constants.NOTIFICATIONS_ENABLED_KEY, Constants.TRUE);
    }

    private void questionGenerator() {
        questionCountTextView.setText("Ques : " + ++questionCount);
        questionTextView = (TextView) findViewById(R.id.questionText);
        String[] output = QuestionGeneratorFactory.getQuestion(categoryName, difficultyLevel);

        //massage the questionText as per the category
        questionText = output[0];
        questionTextView.setText(Html.fromHtml(questionText));
        if (categoryName.equals(Constants.CATEGORY_LOGARITHM) || categoryName.equals(Constants.CATEGORY_EQUATIONS) || categoryName.equals(Constants.CATEGORY_FRACTIONS) || categoryName.equals(Constants.CATEGORY_STATISTICS) || categoryName.equals(Constants.CATEGORY_PERCENTAGES)) {
            questionTextView.setTextScaleX(.7f);
        }
        Log.d(TAG, "Output received, output[1] = " + output[1]);
        if (!categoryName.equals(Constants.CATEGORY_TRUEORFALSE)) {
            if (categoryName.equals(Constants.CATEGORY_STATISTICS) && output[1].contains("[")) {
                this.correctAnswer = output[1];
            } else {
                this.correctAnswer = decimalFormat.format(Double.parseDouble(output[1]));
            }
        } else {
            this.correctAnswer = output[1];
        }
        Log.d(TAG, "Calculated value after formatting = " + correctAnswer);
    }

    private void answerEvaluator() {
        //vibrate for incorrect answer
        //sound for correct answer

        /*
         * This is to take care of the rounding of the answer as per the game menu.
         * percentage , fractions , ratios, trignometry will require this.
         */
        Result singleResult;

        if (categoryName.equals(Constants.CATEGORY_TRUEORFALSE)) {
            if (null != answerText && answerText.length() > 0) {
                Log.d(TAG, "True and False category");
            }

        } else {
            if (null != answerText && answerText.length() > 0 && !answerText.equals(".")
                    && !answerText.equals("-") && !answerText.equals("-.")) {
                answerText = decimalFormat.format(Double.parseDouble(answerText));
                Log.d(TAG, "Formatted answer = " + answerText);
            }
        }
        Log.d(TAG, "Correct answer = " + correctAnswer + "inputted answer = " + answerText);
        if (correctAnswer.equals(answerText)) {
            Log.d(TAG, "Formatted answer = " + answerText);
            ++totalCorrectAnswers;
            ++streakCount;
            isAnswerCorrect = true;
            maxStreakCount = streakCount > maxStreakCount ? streakCount : maxStreakCount;
            if (isSoundEnabled) {
                correctAnswerSoundPlayer.start();
            }
            scoreTextView.setText("Score : " + String.valueOf(++scoreValue));
        } else if(categoryName.equals(Constants.CATEGORY_STATISTICS)) {
            // special checks for statistic section because of mode outputs can be a single number or an array of possible soutions.
            // So to handle "[" and "]" is this all headache !
            String trimmedCorrectAnswer;
            if (!correctAnswer.contains("[")) {
                trimmedCorrectAnswer = correctAnswer;
            } else {
                trimmedCorrectAnswer = correctAnswer.substring(1, correctAnswer.length() - 1);
            }


            String[] possibleAnswersAsArr = trimmedCorrectAnswer.split(",");
            List<String> possibleAnswers = new ArrayList<>();

            for (int i = 0 ; i <possibleAnswersAsArr.length; i++) {
                possibleAnswers.add(decimalFormat.format(Double.parseDouble(possibleAnswersAsArr[i])));
            }
            if (null != answerText && answerText.length() > 0) {
                boolean isFound = false;
                for (String canBeAnAnswer : possibleAnswers) {
                    Log.d(TAG, "answerText = " + answerText);
                    Log.d(TAG, "canBeAnAnswer = " + canBeAnAnswer);
                    if (canBeAnAnswer.equals(answerText)) {
                        ++totalCorrectAnswers;
                        ++streakCount;
                        isAnswerCorrect = true;
                        maxStreakCount = streakCount > maxStreakCount ? streakCount : maxStreakCount;
                        if (isSoundEnabled) {
                            correctAnswerSoundPlayer.start();
                        }
                        scoreTextView.setText("Score : " + String.valueOf(++scoreValue));
                        isFound = true;
                        break;
                    }
                }
                if(!isFound) {
                    updateIncorrectStats();
                }
            } else {
                updateIncorrectStats();
            }
        } else {
            updateIncorrectStats();
        }

        if (isAnswerCorrect) {
            singleResult = new Result(String.valueOf(questionCount), "correctanswer", correctAnswer, answerText);
        } else {
            singleResult = new Result(String.valueOf(questionCount), "wronganswer", correctAnswer, answerText);
        }
        results.add(singleResult);
        //save the correct answer and the user inputted answer in a map and
        //pass it to the summary screen
        //generate the next question
        questionGenerator();
    }

    private void updateIncorrectStats() {
        isAnswerCorrect = false;
        if (isSoundEnabled) {
            incorrectAnswerSoundPlayer.start();
        }
        if (isVibrationEnabled) {
            vibrator.vibrate(300);
        }
        ++totalIncorrectAnswers;
        maxStreakCount = streakCount > maxStreakCount ? streakCount : maxStreakCount;
        streakCount = 0;
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
    public void onBackPressed() {
        if(gameMode.equals(Constants.TIMED_MODE)) {
            countDownTimer.cancel();
        }
        super.onBackPressed();
    }

    private void runTimer() {
        countDownTimer = new CountDownTimer(countDowntimerValue, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTextView.setText("Over");

                //pass data to score activity and calculate stars
                passScoreAndCalculateStars();
            }
        }.start();
    }

    //update the scores and star count for the give category name
    //and difficulty level
    private void persistCategoryScores() {
        Log.d(TAG, "difficulty level = " + difficultyLevel);
        switch (difficultyLevel) {
            case Constants.EASY : persistCategoryEasyScores(); break;
            case Constants.MEDIUM : persistCategoryMediumScores(); break;
            case Constants.HARD : persistCategoryHardScores(); break;
        }

        //special check for true of false menu only to stop spamming !!!
        if (categoryName.equals(Constants.CATEGORY_TRUEORFALSE)) {
            if(totalIncorrectAnswers > (int)(0.3*(totalCorrectAnswers + totalIncorrectAnswers))) {
                totalScore = 0l;
                totalStars = 0l;
            }
        }

        //section for updating the leaderboards
        if (difficultyLevel.equals(Constants.EASY)) {
            loadScoreOfEasyBoard();
        } else if (difficultyLevel.equals(Constants.MEDIUM)) {
            loadScoreOfMediumBoard();
        } else if (difficultyLevel.equals(Constants.HARD)) {
            loadScoreOfHardBoard();
        } else {
            Log.d(TAG, "No match found for updating leaderboard. Please check!");
        }

        //retrieve and update the overall game score
        loadAndUpdateScoreOfOverallBoard();
        loadAndUpdateStarLeaderboard();
    }

    private void loadScoreOfEasyBoard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(mGoogleApiClient, getString(R.string.leaderboard_easy_level), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                if (isScoreResultValid(scoreResult)) {
                    // here you can get the score like this
                    totalEasymodeScore = scoreResult.getScore().getRawScore();
                    Log.d(TAG, "Retrieved score for easy level leaderboard = " + totalEasymodeScore);
                } else {
                    Log.d(TAG, "Score being set to 0 for current user's easy level leaderboard during initialization");
                    totalEasymodeScore = 0;
                }
                    totalEasymodeScore += totalScore;
                    Log.d(TAG, "Current total easy mode score = " + totalEasymodeScore);
                    if(AccomplishmentsOutbox.mGoogleApiClient.isConnected()) {
                        Games.Leaderboards.submitScore(mGoogleApiClient, getResources().getString(R.string.leaderboard_easy_level), totalEasymodeScore);
                        Log.d(TAG, "Submitting score = " + totalEasymodeScore + "to Easy level leaderboard");
                    } else {
                        Log.d(TAG, "Not submitting score to easy leaderboard since user is not signed in");
                    }
                }
        });
    }

    private void loadScoreOfMediumBoard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(mGoogleApiClient, getString(R.string.leaderboard_medium_level), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                    if (isScoreResultValid(scoreResult)) {
                        // here you can get the score like this
                        totalMediummodeScore = scoreResult.getScore().getRawScore();
                        Log.d(TAG, "Retrieved score for medium level leaderboard = " + totalMediummodeScore);
                    } else {
                        totalMediummodeScore = 0;
                        Log.d(TAG, "Score being set to 0 for current user's medium level leaderboard during initialization");
                    }
                    totalMediummodeScore += totalScore;
                    Log.d(TAG, "Current total medium mode score = " + totalMediummodeScore);
                    if(AccomplishmentsOutbox.mGoogleApiClient.isConnected()) {
                        Games.Leaderboards.submitScore(mGoogleApiClient, getResources().getString(R.string.leaderboard_medium_level), totalMediummodeScore);
                        Log.d(TAG, "Submitting score = " + totalMediummodeScore + " to Medium level leaderboard");
                    } else {
                        Log.d(TAG, "Not submitting score to medium leaderboard since user is not signed in");
                    }
                }
        });
    }

    private void loadScoreOfHardBoard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(mGoogleApiClient, getString(R.string.leaderboard_hard_level), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                    if (isScoreResultValid(scoreResult)) {
                        // here you can get the score like this
                        totalHardmodeScore = scoreResult.getScore().getRawScore();
                        Log.d(TAG, "Retrieved score for hard level leaderboard = " + totalHardmodeScore);
                    } else {
                        totalHardmodeScore = 0;
                        Log.d(TAG, "Score being set to 0 for current user's hard level leaderboard during initialization");
                    }
                    totalHardmodeScore += totalScore;
                    Log.d(TAG, "Current total hard mode score = " + totalHardmodeScore);
                    if(AccomplishmentsOutbox.mGoogleApiClient.isConnected()) {
                        Games.Leaderboards.submitScore(mGoogleApiClient, getResources().getString(R.string.leaderboard_hard_level), totalHardmodeScore);
                        Log.d(TAG, "Submitting score = " + totalHardmodeScore + " to Hard level leaderboard");
                    } else {
                        Log.d(TAG, "Not submitting score to hard leaderboard since user is not signed in");
                    }
                }
        });
    }

    private void loadAndUpdateScoreOfOverallBoard() {
        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(mGoogleApiClient, getString(R.string.leaderboard_overall), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
            @Override
            public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                    if (isScoreResultValid(scoreResult)) {
                        // here you can get the score like this
                        totalGameScore = scoreResult.getScore().getRawScore();
                        Log.d(TAG, "Retrieved score for overall leaderboard = " + totalGameScore);
                    } else {
                        Log.d(TAG, "Score being set to 0 for current user's overall level leaderboard during initialization");
                        totalGameScore = 0;
                    }
                    totalGameScore += totalScore;
                    Log.d(TAG, "Current total overall score = " + totalGameScore);
                    //update overall leaderboard and push it to google play
                    if(AccomplishmentsOutbox.mGoogleApiClient.isConnected()) {
                        Games.Leaderboards.submitScore(mGoogleApiClient, getResources().getString(R.string.leaderboard_overall), totalGameScore);
                        Log.d(TAG, "Submitting score = " + totalGameScore + " to Overall leaderboard");
                    } else {
                        Log.d(TAG, "Not submitting score to overall leaderboard since user is not signed in");
                    }

                    //Update the overall score and star count.
                    // This will be used by the game info screen and the levels activity screen
                    editor.putLong(Constants.TOTAL_GAME_SCORE, totalGameScore);
                    editor.commit();
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
                } else {
                    Log.d(TAG, "Score being set to 0 for current user during initialization");
                    totalStarScore = 0;
                }
                    totalStarScore += totalStars;
                    Log.d(TAG, "Current total star score = " + totalStarScore);
                    if(AccomplishmentsOutbox.mGoogleApiClient.isConnected()) {
                        Games.Leaderboards.submitScore(mGoogleApiClient, getResources().getString(R.string.leaderboard_star_achievers), totalStarScore);
                        Log.d(TAG, "Submitting score = " + totalStarScore + " to star leaderboard");
                    } else {
                        Log.d(TAG, "Not submitting score to star leaderboard since user is not signed in");
                    }
                    editor.putLong(Constants.TOTAL_STARS_COUNT, totalStarScore);
                    editor.commit();
                }
        });
    }

    private boolean isScoreResultValid(final Leaderboards.LoadPlayerScoreResult scoreResult) {
        return scoreResult != null && GamesStatusCodes.STATUS_OK == scoreResult.getStatus().getStatusCode() && scoreResult.getScore() != null;
    }

    private void persistCategoryEasyScores() {

        String casePattern = new StringBuilder("CATEGORY_").append(categoryName.toUpperCase())
                .append("_").append(difficultyLevel.toUpperCase()).toString();
        Log.d(getClass().getName(), "casePattern = " + casePattern);

        switch (casePattern) {
            case Constants.CATEGORY_ARITHMETIC_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_ARITHMETIC_EASY);
                break;
            case Constants.CATEGORY_LOGARITHM_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_LOGARITHM_EASY);
                break;
            case Constants.CATEGORY_FRACTIONS_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_FRACTIONS_EASY);
                break;
            case Constants.CATEGORY_PERCENTAGES_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_PERCENTAGES_EASY);
                break;
            case Constants.CATEGORY_EQUATIONS_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_EQUATIONS_EASY);
                break;
            case Constants.CATEGORY_EXPONENTS_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_EXPONENTS_EASY );
                break;
            case Constants.CATEGORY_TRIGONOMETRY_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_TRIGONOMETRY_EASY);
                break;
            case Constants.CATEGORY_STATISTICS_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_STATISTICS_EASY);
                break;
            case Constants.CATEGORY_TRUEORFALSE_EASY :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_TRUEORFALSE_EASY);
                break;
            case Constants.CATEGORY_MULTIPLICATIONS_EASY:
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_MULTIPLICATIONS_EASY);
                break;
        }
        editor.commit();
    }

    private void updateHighScoresForApplicableCategoryAndDifficultyLevel(String categoryDifficultyLevel) {
        long currentHighScore;

        //add logic to update highscores
        currentHighScore = sharedPreferences.getLong(categoryDifficultyLevel, 0);
        if (currentHighScore < totalScore) {
            Log.d(TAG, "Current score = " + currentHighScore + " and total score = " + totalScore);
            editor.putLong(categoryDifficultyLevel, totalScore);
            editor.commit();
        }
    }

    private void persistCategoryMediumScores() {

        String casePattern = new StringBuilder("CATEGORY_").append(categoryName.toUpperCase())
                .append("_").append(difficultyLevel.toUpperCase()).toString();
        Log.d(TAG, "casePattern = " + casePattern);
        switch (casePattern) {
            case Constants.CATEGORY_ARITHMETIC_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_ARITHMETIC_MEDIUM);
                break;
            case Constants.CATEGORY_LOGARITHM_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_LOGARITHM_MEDIUM);
                break;
            case Constants.CATEGORY_FRACTIONS_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_FRACTIONS_MEDIUM);
                break;
            case Constants.CATEGORY_PERCENTAGES_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_PERCENTAGES_MEDIUM);
                break;
            case Constants.CATEGORY_EQUATIONS_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_EQUATIONS_MEDIUM);
                break;
            case Constants.CATEGORY_EXPONENTS_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_EXPONENTS_MEDIUM );
                break;
            case Constants.CATEGORY_TRIGONOMETRY_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_TRIGONOMETRY_MEDIUM);
                break;
            case Constants.CATEGORY_STATISTICS_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_STATISTICS_MEDIUM);
                break;
            case Constants.CATEGORY_TRUEORFALSE_MEDIUM :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_TRUEORFALSE_MEDIUM);
                break;
            case Constants.CATEGORY_MULTIPLICATIONS_MEDIUM:
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_MULTIPLICATIONS_MEDIUM);
                break;

        }
        editor.commit();
    }

    private void persistCategoryHardScores() {

        String casePattern = new StringBuilder("CATEGORY_").append(categoryName.toUpperCase())
                .append("_").append(difficultyLevel.toUpperCase()).toString();
        Log.d(TAG, "casePattern = " + casePattern);
        switch (casePattern) {
            case Constants.CATEGORY_ARITHMETIC_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_ARITHMETIC_HARD);
                break;
            case Constants.CATEGORY_LOGARITHM_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_LOGARITHM_HARD);
                break;
            case Constants.CATEGORY_FRACTIONS_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_FRACTIONS_HARD);
                break;
            case Constants.CATEGORY_PERCENTAGES_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_PERCENTAGES_HARD);
                break;
            case Constants.CATEGORY_EQUATIONS_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_EQUATIONS_HARD);
                break;
            case Constants.CATEGORY_EXPONENTS_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_EXPONENTS_HARD );
                break;
            case Constants.CATEGORY_TRIGONOMETRY_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_TRIGONOMETRY_HARD);
                break;
            case Constants.CATEGORY_STATISTICS_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_STATISTICS_HARD);
                break;
            case Constants.CATEGORY_TRUEORFALSE_HARD :
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_TRUEORFALSE_HARD);
                break;
            case Constants.CATEGORY_MULTIPLICATIONS_HARD:
                updateHighScoresForApplicableCategoryAndDifficultyLevel(Constants.CATEGORY_MULTIPLICATIONS_HARD);
                break;

        }
        editor.commit();
    }

    private void initializeButtonHandlers() {

        answerText = "";
        answerTextView = (TextView) findViewById(R.id.answerText);
        answerTextView.setText(answerText);

        buttonTrue = (Button) findViewById(R.id.buttonTrue);
        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerText = "true";
                answerEvaluator();
                answerText = "";
                answerTextView.setText(answerText);
            }
        });

        buttonFalse = (Button) findViewById(R.id.buttonFalse);
        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerText = "false";
                answerEvaluator();
                answerText = "";
                answerTextView.setText(answerText);
            }
        });

        button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "0";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "1";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "2";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "3";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "4";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "5";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "6";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "7";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "8";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerText.length() <= 10) {
                    answerText += "9";
                    answerTextView.setText(answerText);
                } else {
                    Toast.makeText(getApplicationContext(), "Action not permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonMinus = (Button) findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answerText.length() == 0) {
                    answerText += "-";
                    answerTextView.setText(answerText);
                }
            }
        });

        buttonDecimal = (Button) findViewById(R.id.buttonDecimal);
        buttonDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answerText.contains(".") && answerText.length() <= 10) {
                    answerText += ".";
                    answerTextView.setText(answerText);
                }
            }
        });

        buttonDel = (Button) findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( null!= answerText && answerText.length() > 0) {
                    answerText = answerText.substring(0, answerText.length() - 1);
                    answerTextView.setText(answerText);
                }
            }
        });

        buttonClr = (Button) findViewById(R.id.buttonClr);
        buttonClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerText = "";
                answerTextView.setText(answerText);
            }
        });

        buttonEnter = (Button) findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //evaluate the answer
                answerEvaluator();
                answerText = "";
                answerTextView.setText(answerText);
            }
        });

    }

    private void disableAllNonTrueFalseControls() {
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
        buttonDel.setEnabled(false);
        buttonClr.setEnabled(false);
        buttonMinus.setEnabled(false);
        buttonEnter.setEnabled(false);
        buttonDecimal.setEnabled(false);
    }

    private void disableTrueFalseControls() {
        buttonTrue.setEnabled(false);
        buttonFalse.setEnabled(false);
    }

    private void passScoreAndCalculateStars() {

        //assign special weigthages to tough menus
        int categoryFactor = 1;

        switch (categoryName) {
            case Constants.CATEGORY_LOGARITHM : categoryFactor = 2; break;
            case Constants.CATEGORY_FRACTIONS : categoryFactor = 3; break;
            case Constants.CATEGORY_PERCENTAGES: categoryFactor = 3; break;
            case Constants.CATEGORY_EQUATIONS : categoryFactor = 2; break;
            case Constants.CATEGORY_TRIGONOMETRY: categoryFactor = 3; break;
            case Constants.CATEGORY_STATISTICS : categoryFactor = 2; break;
            default : categoryFactor = 1; break;
        }

        int totalQuestions = totalCorrectAnswers + totalIncorrectAnswers;
        float normalizationFactor = totalCorrectAnswers*1000f/countDowntimerValue;
        Log.d(TAG, "Normalization factor = " + normalizationFactor);
        //factor values : easy->1, medium->2, hard->3
        totalScore = (totalCorrectAnswers + maxStreakCount*2)*categoryFactor;
        Log.d(TAG, "totalscore = " + totalScore);
        if(normalizationFactor > 0.20f && normalizationFactor < 0.40f) {
            totalStars = 1;
        } else if(normalizationFactor >= 0.40f && normalizationFactor < 0.6f) {
            totalStars = 2;
        } else if(normalizationFactor >= 0.6f) {
            totalStars = 3;
        }


        //persist the scores to the local storage.
        persistCategoryScores();

        //pass score data
        Intent startScoreActivity = new Intent(getApplicationContext(), ScoreActivity.class);
        startScoreActivity.putExtra("CategoryName", categoryName);
        startScoreActivity.putExtra("DifficultyLevel", difficultyLevel);
        startScoreActivity.putExtra("TotalQuestions", String.valueOf(totalQuestions));
        startScoreActivity.putExtra("TotalCorrectAnswers", String.valueOf(totalCorrectAnswers));
        startScoreActivity.putExtra("TotalIncorrectAnswers", String.valueOf(totalIncorrectAnswers));
        startScoreActivity.putExtra("MaxStreakCount", String.valueOf(2*maxStreakCount));
        startScoreActivity.putExtra("TotalScore", String.valueOf(totalScore));
        startScoreActivity.putExtra("TotalStars", String.valueOf(totalStars));
        startScoreActivity.putParcelableArrayListExtra("SummaryResults", results);
        startActivity(startScoreActivity);
    }
}
