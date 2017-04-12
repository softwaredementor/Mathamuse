package com.appfission.mathamuse;

import android.app.Activity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by srikanthmannepalle on 3/9/17.
 */

public class BaseScoreActivity extends Activity {

    public static final Map<Integer, int[]> LEVEL_MAPPINGS = new HashMap<>();

    public BaseScoreActivity() {
        LEVEL_MAPPINGS.put(0, new int[] {0, 2499, 75});
        LEVEL_MAPPINGS.put(1, new int[] {2500, 4999, 150});
        LEVEL_MAPPINGS.put(2, new int[] {5000, 7999, 225});
        LEVEL_MAPPINGS.put(3, new int[] {8000, 10999, 350});
        LEVEL_MAPPINGS.put(4, new int[] {11000, 14999, 450});
        LEVEL_MAPPINGS.put(5, new int[] {15000, 18999, 550});
        LEVEL_MAPPINGS.put(6, new int[] {19000, 22999, 650});
        LEVEL_MAPPINGS.put(7, new int[] {23000, 26999, 750});
        LEVEL_MAPPINGS.put(8, new int[] {27000, 31999, 850});
        LEVEL_MAPPINGS.put(9, new int[] {32000, 37999, 1000});
        LEVEL_MAPPINGS.put(10, new int[] {38000, 43999, 1200});
        LEVEL_MAPPINGS.put(11, new int[] {44000, 49999, 1400});
        LEVEL_MAPPINGS.put(12, new int[] {50000, 57999, 1650});
        LEVEL_MAPPINGS.put(13, new int[] {58000, 64999, 2000});
    }

    protected int calculateCurrentLevel(long totalGameScore, long totalStarsCount) {
        int currentLevel = 0;
        if (((totalGameScore >=0 && totalGameScore <=2499) || totalGameScore > 2499) && ((totalStarsCount >0 && totalStarsCount <=75) || totalStarsCount >75)) {
            currentLevel = 0;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=2500 && totalGameScore <=4999) || totalGameScore > 4999) && ((totalStarsCount >75 && totalStarsCount <=150) || totalStarsCount >150)) {
            currentLevel = 1;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=5000 && totalGameScore <=7999) || totalGameScore > 7999) && ((totalStarsCount >150 && totalStarsCount <=225) || totalStarsCount >225)) {
            currentLevel = 2;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=8000 && totalGameScore <=10999) || totalGameScore > 10999) && ((totalStarsCount >225 && totalStarsCount <=350) || totalStarsCount >350)) {
            currentLevel = 3;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=11000 && totalGameScore <=14999) || totalGameScore > 14999) && ((totalStarsCount >350 && totalStarsCount <=450) || totalStarsCount >450)) {
            currentLevel = 4;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=15000 && totalGameScore <=18999) || totalGameScore > 18999) && ((totalStarsCount >450 && totalStarsCount <=550) || totalStarsCount >550)) {
            currentLevel = 5;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=19000 && totalGameScore <=22999) || totalGameScore > 22999) && ((totalStarsCount >550 && totalStarsCount <=650) || totalStarsCount >650)) {
            currentLevel = 6;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=23000 && totalGameScore <=26999) || totalGameScore > 26999) && ((totalStarsCount >650 && totalStarsCount <=750) || totalStarsCount >750)) {
            currentLevel = 7;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=27000 && totalGameScore <=31999) || totalGameScore > 31999) && ((totalStarsCount >750 && totalStarsCount <=850) || totalStarsCount >850)) {
            currentLevel = 8;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=32000 && totalGameScore <=37999) || totalGameScore > 37999) && ((totalStarsCount >850 && totalStarsCount <=1000) || totalStarsCount >1000)) {
            currentLevel = 9;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=38000 && totalGameScore <=43999) || totalGameScore > 43999) && ((totalStarsCount >1000 && totalStarsCount <=1200) || totalStarsCount >1200)) {
            currentLevel = 10;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=44000 && totalGameScore <=49999) || totalGameScore > 49999) && ((totalStarsCount >1200 && totalStarsCount <=1400) || totalStarsCount >1400)) {
            currentLevel = 11;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=50000 && totalGameScore <=57999) || totalGameScore > 57999) && ((totalStarsCount >1400 && totalStarsCount <=1650) || totalStarsCount >1650)) {
            currentLevel = 12;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }  if(((totalGameScore >=58000 && totalGameScore <=64999) || totalGameScore > 64999) && ((totalStarsCount >1650 && totalStarsCount <=2000) || totalStarsCount >2000)) {
            currentLevel = 13;
            Log.d(getClass().getName(), "Gamescore = " + totalGameScore + " and Star count = " + totalStarsCount + " current level = " + currentLevel);
        }
        return currentLevel;
    }


    protected String calculateCurrentLevelName(long totalGameScore, long totalStarsCount) {
        int currentLevel = calculateCurrentLevel(totalGameScore, totalStarsCount);
        switch(currentLevel) {
            case 0 : return "noob";
            case 1 : return "rookie";
            case 2 : return "semipro";
            case 3 : return "pro";
            case 4 : return "juggler";
            case 5 : return "rebel";
            case 6 : return "talented";
            case 7 : return "veteren";
            case 8 : return "master";
            case 9 : return "magician";
            case 10 : return "expert";
            case 11 : return "legend";
            case 12 : return "unstoppable";
            case 13 : return "godlike";
            default: return "";
        }
    }

    protected int calculateNextLevel(long totalGameScore, long totalStarsCount) {
        int currentLevel = calculateCurrentLevel(totalGameScore, totalStarsCount);
        if (currentLevel < 13) {
            return currentLevel + 1;
        } else {
            return -1;
        }
    }


    protected String calculateNextLevelName(long totalGameScore, long totalStarsCount) {
        int nextLevel = calculateNextLevel(totalGameScore, totalStarsCount);
        switch(nextLevel) {
            case 1 : return "rookie";
            case 2 : return "semipro";
            case 3 : return "pro";
            case 4 : return "juggler";
            case 5 : return "rebel";
            case 6 : return "talented";
            case 7 : return "veteren";
            case 8 : return "master";
            case 9 : return "magician";
            case 10 : return "expert";
            case 11 : return "legend";
            case 12 : return "unstoppable";
            case 13 : return "godlike";
            case - 1: return "";
            default: return null;
        }
    }

    protected String[] calculateRequiredScoreAndStars(long totalGameScore, long totalStarsCount) {
        int currentLevel = calculateCurrentLevel(totalGameScore, totalStarsCount);
        int nextLevel = calculateNextLevel(totalGameScore, totalStarsCount);
        long requiredGameScore, requiredStarCount;
        Log.d(getClass().getName(), "currentLevel = " + currentLevel + " and nextLevel = " + nextLevel);

        if (nextLevel == -1) {
            return new String[] {"", ""};
        } else {
            requiredGameScore = LEVEL_MAPPINGS.get(nextLevel)[0] - totalGameScore;
            requiredStarCount = LEVEL_MAPPINGS.get(nextLevel)[2] - totalStarsCount;
            Log.d(getClass().getName(), "requiredGameScore = " + requiredGameScore + " and requiredStarCount = " + requiredStarCount);
        }
        return new String[] {String.valueOf(requiredGameScore), String.valueOf(requiredStarCount)};
    }
}
