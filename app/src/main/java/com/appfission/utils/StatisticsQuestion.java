package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by srikanthmannepalle on 2/27/17.
 */

public class StatisticsQuestion extends GenerateQuestion implements Question {
    //mean, median, mode, range, standard deviation, covariance

    DecimalFormat decimalFormat = new DecimalFormat("##.00");
    private static String TAG = StatisticsQuestion.class.getName();

    public StatisticsQuestion(String gameLevel) {
        difficultyLevel = gameLevel;
    }

    @Override
    public String[] createQuestion() {
        switch (difficultyLevel) {
            case "Easy":
                return easyQuestion();
            case "Medium" :
                return mediumQuestion();
            case "Hard" :
                return hardQuestion();
            default:
                return null;
        }
    }

    private String[] easyQuestion() {
        return generateStatQuestion(4);
    }

    private String[] mediumQuestion() {
      return generateStatQuestion(5);
    }

    private String[] hardQuestion() {
        return  generateStatQuestion(6);
    }

    private String[] generateStatQuestion(int arraySize) {
        int[] numbers = new int[arraySize];
        for (int i = 0 ; i < numbers.length; i++) {
            numbers[i] = (int)Math.round(Math.random()*10);
        }

        int caseIndex = (int) Math.round((Math.random()*3));
        String displayExpression = null, calculatedValue = null;
        Log.d(TAG, "Calculated index = " + caseIndex);
        switch (caseIndex) {
            case 0 : calculatedValue = calculateMean(numbers);
                displayExpression = "Mean of : " + Arrays.toString(numbers); break;
            case 1 : calculatedValue = calculateMedian(numbers);
                displayExpression = "Median of : " + Arrays.toString(numbers); break;
            case 2 : calculatedValue = calculateRange(numbers);
                displayExpression = "Range of : " + Arrays.toString(numbers); break;
            case 3 : calculatedValue = Arrays.toString(calculateMode(numbers).toArray());
                displayExpression = "Mode of : " + Arrays.toString(numbers); break;
        }
        return new String[] {displayExpression, calculatedValue};
    }

    private String calculateMean(int[] numbers) {
        double mean = 0;
        for (int i = 0 ; i < numbers.length; i++) {
            mean += numbers[i];
        }
        mean = mean / numbers.length;
        Log.d(TAG, "Calculated mean = " + decimalFormat.format(mean));
        return decimalFormat.format(mean);
    }

    private String calculateMedian(int[] numbers) {
        int[] numArr = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(numArr);
        Log.d(TAG, "Sorted array = " + Arrays.toString(numArr));
        float median;
        if (numArr.length % 2 == 0) {
            median = (numArr[numArr.length/2] + numArr[numArr.length/2 - 1]) / 2.0f;
        } else {
            //BUG fix for 1.3 version
            median = numArr[(numArr.length - 1)/2];
        }
        Log.d(TAG, "Calculated median = " + decimalFormat.format(median));
        return decimalFormat.format(median);
    }

    private List<Integer> calculateMode(int[] numbers) {
        Map<Integer, Integer> numberMap = new HashMap<>();
        List<Integer> possibleModes = new ArrayList<>();
        for (int i = 0 ; i < numbers.length ; i++) {
            if(numberMap.containsKey(numbers[i])) {
                numberMap.put(numbers[i], numberMap.get(numbers[i]) + 1);
            } else {
                numberMap.put(numbers[i], 1);
            }
        }

        Integer popular = Collections.max(numberMap.entrySet(),
                new Comparator<Map.Entry<Integer, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        return o1.getValue().compareTo(o2.getValue());
                    }
                }).getKey();

        for (Map.Entry<Integer, Integer> item : numberMap.entrySet()) {
            if (item.getValue() == numberMap.get(popular)) {
                possibleModes.add(item.getKey());
            }
        }
        Log.d(TAG, "Calculated modes = " + Arrays.toString(possibleModes.toArray()));
        return possibleModes;
    }

    private String calculateRange(int[] numbers) {
        int[] numArr = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(numArr);
        Log.d(TAG, "Sorted array = " + Arrays.toString(numArr));
        float range = numArr[numArr.length - 1] - numArr[0];
        Log.d(TAG, "Calculated range = " + decimalFormat.format(range));
        return decimalFormat.format(range);
    }
}
