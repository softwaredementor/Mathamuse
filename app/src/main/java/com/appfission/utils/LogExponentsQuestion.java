package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by srikanthmannepalle on 2/24/17.
 */

public class LogExponentsQuestion extends GenerateQuestion implements Question {

    protected static Map<Integer, int[]> easyBaseAndIndexValues = new HashMap<>();
    protected static Map<Integer, int[]> mediumBaseAndIndexValues = new HashMap<>();
    protected static Map<Integer, int[]> hardBaseAndIndexValues = new HashMap<>();
    private static String TAG = LogExponentsQuestion.class.getName();
    private static Random randomInteger;

    public LogExponentsQuestion(String gameLevel) {
        randomInteger = new Random();
        difficultyLevel = gameLevel;

        easyBaseAndIndexValues.put(2, new int[]{1, 2, 3, 4, 5, 6});
        easyBaseAndIndexValues.put(3, new int[]{0, 1, 2, 3, 4});
        easyBaseAndIndexValues.put(4, new int[]{1, 2, 3});
        easyBaseAndIndexValues.put(5, new int[]{1, 2, 3});
        easyBaseAndIndexValues.put(6, new int[]{1, 2, 3});
        easyBaseAndIndexValues.put(7, new int[]{0, 1, 2});
        easyBaseAndIndexValues.put(8, new int[]{1, 2});
        easyBaseAndIndexValues.put(9, new int[]{1, 2});
        easyBaseAndIndexValues.put(10, new int[]{0, 1, 2});
        easyBaseAndIndexValues.put(11, new int[]{0, 1});
        easyBaseAndIndexValues.put(12, new int[]{0, 1});
        easyBaseAndIndexValues.put(13, new int[]{0, 1});
        easyBaseAndIndexValues.put(14, new int[]{0, 1});
        easyBaseAndIndexValues.put(15, new int[]{0, 1});

        mediumBaseAndIndexValues.put(2, new int[]{1, 5, 6, 7, 8, 9});
        mediumBaseAndIndexValues.put(3, new int[]{1, 4, 5, 6});
        mediumBaseAndIndexValues.put(4, new int[]{1, 3, 4, 5});
        mediumBaseAndIndexValues.put(5, new int[]{1, 3 ,4});
        mediumBaseAndIndexValues.put(6, new int[]{1, 3, 4});
        mediumBaseAndIndexValues.put(7, new int[]{0, 1, 2, 3});
        mediumBaseAndIndexValues.put(8, new int[]{0, 1, 2, 3});
        mediumBaseAndIndexValues.put(9, new int[]{0, 1, 2, 3});
        mediumBaseAndIndexValues.put(10, new int[]{0, 1, 2, 3});
        mediumBaseAndIndexValues.put(11, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(12, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(13, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(14, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(15, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(16, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(17, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(18, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(19, new int[]{0, 1, 2});
        mediumBaseAndIndexValues.put(20, new int[]{0, 1, 2});

        hardBaseAndIndexValues.put(2, new int[]{1, 7, 8, 9, 10, 11, 12, 13});
        hardBaseAndIndexValues.put(3, new int[]{1, 5, 6, 7, 8});
        hardBaseAndIndexValues.put(4, new int[]{1, 4, 5, 6});
        hardBaseAndIndexValues.put(5, new int[]{1, 4, 5});
        hardBaseAndIndexValues.put(6, new int[]{1, 3, 4, 5});
        hardBaseAndIndexValues.put(7, new int[]{1, 3, 4});
        hardBaseAndIndexValues.put(8, new int[]{1, 3, 4});
        hardBaseAndIndexValues.put(9, new int[]{1, 3 ,4});
        hardBaseAndIndexValues.put(10, new int[]{0, 1, 3, 4});
        hardBaseAndIndexValues.put(11, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(12, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(13, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(14, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(15, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(16, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(17, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(18, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(19, new int[]{0, 1, 2, 3});
        hardBaseAndIndexValues.put(20, new int[]{0, 1, 2, 3});
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

    private static String[] easyQuestion() {
        Log.d(TAG, "####EASY QUESTION####");
        int[] easyBaseNumbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        int baseIndex1, baseIndex2;
        int operand1, operand2;

        baseIndex1 = easyBaseNumbers[randomInteger.nextInt(easyBaseNumbers.length - 1)];
        Log.d(TAG, "baseIndex1 = " + baseIndex1);
        int[] baseIndex1Values = easyBaseAndIndexValues.get(baseIndex1);
        int arrIndexForBaseIndex1Values = randomInteger.nextInt(baseIndex1Values.length - 1);

        operand1 = (int) Math.pow(baseIndex1, baseIndex1Values[arrIndexForBaseIndex1Values]);
        Log.d(TAG, "Operand1 = log" + baseIndex1 + "(" + operand1 + ")");

        baseIndex2 = easyBaseNumbers[randomInteger.nextInt(easyBaseNumbers.length - 1)];
        Log.d(TAG, "baseIndex2 = " + baseIndex2);
        int[] baseIndex2Values = easyBaseAndIndexValues.get(baseIndex2);
        int arrIndexForBaseIndex2Values = randomInteger.nextInt(baseIndex2Values.length - 1);

        operand2 = (int) Math.pow(baseIndex2, baseIndex2Values[arrIndexForBaseIndex2Values]);
        Log.d(TAG, "Operand2 = log" + baseIndex2 + "(" + operand2 + ")");

        int index = (int) (Math.random() * 4);
        String operator = generateMultipleOperators(1)[0];
        String displayExpressionString, expressionString;

        if (operator.equals("/")) {
            //check the log1/log2 is an integer do re-adjustments for log2
            int counter = 0;
            boolean isFactorFound = false;
            do {
                for (int i = baseIndex2Values.length - 1; i >= 0; i--) {
                    if (baseIndex2Values[i] > 0 && (baseIndex1Values[arrIndexForBaseIndex1Values] % baseIndex2Values[i]) == 0) {
                        counter = i;
                        isFactorFound = true;
                        break; //found the divisor
                    }
                }
            } while (!isFactorFound);
            operand2 = (int) Math.pow(baseIndex2, baseIndex2Values[counter]);
            Log.d(TAG, "Operand2 = log" + baseIndex2 + "(" + operand2 + ")");

            //extra starting-ending parenthesis are added for '/' symbol !
            displayExpressionString = "(log<sub><small>" + baseIndex1 + "</small></sub>" + operand1
                    + " " + operator + " log<sub><small>" + baseIndex2 + "</small></sub>" + operand2+")";
            expressionString = "((log10" + "(" + operand1 + ")" +
                    " / " + "log10" + "(" + baseIndex1 + "))" + operator +
                    "(log10" + "(" + operand2 + ")" +
                    " / " + "log10" + "(" + baseIndex2 + ")))";
        }

        else {
            displayExpressionString = "log<sub><small>" + baseIndex1 + "</small></sub>" + operand1 +
                    " " + operator + " log<sub><small>" + baseIndex2 + "</small></sub>" + operand2;
            expressionString = "(log10" + "(" + operand1 + ")" +
                    " / " + "log10" + "(" + baseIndex1 + "))" + operator +
                    "(log10" + "(" + operand2 + ")" +
                    " / " + "log10" + "(" + baseIndex2 + "))";
        }


        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression used for calculation = " + expressionString);
        Expression expression = new Expression(expressionString);
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + Math.round(Float.parseFloat(String.valueOf(value.toPlainString()))));
        return new String[]{displayExpressionString, Math.round(Float.parseFloat(String.valueOf(value.toPlainString()))) + ""};
    }

    private static String[] mediumQuestion() {
        return mediumHardLevelBaseFunction(Constants.MEDIUM);
    }

    private static String[] hardQuestion() {
        return mediumHardLevelBaseFunction(Constants.HARD);
    }

    private static String[] mediumHardLevelBaseFunction(String level) {
        int[] mediumHardBaseNumbers = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        int baseIndex1, baseIndex2, baseIndex3;
        int operand1, operand2, operand3;

        Map<Integer, int[]> levelledMap = null;

        if (level.equals(Constants.MEDIUM)) {
            levelledMap = mediumBaseAndIndexValues;
        } else if (level.equals(Constants.HARD)) {
            levelledMap = hardBaseAndIndexValues;
        }

        baseIndex1 = mediumHardBaseNumbers[randomInteger.nextInt(mediumHardBaseNumbers.length - 1)];
        Log.d(TAG, "baseIndex1 = " + baseIndex1);
        int[] baseIndex1Values = levelledMap.get(baseIndex1);
        int arrIndexForBaseIndex1Values = randomInteger.nextInt(baseIndex1Values.length - 1);

        operand1 = (int) Math.pow(baseIndex1, baseIndex1Values[arrIndexForBaseIndex1Values]);
        Log.d(TAG, "Operand1 = log" + baseIndex1 + "(" + operand1 + ")");

        baseIndex2 = mediumHardBaseNumbers[randomInteger.nextInt(mediumHardBaseNumbers.length - 1)];
        Log.d(TAG, "baseIndex2 = " + baseIndex2);
        int[] baseIndex2Values = levelledMap.get(baseIndex2);
        int arrIndexForBaseIndex2Values = randomInteger.nextInt(baseIndex2Values.length - 1);

        operand2 = (int) Math.pow(baseIndex2, baseIndex2Values[arrIndexForBaseIndex2Values]);
        Log.d(TAG, "Operand2 = log" + baseIndex2 + "(" + operand2 + ")");

        int index = (int) (Math.random() * 4);

        String[] operatorsArray = generateMultipleOperators(2);
        String operator1 = operatorsArray[0];
        String operator2 = operatorsArray[1];

        StringBuilder displayExpressionString = new StringBuilder();
        StringBuilder expressionString = new StringBuilder();

        if (operator1.equals("/")) {
            //check the log1/log2 is an integer do re-adjustments for log2
            int counter = 0;
            boolean isFactorFound = false;
            do {
                for (int i = baseIndex2Values.length - 1; i >= 0; i--) {
                    if (baseIndex2Values[i] > 0 && (baseIndex1Values[arrIndexForBaseIndex1Values] % baseIndex2Values[i]) == 0) {
                        counter = i;
                        isFactorFound = true;
                        break; //found the divisor
                    }
                }
            } while (!isFactorFound);

            operand2 = (int) Math.pow(baseIndex2, baseIndex2Values[counter]);
            Log.d(TAG, "Operand2 = log" + baseIndex2 + "(" + operand2 + ")");

            //extra starting-ending parenthesis are added for '/' symbol !
            displayExpressionString.append("(log<sub><small>" + baseIndex1 + "</small></sub>" + operand1 +
                    " " + operator1 + " log<sub><small>" + baseIndex2 + "</small></sub>" + operand2+")");
            expressionString.append("((log10" + "(" + operand1 + ")" +
                    " / " + "log10" + "(" + baseIndex1 + "))" + operator1 +
                    "(log10" + "(" + operand2 + ")" +
                    " / " + "log10" + "(" + baseIndex2 + ")))");
        }

        else {
            displayExpressionString.append("log<sub><small>" + baseIndex1 + "</small></sub>" + operand1 +
                    " " + operator1 + " log<sub><small>" + baseIndex2 + "</small></sub>" + operand2);
            expressionString.append("(log10" + "(" + operand1 + ")" +
                    " / " + "log10" + "(" + baseIndex1 + "))" + operator1 +
                    "(log10" + "(" + operand2 + ")" +
                    " / " + "log10" + "(" + baseIndex2 + "))");
        }

        //handle second operator now..whew !
        baseIndex3 = mediumHardBaseNumbers[randomInteger.nextInt(mediumHardBaseNumbers.length - 1)];
        Log.d(TAG, "baseIndex3 = " + baseIndex3);
        int[] baseIndex3Values = levelledMap.get(baseIndex3);
        int arrIndexForBaseIndex3Values = randomInteger.nextInt(baseIndex3Values.length - 1);

        operand3 = (int) Math.pow(baseIndex3, baseIndex3Values[arrIndexForBaseIndex3Values]);
        Log.d(TAG, "Operand3 = log" + baseIndex3 + "(" + operand3 + ")");


        if (operator2.equals("/")) {
            //check the log1/log2 is an integer do re-adjustments for log2
            int counter = 0;
            boolean isFactorFound = false;
            do {
                for (int i = baseIndex3Values.length - 1; i >= 0; i--) {
                    if (baseIndex3Values[i] > 0 && (baseIndex2Values[arrIndexForBaseIndex2Values] % baseIndex3Values[i]) == 0) {
                        counter = i;
                        isFactorFound = true;
                        break; //found the divisor
                    }
                }
            } while (!isFactorFound);

            operand3 = (int) Math.pow(baseIndex3, baseIndex3Values[counter]);
            Log.d(TAG, "Operand3 = log" + baseIndex3 + "(" + operand3 + ")");

            //extra starting-ending parenthesis are added for '/' symbol !
            //alter "(" bracket
            displayExpressionString = new StringBuilder("log<sub><small>" + baseIndex1 + "</small></sub>" + operand1 +
            " " + operator1 + " (log<sub><small>" + baseIndex2 + "</small></sub>" + operand2).append(" / log<sub><small>" + baseIndex3 + "</small></sub>" + operand3 + ")");
            //alter "(" bracket
            expressionString = new StringBuilder("(log10" + "(" + operand1 + ")" +
            " / " + "log10" + "(" + baseIndex1 + "))" + operator1 +
                    "((log10" + "(" + operand2 + ")" + " / " + "log10" + "(" + baseIndex2 + "))")
                    .append("/(log10" + "(" + operand3 + ")" + "/" + "log10" + "(" + baseIndex3 + ")))");
        }

        else {
            displayExpressionString.append(" " + operator2 + " log<sub><small>" + baseIndex3 + "</small></sub>" + operand3);
            expressionString.append(operator2 + "(log10" + "(" + operand3 + ")" + "/" + "log10" + "(" + baseIndex3 + "))");
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression used for calculation = " + expressionString);
        Expression expression = new Expression(expressionString.toString());
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + Math.round(Float.parseFloat(String.valueOf(value.toPlainString()))));
        return new String[]{displayExpressionString.toString(), Math.round(Float.parseFloat(String.valueOf(value.toPlainString()))) + ""};
    }
}
