package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by srikanthmannepalle on 2/26/17.
 */

public class ExponentsQuestion extends GenerateQuestion implements Question {

    protected static Map<Integer, int[]> easyPowerAndIndexValues = new HashMap<>();
    protected static Map<Integer, int[]> mediumPowerAndIndexValues = new HashMap<>();
    protected static Map<Integer, int[]> hardPowerAndIndexValues = new HashMap<>();
    private static Random randomInteger;
    private static String TAG = ExponentsQuestion.class.getName();

    public ExponentsQuestion(String gameLevel) {
        difficultyLevel = gameLevel;
        randomInteger = new Random();

        easyPowerAndIndexValues.put(1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        easyPowerAndIndexValues.put(2, new int[]{0, 1, 2, 3});
        easyPowerAndIndexValues.put(3, new int[]{0, 1, 2, 3});
        easyPowerAndIndexValues.put(4, new int[]{0, 1, 2, 3});
        easyPowerAndIndexValues.put(5, new int[]{0, 1, 2, 3});
        easyPowerAndIndexValues.put(6, new int[]{0, 1, 2});
        easyPowerAndIndexValues.put(7, new int[]{0, 1, 2});
        easyPowerAndIndexValues.put(8, new int[]{0, 1, 2});
        easyPowerAndIndexValues.put(9, new int[]{0, 1, 2});
        easyPowerAndIndexValues.put(10, new int[]{0, 1, 2});

        mediumPowerAndIndexValues.put(1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        mediumPowerAndIndexValues.put(2, new int[]{0, 1, 2, 3, 4});
        mediumPowerAndIndexValues.put(3, new int[]{0, 1, 2, 3, 4});
        mediumPowerAndIndexValues.put(4, new int[]{0, 1, 2, 3, 4});
        mediumPowerAndIndexValues.put(5, new int[]{0, 1, 2, 3});
        mediumPowerAndIndexValues.put(6, new int[]{0, 1, 2});
        mediumPowerAndIndexValues.put(7, new int[]{0, 1, 2});
        mediumPowerAndIndexValues.put(8, new int[]{0, 1, 2});
        mediumPowerAndIndexValues.put(9, new int[]{0, 1, 2});
        mediumPowerAndIndexValues.put(10, new int[]{0, 1, 2});

        hardPowerAndIndexValues.put(1, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        hardPowerAndIndexValues.put(2, new int[]{0, 1, 2, 3, 4, 5, 6});
        hardPowerAndIndexValues.put(3, new int[]{0, 1, 2, 3, 4});
        hardPowerAndIndexValues.put(4, new int[]{0, 1, 2, 3, 4});
        hardPowerAndIndexValues.put(5, new int[]{0, 1, 2, 3, 4});
        hardPowerAndIndexValues.put(6, new int[]{0, 1, 2, 3});
        hardPowerAndIndexValues.put(7, new int[]{0, 1, 2, 3});
        hardPowerAndIndexValues.put(8, new int[]{0, 1, 2, 3});
        hardPowerAndIndexValues.put(9, new int[]{0, 1, 2, 3});
        hardPowerAndIndexValues.put(10, new int[]{0, 1, 2, 3});
    }

    @Override
    public String[] createQuestion() {
        switch (this.difficultyLevel) {
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
        Log.d(TAG, "#### " + difficultyLevel.toUpperCase()+ " QUESTION####");
        int[] easyPowerNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int baseIndex1, baseIndex2;
        int baseIndex1Power, baseIndex2Power;
        int operand1, operand2;

        baseIndex1 = easyPowerNumbers[randomInteger.nextInt(easyPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex1 = " + baseIndex1);
        int[] baseIndex1Values = easyPowerAndIndexValues.get(baseIndex1);
        int arrIndexForBaseIndex1Values = (int) Math.round(Math.random()*(baseIndex1Values.length - 1));
        baseIndex1Power = baseIndex1Values[arrIndexForBaseIndex1Values];
        operand1 = (int) Math.pow(baseIndex1, baseIndex1Power);
        Log.d(TAG, "Operand1 = " + baseIndex1 + "^" + baseIndex1Power + ")");

        baseIndex2 = easyPowerNumbers[randomInteger.nextInt(easyPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex2 = " + baseIndex2);
        int[] baseIndex2Values = easyPowerAndIndexValues.get(baseIndex2);
        int arrIndexForBaseIndex2Values = (int) Math.round(Math.random()*(baseIndex2Values.length - 1));
        baseIndex2Power = baseIndex2Values[arrIndexForBaseIndex2Values];
        operand2 = (int) Math.pow(baseIndex2, baseIndex2Power);
        Log.d(TAG, "Operand2 = " + baseIndex2 + "^" + baseIndex2Power + ")");

        int index = (int) (Math.random() * 4);
        String operator = generateMultipleOperators(1)[0];
        String expressionString;
        StringBuilder displayString;

        if (operator.equals("/")) {
            int[] factorsList = findExponentFactors(baseIndex1, baseIndex1Power);
            operand2 = (int) (Math.pow (factorsList[0], factorsList[1]));
            displayString = new StringBuilder().append("(" + baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup>" + " / " +
                    factorsList[0] + "<sup><small>" + factorsList[1] + "</small></sup>)");
            expressionString = "(" + operand1 + " / " + operand2 + ")";
        } else {
            displayString = new StringBuilder().append(baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup> " + operator +
                    " " + baseIndex2 + "<sup><small>" + baseIndex2Power + "</small></sup>");
            expressionString = (operand1 + " " + operator + " " + operand2);
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString);
        Expression expression = new Expression(operand1 + operator + operand2);
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + value.toPlainString());
        return new String[]{displayString.toString(), String.valueOf(value.toPlainString())};
    }

    private static String[] mediumQuestion() {
        Log.d(TAG, "#### " + difficultyLevel.toUpperCase()+ " QUESTION####");
        int[] mediumPowerNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int baseIndex1, baseIndex2, baseIndex3;
        int baseIndex1Power, baseIndex2Power, baseIndex3Power;
        int operand1, operand2, operand3;

        baseIndex1 = mediumPowerNumbers[randomInteger.nextInt(mediumPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex1 = " + baseIndex1);
        int[] baseIndex1Values = mediumPowerAndIndexValues.get(baseIndex1);
        int arrIndexForBaseIndex1Values = (int) Math.round(Math.random()*(baseIndex1Values.length - 1));
        baseIndex1Power = baseIndex1Values[arrIndexForBaseIndex1Values];
        operand1 = (int) Math.pow(baseIndex1, baseIndex1Power);
        Log.d(TAG, "Operand1 = " + baseIndex1 + "^" + baseIndex1Power + ")");

        baseIndex2 = mediumPowerNumbers[randomInteger.nextInt(mediumPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex2 = " + baseIndex2);
        int[] baseIndex2Values = mediumPowerAndIndexValues.get(baseIndex2);
        int arrIndexForBaseIndex2Values = (int) Math.round(Math.random()*(baseIndex2Values.length - 1));
        baseIndex2Power = baseIndex2Values[arrIndexForBaseIndex2Values];
        operand2 = (int) Math.pow(baseIndex2, baseIndex2Power);
        Log.d(TAG, "Operand2 = " + baseIndex2 + "^" + baseIndex2Power + ")");

        baseIndex3 = mediumPowerNumbers[randomInteger.nextInt(mediumPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex3 = " + baseIndex3);
        int[] baseIndex3Values = mediumPowerAndIndexValues.get(baseIndex3);
        int arrIndexForBaseIndex3Values = (int) Math.round(Math.random()*(baseIndex3Values.length - 1));
        baseIndex3Power = baseIndex3Values[arrIndexForBaseIndex3Values];
        operand3 = (int) Math.pow(baseIndex3, baseIndex3Power);
        Log.d(TAG, "Operand3 = " + baseIndex3 + "^" + baseIndex3Power + ")");

        int index = (int) (Math.random() * 4);
        String[] operator = generateMultipleOperators(2);
        String operator1 = operator[0];
        String operator2 = operator[1];

        StringBuilder displayString, expressionString;

        if (operator1.equals("/")) {
            int[] factorsList = findExponentFactors(baseIndex1, baseIndex1Power);
            baseIndex2 = factorsList[0];
            baseIndex2Power = factorsList[1];
            operand2 = (int) (Math.pow (baseIndex2, baseIndex2Power));
            displayString = new StringBuilder().append("(" + baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup>" + " / " +
                    baseIndex2 + "<sup><small>" + baseIndex2Power + "</small></sup>" + ")");
            expressionString = new StringBuilder().append("((" + operand1 + " / " + operand2 + ")");
        } else {
            displayString = new StringBuilder().append(baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup> " + operator1 +
                    " " + baseIndex2 + "<sup><small>" + baseIndex2Power + "</small></sup>");
            expressionString = new StringBuilder().append("("+ operand1 + " " + operator1 + " " + operand2);
        }

        if (operator2.equals("/")) {
            int[] factorsList = findExponentFactors(baseIndex2, baseIndex2Power);
            operand3 = (int) (Math.pow (factorsList[0], factorsList[1]));
            baseIndex3 = factorsList[0];
            baseIndex3Power = factorsList[1];
            displayString = new StringBuilder().append("(" + baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup> " + operator1 +
                    " (" + baseIndex2 + "<sup><small>" + baseIndex2Power + "</small></sup>" + " / " + baseIndex3 + "<sup><small>" + baseIndex3Power + "</small></sup> ))");
            expressionString = new StringBuilder("(" + operand1 + " " + operator1 + " (" + operand2).append(" / " + operand3 + "))");
        } else {
            displayString.append(" " + operator2 + " " + baseIndex3 + "<sup><small>" + baseIndex3Power + "</small></sup>");
            expressionString.append(" " + operator2 + " " + operand3 + ")");
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString.toString());
        Expression expression = new Expression(expressionString.toString());
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + value.toPlainString());
        return new String[]{displayString.toString(), String.valueOf(value.toPlainString())};
    }

    private static String[] hardQuestion() {
        Log.d(TAG, "#### " + difficultyLevel.toUpperCase()+ " QUESTION####");
        int[] hardPowerNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int baseIndex1, baseIndex2, baseIndex3;
        int baseIndex1Power, baseIndex2Power, baseIndex3Power;
        int operand1, operand2, operand3;

        baseIndex1 = hardPowerNumbers[randomInteger.nextInt(hardPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex1 = " + baseIndex1);
        int[] baseIndex1Values = hardPowerAndIndexValues.get(baseIndex1);
        int arrIndexForBaseIndex1Values = (int) Math.round(Math.random()*(baseIndex1Values.length - 1));
        baseIndex1Power = baseIndex1Values[arrIndexForBaseIndex1Values];
        operand1 = (int) Math.pow(baseIndex1, baseIndex1Power);
        Log.d(TAG, "Operand1 = " + baseIndex1 + "^" + baseIndex1Power + ")");

        baseIndex2 = hardPowerNumbers[randomInteger.nextInt(hardPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex2 = " + baseIndex2);
        int[] baseIndex2Values = hardPowerAndIndexValues.get(baseIndex2);
        int arrIndexForBaseIndex2Values = (int) Math.round(Math.random()*(baseIndex2Values.length - 1));
        baseIndex2Power = baseIndex2Values[arrIndexForBaseIndex2Values];
        operand2 = (int) Math.pow(baseIndex2, baseIndex2Power);
        Log.d(TAG, "Operand2 = " + baseIndex2 + "^" + baseIndex2Power + ")");

        baseIndex3 = hardPowerNumbers[randomInteger.nextInt(hardPowerNumbers.length - 1)];
        Log.d(TAG, "baseIndex3 = " + baseIndex3);
        int[] baseIndex3Values = hardPowerAndIndexValues.get(baseIndex3);
        int arrIndexForBaseIndex3Values = (int) Math.round(Math.random()*(baseIndex3Values.length - 1));
        baseIndex3Power = baseIndex3Values[arrIndexForBaseIndex3Values];
        operand3 = (int) Math.pow(baseIndex3, baseIndex3Power);
        Log.d(TAG, "Operand3 = " + baseIndex3 + "^" + baseIndex3Power + ")");

        int index = (int) (Math.random() * 4);
        String[] operator = generateMultipleOperators(2);
        String operator1 = operator[0];
        String operator2 = operator[1];

        StringBuilder displayString, expressionString;

        if (operator1.equals("/")) {
            int[] factorsList = findExponentFactors(baseIndex1, baseIndex1Power);
            baseIndex2 = factorsList[0];
            baseIndex2Power = factorsList[1];
            operand2 = (int) (Math.pow (baseIndex2, baseIndex2Power));
            displayString = new StringBuilder().append("(" + baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup>" + " / " +
                    baseIndex2 + "<sup><small>" + baseIndex2Power + "</small></sup>" + ")");
            expressionString = new StringBuilder().append("((" + operand1 + " / " + operand2 + ")");
        } else {
            displayString = new StringBuilder().append(baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup> " + operator1 +
                    " " + baseIndex2 + "<sup><small>" + baseIndex2Power + "</small></sup>");
            expressionString = new StringBuilder().append("("+ operand1 + " " + operator1 + " " + operand2);
        }

        if (operator2.equals("/")) {
            int[] factorsList = findExponentFactors(baseIndex2, baseIndex2Power);
            operand3 = (int) (Math.pow (factorsList[0], factorsList[1]));
            baseIndex3 = factorsList[0];
            baseIndex3Power = factorsList[1];
            displayString = new StringBuilder().append("(" + baseIndex1 + "<sup><small>" + baseIndex1Power + "</small></sup> " + operator1 +
                    " (" + baseIndex2 + "<sup><small>" + baseIndex2Power + "</small></sup>" + " / " + baseIndex3 + "<sup><small>" + baseIndex3Power + "</small></sup> ))");
            expressionString = new StringBuilder("(" + operand1 + " " + operator1 + " (" + operand2).append(" / " + operand3 + "))");
        } else {
            displayString.append(" " + operator2 + " " + baseIndex3 + "<sup><small>" + baseIndex3Power + "</small></sup>");
            expressionString.append(" " + operator2 + " " + operand3 + ")");
        }

        BigDecimal value = BigDecimal.valueOf(0);
        Log.d(TAG, "Expression = " + expressionString.toString());
        Expression expression = new Expression(expressionString.toString());
        value = expression.eval();
        Log.d(TAG, "Calculated value = " + value.toPlainString());
        return new String[]{displayString.toString(), String.valueOf(value.toPlainString())};
    }

    protected static int[] findExponentFactors(final int aNumber, final int power) {
        Log.d(TAG, "Numerator number = " + aNumber + " and power = " + power);
        Map<Integer, int[]> factorsMap = null;
        switch (difficultyLevel) {
            case Constants.EASY:
                factorsMap = easyPowerAndIndexValues;
                break;
            case Constants.MEDIUM:
                factorsMap = mediumPowerAndIndexValues;
                break;
            case Constants.HARD:
                factorsMap = hardPowerAndIndexValues;
                break;
        }

        for (Map.Entry<Integer, int[]> possibleFactor : factorsMap.entrySet()) {
            boolean isCompatible = ((possibleFactor.getKey() - aNumber) % 2 == 0 ? true : false);
            if (isCompatible) {
                int factor = possibleFactor.getKey();
                int[] values = factorsMap.get(factor);
                for (int i = values.length - 1; i >=0 ; i--) {
                    if (Math.pow(aNumber, power) % Math.pow(factor, values[i]) == 0) {
                        int requiredPower = possibleFactor.getValue()[i];
                        return new int[] {factor, requiredPower};
                    }
                }
            }
        }
        return null;
    }
}

