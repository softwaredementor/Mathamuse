package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by srikanthmannepalle on 2/26/17.
 */

public class PercentagesQuestion extends GenerateQuestion implements Question {

    private static DecimalFormat decimalFormat = new DecimalFormat("##.00");
    public PercentagesQuestion(String gameLevel) {
        difficultyLevel = gameLevel;
    }
    private static String TAG = PercentagesQuestion.class.getName();

    @Override
    public String[] createQuestion() {
        switch (difficultyLevel) {
            case "Easy":
                return easyQuestion();
            case "Medium":
                return mediumQuestion();
            case "Hard":
                return hardQuestion();
            default:
                return null;
        }
    }

    private static String[] easyQuestion() {
        Log.d(TAG, "####EASY QUESTION####");
        int[] percentArray = new int[] {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        int[] numbersArray = new int[] { 5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
        return generatePercentageQuestion(percentArray, numbersArray);
    }

    private static String[] mediumQuestion() {
        int[] percentArray = new int[] {1, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        int[] numbersArray = new int[] { 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
        return generatePercentageQuestion(percentArray, numbersArray);
    }

    private static String[] hardQuestion() {
        int[] percentArray = new int[] {1, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
        int[] numbersArray = new int[] { 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
        return generatePercentageQuestion(percentArray, numbersArray);      }

    private static String[] generatePercentageQuestion(int[] percentArray, int[] numbersArray) {
        int operand1 = numbersArray[(int) (Math.random() * (numbersArray.length - 1))];
        int operand2 = numbersArray[(int) (Math.random() * (numbersArray.length - 1))];
        int percentage1 = percentArray[(int)(Math.round(Math.random()*(percentArray.length - 1)))];
        int percentage2 = percentArray[(int)(Math.round(Math.random()*(percentArray.length - 1)))];
        int index = (int) (Math.random() * 4);

        String operator = generateMultipleOperators(1)[0];
        String displayString, expressionString;

        if (operator.equals("/")) {
            int[] factorsList = findFactors(operand1);
            operand2 = factorsList[(int) (Math.random() * (factorsList.length - 1))];
            displayString = "(" + percentage1 + "% of " + operand1 + ") / (" + percentage2 + "% of " + operand2 + ")";
            expressionString = (((percentage1*operand1)/100f) + " / " + ((percentage2*operand2)/100f));
        } else {
            displayString = "(" + percentage1 + "% of " + operand1 + ") " +operator + " (" + percentage2 + "% of " + operand2 + ")";
            expressionString = (((percentage1*operand1)/100f) + " " + operator + " " + ((percentage2*operand2)/100f));
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString);
        Expression expression = new Expression(expressionString);
        value = expression.setPrecision(7).eval();
        Log.d(TAG, "Calculated value = " + decimalFormat.format(Double.parseDouble(value.toPlainString())));
        return new String[]{displayString, decimalFormat.format(Double.parseDouble(value.toPlainString()))};
    }
}
