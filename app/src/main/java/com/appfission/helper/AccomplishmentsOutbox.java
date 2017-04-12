package com.appfission.helper;

import android.content.Context;
import android.util.Log;

import com.appfission.mathamuse.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

/**
 * Created by srikanthmannepalle on 3/22/17.
 */

public class AccomplishmentsOutbox {

    public boolean mNoobAchievement = false;
    public boolean mRookieAchievement = false;
    public boolean mSemiproAchievement = false;
    public boolean mProAchievement = false;
    public boolean mJugglerAchievement = false;
    public boolean mRebelAchievement = false;
    public boolean mTalentedAchievement = false;
    public boolean mVeterenAchievement = false;
    public boolean mMasterAchievement = false;
    public boolean mMagicianAchievement = false;
    public boolean mExpertAchievement = false;
    public boolean mLegendAchievement = false;
    public boolean mUnstoppableAchievement = false;
    public boolean mGodlikeAchievement = false;


    public static Context context;
    public static GoogleApiClient mGoogleApiClient;
    private final static String TAG = AccomplishmentsOutbox.class.getName();

    public AccomplishmentsOutbox(Context ctx, GoogleApiClient mGoogleApiClient) {
        this.context = ctx;
        this.mGoogleApiClient = mGoogleApiClient;
    }

    //Useful if we were to save and upload data to cloud
    public boolean isEmpty() {
        return (!mNoobAchievement && !mRookieAchievement && !mSemiproAchievement && !mProAchievement
                && !mJugglerAchievement && !mRebelAchievement && !mTalentedAchievement && !mVeterenAchievement
                && !mMasterAchievement && !mMagicianAchievement && !mExpertAchievement && !mLegendAchievement
                && !mUnstoppableAchievement && !mGodlikeAchievement);
    }

    public void saveLocal(Context ctx) {
            /* TODO: This is left as an exercise. To make it more difficult to cheat,
             * this data should be stored in an encrypted file! And remember not to
             * expose your encryption key (obfuscate it by building it from bits and
             * pieces and/or XORing with another string, for instance). */
    }

    public void loadLocal(Context ctx) {
            /* TODO: This is left as an exercise. Write code here that loads data
             * from the file you wrote in saveLocal(). */
    }

    public boolean isSignedIn() {
        Log.d(TAG, "mGoogleApiClient is null = " + String.valueOf(mGoogleApiClient == null));
        Log.d(TAG, "mGoogleApiClient is connected = " + String.valueOf(mGoogleApiClient.isConnected()));
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    public void pushAccomplishments() {
        Log.d(TAG, "Pushing accomplishments");
        if (!isSignedIn()) {
            // can't push to the cloud, so save locally
            saveLocal(context);
            Log.d(TAG, "User was not signed in, so saving his local data");
            return;
        }
        Log.d(TAG, "User signed in, achievements unlocking begins");
        if (mNoobAchievement) {
            Log.d(TAG, "Noob unlocked = " + context.getResources().getString(R.string.achievement_noob));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_noob));
            mNoobAchievement = false;
        }
        if (mRookieAchievement) {
            Log.d(TAG, "Rookie unlocked = " + context.getResources().getString(R.string.achievement_rookie));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_rookie));
            mRookieAchievement = false;
        }
        if (mSemiproAchievement) {
            Log.d(TAG, "Semipro unlocked = " + context.getResources().getString(R.string.achievement_semi_pro));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_semi_pro));
            mSemiproAchievement = false;
        }
        if (mProAchievement) {
            Log.d(TAG, "Pro unlocked = " + context.getResources().getString(R.string.achievement_pro));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_pro));
            mProAchievement = false;
        }
        if (mJugglerAchievement) {
            Log.d(TAG, "Juggler unlocked = " + context.getResources().getString(R.string.achievement_juggler));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_juggler));
            mJugglerAchievement = false;
        }
        if (mRebelAchievement) {
            Log.d(TAG, "Rebel unlocked = " + context.getResources().getString(R.string.achievement_rebel));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_rebel));
            mRebelAchievement = false;
        }
        if (mTalentedAchievement) {
            Log.d(TAG, "Talented unlocked = " + context.getResources().getString(R.string.achievement_talented));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_talented));
            mTalentedAchievement = false;
        }
        if (mVeterenAchievement) {
            Log.d(TAG, "Veteren unlocked = " + context.getResources().getString(R.string.achievement_veteren));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_veteren));
            mVeterenAchievement = false;
        }
        if (mMasterAchievement) {
            Log.d(TAG, "Hamster unlocked = " + context.getResources().getString(R.string.achievement_master));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_master));
            mMasterAchievement = false;
        }
        if (mMagicianAchievement) {
            Log.d(TAG, "Magician unlocked = " + context.getResources().getString(R.string.achievement_magician));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_magician));
            mMagicianAchievement = false;
        }
        if (mExpertAchievement) {
            Log.d(TAG, "Expert unlocked = " + context.getResources().getString(R.string.achievement_expert));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_expert));
            mExpertAchievement = false;
        }
        if (mLegendAchievement) {
            Log.d(TAG, "Legend unlocked = " + context.getResources().getString(R.string.achievement_legend));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_legend));
            mLegendAchievement = false;
        }
        if (mUnstoppableAchievement) {
            Log.d(TAG, "Unstoppable unlocked = " + context.getResources().getString(R.string.achievement_unstoppable));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_unstoppable));
            mUnstoppableAchievement = false;
        }
        if (mGodlikeAchievement) {
            Log.d(TAG, "Godlike unlocked = " + context.getResources().getString(R.string.achievement_godlike));
            Games.Achievements.unlock(mGoogleApiClient, context.getResources().getString(R.string.achievement_godlike));
            mGodlikeAchievement = false;
        }
        saveLocal(context);
    }
}

