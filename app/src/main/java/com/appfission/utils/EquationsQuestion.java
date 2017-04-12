package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

import java.math.BigDecimal;

/**
 * Created by srikanthmannepalle on 2/27/17.
 */

public class EquationsQuestion extends GenerateQuestion implements Question {

    public EquationsQuestion(String gameLevel) {
        difficultyLevel = gameLevel;
    }
    private static String TAG = EquationsQuestion.class.getName();

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
      return generateEquationsQuestions(20);
    }

    private String[] mediumQuestion() {
        return generateEquationsQuestions(50);
    }

    private String[] hardQuestion() {
        return generateEquationsQuestions(100);
    }

    private String[] generateEquationsQuestions(int maxVal) {
        int tempX, X;
        Log.d(TAG, "####" + difficultyLevel+ " QUESTION####");
        int operand1 = (int) (Math.random() * maxVal + 1);
        int operand2 = (int) (Math.random() * maxVal + 1);
        int operand3 = (int) (Math.random() * maxVal + 1);

        //manipulate X now
        int randomXIndex = (int)(Math.random()*3);
        switch (randomXIndex) {
            case 0 : X = operand1; tempX = operand1; break;
            case 1 : X = operand2; tempX = operand2; break;
            case 2 : X = operand3; tempX = operand3; break;
            default: X = operand2; tempX = operand2; break;
        }

        String[] operatorsArray = generateMultipleOperators(2);
        String operator1 = operatorsArray[0];
        String operator2 = operatorsArray[1];

        StringBuilder displayString = new StringBuilder("Solve X : ");
        StringBuilder expressionString = new StringBuilder();

        if (operator1.equals("/")) {
            int[] factorsList = findFactors(operand1);
            operand2 = factorsList[(int) (Math.random() * (factorsList.length - 1))];
            if (X == tempX) {
                X = operand2;
            }
            if (X == operand1) {
                displayString.append("(" + "X" + " / " + operand2 + ")");
            } else if (X == operand2) {
                displayString.append("(" + operand1 + " / " + "X" + ")");
            } else {
                displayString.append("(" + operand1 + " / " + operand2 + ")");
            }
            expressionString.append("(" + operand1 + " / " + operand2 + ")");
        } else {
            if (X == operand1) {
                displayString.append("X" + " " + operator1 + " " + operand2);
            } else if (X == operand2) {
                displayString.append(operand1 + " " + operator1 + " " + "X");
            } else {
                displayString.append(operand1 + " " + operator1 + " " + operand2);
            }
            expressionString.append(operand1 + " " + operator1 + " " + operand2);
        }

        if (operator2.equals("/")) {
            int[] factorsList = findFactors(operand2);
            operand3 = factorsList[(int) (Math.random() * (factorsList.length - 1))];
            if (X == tempX) {
                X = operand3;
            }
            //insert parantheses just before 2nd operand
            if (X == operand1) {
                displayString = new StringBuilder("Solve X : ").
                        append("X" + " " + operator1 + " (" + operand2);
                displayString.append(" / " + operand3 + ")");
            } else if (X == operand2) {
                displayString = new StringBuilder("Solve X : ").
                        append(operand1 + " " + operator1 + " (" + "X");
                displayString.append(" / " + operand3 + ")");
            } else if (X == operand3){
                displayString = new StringBuilder("Solve X : ").append(operand1 + " " + operator1 + " (" + operand2);
                displayString.append(" / " + "X" + ")");
            }

            expressionString = new StringBuilder(operand1 + " " + operator1 + " (" + operand2);
            expressionString.append(" / " + operand3 + ")");
        } else {
            if (X == operand3) {
                displayString.append(" " + operator2 + " " + "X");
            } else {
                displayString.append(" " + operator2 + " " + operand3);
            }
            expressionString.append(" " + operator2 + " " + operand3);
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString);
        Expression expression = new Expression(expressionString.toString());
        value = expression.eval();
        Log.d(TAG, "Calculated X = " + X);

        displayString.append(" = " + value.toPlainString());

        return new String[]{displayString.toString(), String.valueOf(X)};
    }
}
