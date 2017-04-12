package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

import java.math.BigDecimal;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public class ArithmeticQuestion extends GenerateQuestion implements Question {

    public ArithmeticQuestion(String gameLevel) {
        difficultyLevel = gameLevel;
    }
    private static String TAG = ArithmeticQuestion.class.getName();

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

    private static String[] easyQuestion() {
        Log.d(TAG, "####EASY QUESTION####");
        int operand1 = (int) (Math.random() * 25 + 1);
        int operand2 = (int) (Math.random() * 25 + 1);
        String operator = generateMultipleOperators(1)[0];
        String expressionString;

        if (operator.equals("/")) {
            int[] factorsList = findFactors(operand1);
            operand2 = factorsList[(int) (Math.random() * (factorsList.length - 1))];
            expressionString = ("(" + operand1 + " / " + operand2 + ")");
        } else {
            expressionString = (operand1 + " " + operator + " " + operand2);
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString);
        Expression expression = new Expression(operand1 + operator + operand2);
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + value.toPlainString());
        return new String[]{expressionString, String.valueOf(value.toPlainString())};
    }

    private static String[] mediumQuestion() {
        return mediumHardLevelBaseFunction("medium", 30);
    }

    private static String[] hardQuestion() {
        return mediumHardLevelBaseFunction(Constants.HARD, 100);
    }

    private static String[] mediumHardLevelBaseFunction(String level, int maxVal) {
        Log.d(TAG, "#### " + level.toUpperCase()+ " QUESTION####");
        int operand1 = (int) (Math.random() * maxVal + 1);
        int operand2 = (int) (Math.random() * maxVal + 1);
        int operand3 = (int) (Math.random() * maxVal + 1);

        String[] operatorsArray = generateMultipleOperators(2);
        String operator1 = operatorsArray[0];
        String operator2 = operatorsArray[1];

        StringBuilder expressionString = new StringBuilder();

        if (operator1.equals("/")) {
            int[] factorsList = findFactors(operand1);
            operand2 = factorsList[(int) (Math.random() * (factorsList.length - 1))];
            expressionString.append("(" + operand1 + " / " + operand2 + ")");
        } else {
            expressionString.append(operand1 + " " + operator1 + " " + operand2);
        }

        if (operator2.equals("/")) {
            int[] factorsList = findFactors(operand2);
            operand3 = factorsList[(int) (Math.random() * (factorsList.length - 1))];

            //insert parantheses just before 2nd operand
            expressionString = new StringBuilder(operand1 + " " + operator1 + " (" + operand2);
            expressionString.append(" / " + operand3 + ")");
        } else {
            expressionString.append(" " + operator2 + " " + operand3);
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString);
        Expression expression = new Expression(expressionString.toString());
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + value.toPlainString());
        return new String[]{expressionString.toString(), String.valueOf(value.toPlainString())};
    }
}
