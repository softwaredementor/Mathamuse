package com.appfission.mathamuse;

import android.util.Log;

import com.appfission.utils.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public abstract class GenerateQuestion {
    // if easy : + , -, *, / and 2 operands only ( -20 to 20 for +/- )
    // if medium : +, -, *, / and 3 operands ( -20 to 20 for +/- )
    // if hard : +, -, *, / and 3 operands ( -100 to 100 for +/- )
    // TODO : if survival : +, -, *, / and 4 operands ( -100 to 100 for +/- )
    protected static String difficultyLevel;
    private static String TAG = GenerateQuestion.class.getName();
    public GenerateQuestion() {

    }

    protected static Operator operators[] = new Operator[] {Operator.PLUS, Operator.MINUS,
            Operator.MULTIPLIER, Operator.DIVIDER};

    protected static int[] findFactors(int aNumber) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 1; i <= aNumber/2; i++) {
            if (aNumber % i == 0) {
                factors.add(i);
            }
        }
        factors.add(aNumber);
        int[] intFactors = new int[factors.size()];
        for (int j = 0; j < intFactors.length; j++) {
            intFactors[j] = factors.get(j);
        }
        Log.d(TAG, "Original number = " + aNumber +" " +
                "\nList of factors are : " + Arrays.toString(intFactors));
        return intFactors;
    }

    protected static String[] generateMultipleOperators(int count) {
        return generateMultipleOperators(count, false);
    }

    protected static String[] generateMultipleOperators(int count, boolean isDivisionSkipped) {
        String[] operatorsArray = new String[count];
        List<String> operatorsList = new ArrayList<>();
        do {
            int index = (int) (Math.random() * 4);
            String operator = operators[index].getDisplayValue();
            Log.d(TAG, "operator = " + operator);

            if ((operator.equals("+") || operator.equals("-")) || !operatorsList.contains(operator)) {
                operatorsList.add(operator);
            }

            if (isDivisionSkipped) {
                if (operatorsList.contains("/")) {
                    operatorsList.remove("/");
                }
            }
        } while (operatorsList.size() < count);

        for (String x : operatorsList) {
            Log.d(TAG, x + " ");
        }
        Log.d(TAG, "Size = " + operatorsList.size());
        return operatorsList.toArray(operatorsArray);
    }
}
