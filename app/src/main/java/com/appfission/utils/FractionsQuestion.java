package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by srikanthmannepalle on 2/27/17.
 */

public class FractionsQuestion extends GenerateQuestion implements Question {

    private static Random randomInteger;
    private static String TAG = FractionsQuestion.class.getName();
    public FractionsQuestion(String gameLevel) {
        difficultyLevel = gameLevel;
    }

    @Override
    public String[] createQuestion() {
        switch (difficultyLevel) {
            case Constants.EASY:
                return easyQuestion();
            case Constants.MEDIUM:
                return mediumQuestion();
            case Constants.HARD:
                return hardQuestion();
            default:
                return null;
        }
    }

    //perfect divisions upto 100
    private String[] easyQuestion() {
      return generateFractionQuestion(20);
    }

    //2 perfect divsions and 1 can be anything upto 100
    private String[] mediumQuestion() {
        return generateFractionQuestion(50);
    }

    //all 3 can be any fraction upto 100
    private String[] hardQuestion() {
        return generateFractionQuestion(100);
    }

    private String[] generateFractionQuestion(int maxVal) {
        Log.d(TAG, "####" + difficultyLevel + " QUESTION####");
        int num1Operand = (int) (Math.random()*maxVal + 1);
        int num2Operand = (int) (Math.random()*maxVal + 1);
        int num3Operand = (int) (Math.random()*maxVal + 1);
        String[] operatorsArray = generateMultipleOperators(2, true);

        String operator1 = operatorsArray[0];
        String operator2 = operatorsArray[1];

        int[] factorsList1 = findFactors(num1Operand);
        int den1Operand = factorsList1[(int) (Math.random() * (factorsList1.length - 1))];
        int[] factorsList2 = findFactors(num2Operand);
        int den2Operand = factorsList2[(int) (Math.random() * (factorsList2.length - 1))];
        int[] factorsList3 = findFactors(num3Operand);
        int den3Operand = factorsList3[(int) (Math.random() * (factorsList3.length - 1))];

        StringBuilder expressionString = new StringBuilder("(" + num1Operand + "/" + den1Operand + ")")
                .append(" " + operator1 + " ")
                .append("(" + num2Operand + "/" + den2Operand + ")")
                .append(" " + operator2 + " ")
                .append("(" + num3Operand + "/" + den3Operand + ")");

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString);
        Expression expression = new Expression(expressionString.toString());
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + value.toPlainString());
        return new String[]{expressionString.toString(), String.valueOf(value.toPlainString())};
    }
}
