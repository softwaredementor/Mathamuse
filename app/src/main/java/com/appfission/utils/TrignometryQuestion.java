package com.appfission.utils;

import android.util.Log;

import java.util.Random;

/**
 * Created by srikanthmannepalle on 2/24/17.
 */

public class TrignometryQuestion extends TrignometryBaseQuestion implements Question {

    //sinA, cosA, tanA, cosecA, secA, cotA
    //sin(A+B) = sinACosB + sinBCosA
    //sin2A = 2sinAcosA
    //cos(A+B) = cosACosB - sinAsinB
    //cos2A = cosA^2 = sin2A^2
    //tan(A+B) = (1 - tanAtanB) / (tan A + tan B)
    //tan2A = ( 1- tanA^2) / 2tanA

    private static Random randomInteger;
    private static String TAG = TrignometryQuestion.class.getName();

    public TrignometryQuestion(String gameLevel) {
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

    private String[] easyQuestion() {
        Log.d(TAG, "####EASY QUESTION####");
        int angle = (anglesSet[(int) (Math.random()* (anglesSet.length - 1))]);
        Log.d(TAG, "Angle = " + angle);

        int quesTypeIndex = (int) Math.round((Math.random()*5));
        switch(quesTypeIndex) {
            case 0 : return getSineQuestion(angle);
            case 1 : return getCosQuestion(angle);
            case 2 : return getTanQuestion(angle);
            case 3 : return getCosecQuestion(angle);
            case 4 : return getSecQuestion(angle);
            case 5 : return getCotQuestion(angle);
            default : throw new RuntimeException("Unsupported easy trigo question type");
        }
    }

    private String[] mediumQuestion() {
        Log.d(TAG, "####MEDIUM QUESTION####");
        int angle = (anglesSet[(int) (Math.random()* (anglesSet.length - 1))]);
        Log.d(TAG, "Angle = " + angle);
        String[] operators =  {"+", "-"};
        String[] output1 = getOneQuestion(angle);
        String[] output2 = getOneQuestion(angle);
        String displayString, expressionEval, operator, value;
        operator = operators[(int)(Math.round(Math.random()*1))];
        displayString = output1[0] + " " + operator + " " + output2[0];
        expressionEval = output1[1] + " " + operator + " " + output2[1];
        value = new Expression(expressionEval).eval().toPlainString();
        return new String[] {displayString, value};
    }

    private String[] hardQuestion() {
        Log.d(TAG, "####HARD QUESTION####");
        int angle = (anglesSet[(int) (Math.random()* (anglesSet.length - 1))]);
        Log.d(TAG, "Angle = " + angle);
        String[] operators =  {"+", "-"};
        String[] output1 = getOneQuestion(angle);
        String[] output2 = getOneQuestion(angle);
        while (output1[0].contains("cosec") && output2[0].contains("cosec")) {
            output2 = getOneQuestion(angle);
        }
        int num = (int)(Math.round(Math.random()*10));

        String displayString, expressionEval, operator1, operator2, value;
        operator1 = operators[(int)(Math.round(Math.random()*1))];
        operator2 = operators[(int)(Math.round(Math.random()*1))];

        displayString = output1[0] + " " + operator1 + " " + output2[0] + " " + operator2 + " " + num;
        expressionEval = output1[1] + " " + operator1 + " " + output2[1] + " " + operator2  + " " + num;
        value = new Expression(expressionEval).eval().toPlainString();
        return new String[] {displayString, value};
    }

    private String[] getOneQuestion(int angle) {
        int quesTypeIndex = (int) Math.round((Math.random()*5));
        String[] output = null;
            switch (quesTypeIndex) {
                case 0:
                    output = getSineQuestion(angle); break;
                case 1:
                    output = getCosQuestion(angle); break;
                case 2:
                    output = getTanQuestion(angle); break;
                case 3:
                    output = getCosecQuestion(angle); break;
                case 4:
                    output = getSecQuestion(angle); break;
                case 5:
                    output = getCotQuestion(angle); break;
                default:
                    throw new RuntimeException("Unsupported easy trigo question type");
            }
        return output;
    }

    private String[] getSineQuestion(int angle) {
        String displayString = "sin(" + angle + "°)";
        Log.d(TAG, "Calculated value before formatting = " + new Expression(sinMap.get(angle)).setPrecision(4).eval().toPlainString());
        return new String[] {displayString, new Expression(sinMap.get(angle)).setPrecision(4).eval().toPlainString()};
    }

    private String[] getCosecQuestion(int angle) {
        if (angle == 0 || angle == 180 || angle == 360) {
            angle -= 30;
        }
        String displayString = "cosec(" + angle + "°)";
        Log.d(TAG, "Calculated value before formatting = " + new Expression(cosecMap.get(angle)).setPrecision(4).eval().toPlainString());
        return new String[] {displayString, new Expression(cosecMap.get(angle)).setPrecision(4).eval().toPlainString()};
    }

    private String[] getCosQuestion(int angle) {
        String displayString = "cos(" + angle + "°)";
        Log.d(TAG, "Calculated value before formatting = " + new Expression(cosMap.get(angle)).setPrecision(4).eval().toPlainString());
        return new String[] {displayString, new Expression(cosMap.get(angle)).setPrecision(4).eval().toPlainString()};
    }

    private String[] getSecQuestion(int angle) {
        if (angle == 90 || angle == 270) {
            angle -= 30;
        }
        String displayString = "sec(" + angle + "°)";
        Log.d(TAG, "Calculated value before formatting = " + new Expression(secMap.get(angle)).setPrecision(4).eval().toPlainString());
        return new String[] {displayString, new Expression(secMap.get(angle)).setPrecision(4).eval().toPlainString()};
    }

    private String[] getTanQuestion(int angle) {
        if (angle == 90 || angle == 270) {
            angle -= 30;
        }
        String displayString = "tan(" + angle + "°)";
        Log.d(TAG, "Calculated value before formatting = " + new Expression(tanMap.get(angle)).setPrecision(4).eval().toPlainString());
        return new String[] {displayString, new Expression(tanMap.get(angle)).setPrecision(4).eval().toPlainString()};
    }

    private String[] getCotQuestion(int angle) {
        if (angle == 0 || angle == 180 || angle == 360) {
            angle -= 30;
        }
        String displayString = "cot(" + angle + "°)";
        Log.d(TAG, "Calculated value before formatting = " + new Expression(cotMap.get(angle)).setPrecision(4).eval().toPlainString());
        return new String[] {displayString, new Expression(cotMap.get(angle)).setPrecision(4).eval().toPlainString()};
    }
}
