package com.appfission.utils;

import android.util.Log;

import com.appfission.mathamuse.GenerateQuestion;

/**
 * Created by srikanthmannepalle on 2/27/17.
 */

public class TrueFalseQuestion extends GenerateQuestion implements Question {

    private int[] primeNumbersArr = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293,307,311,313,317,331,337,347,349,353,359,367,373,379,383,389,397,401,409,419,421,431,433,439,443,449,457,461,463,467,479,487,491,499,503,509,521,523,541,547,557,563,569,571,577,587,593,599,601,607,613,617,619,631,641,643,647,653,659,661,673,677,683,691,701,709,719,727,733,739,743,751,757,761,769,773,787,797,809,811,821,823,827,829,839,853,857,859,863,877,881,883,887,907,911,919,929,937,941,947,953,967,971,977,983,991,997};
    private String[] comparatorString = {" > " , " = ", " < "};
    private static String TAG = TrueFalseQuestion.class.getName();

    public TrueFalseQuestion(String gameLevel) {
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
        int randomizer = (int)(Math.round(Math.random()*4));
        switch (randomizer) {
            case 0 : return primeNumberQuestions();
            case 1 : return nthPowerQuestions();
            case 2 : return modQuestions();
            case 3 : return evenNumberQuestions();
            case 4 : return oddNumberQuestions();
            default : return null;
        }
    }

    private String[] mediumQuestion() {
        int randomizer = (int)(Math.round(Math.random()*4));
        switch (randomizer) {
            case 0 : return primeNumberQuestions();
            case 1 : return nthPowerQuestions();
            case 2 : return modQuestions();
            case 3 : return evenNumberQuestions();
            case 4 : return oddNumberQuestions();
            default : return null;
        }
    }

    private  String[] hardQuestion() {
        int randomizer = (int)(Math.round(Math.random()*4));
        switch (randomizer) {
            case 0 : return primeNumberQuestions();
            case 1 : return nthPowerQuestions();
            case 2 : return modQuestions();
            case 3 : return evenNumberQuestions();
            case 4 : return oddNumberQuestions();
            default : return null;
        }
    }

    private String[] modQuestions() {
        int num1, num2, exprIndex;
        String[] comparatorString = {" >= ", " > ", " = ", " < ", " < = "};
        String displayString = null, conditionHolds = "false";
        String comparator = comparatorString[(int) Math.round(Math.random() * 4)];
        exprIndex = (int) (Math.round(Math.random() * 3));

        if (difficultyLevel.equals(Constants.EASY)) {
            num1 = (int) (Math.round(Math.random() * 10) + 1);
            do {
                num2 = (int) (Math.round(Math.random() * 10) + 1);
            } while (num2 == 0);

        } else if (difficultyLevel.equals(Constants.MEDIUM)) {
            num1 = (int) (Math.round(Math.random() * 10) - 5);
            do {
                num2 = (int) (Math.round(Math.random() * 10) - 5);
            } while(num2 == 0);
        } else {
            num1 = (int) (Math.round(Math.random() * 20) - 10);
            do {
                num2 = (int) (Math.round(Math.random() * 20) - 10);
            } while(num2 == 0);
        }

        switch (exprIndex) {
            case 0:
                displayString = "|(" + num1 + " ) x (" + num2 + ")|" + comparator + "|" + num1 + "|" + " x " + "|" + num2 + "|";
                break;
            case 1:
                displayString = "|(" + num1 + ") / (" + num2 + ")|" + comparator + "|" + num1 + "|" + " / " + "|" + num2 + "|";
                break;
            case 2:
                displayString = "|(" + num1 + ") - (" + num2 + ")|" + comparator + "|" + num1 + "|" + " - " + "|" + num2 + "|";
                break;
            case 3:
                displayString = "|(" + num1 + ") + (" + num2 + ")|" + comparator + "|" + num1 + "|" + " + " + "|" + num2 + "|";
                break;
            default:
                Log.d(TAG, "Invalid condition got hit");
        }

        Log.d(TAG, "displayString = " + displayString);

        if ((comparator.equals(" = ") || comparator.equals(" >= ") || comparator.equals(" < = ")) && (exprIndex == 0 || exprIndex == 1)) {
            conditionHolds = "true";
        } else {
            if (exprIndex == 2) {
                int val1 = Math.abs(num1 - num2);
                int val2 = Math.abs(num1) - Math.abs(num2);
                boolean flag1 = false, flag2 = false, flag3 = false, flag5 = false;
                if (val1 == val2) {
                    flag3 = true;
                }
                else if (val1 > val2) {
                    flag2 = true;
                }
                if (val1 >= val2) {
                    flag1 = true;
                }
                if (val1 <= val2) {
                    flag5 = true;
                }
                if ((flag3 && comparator.equals(" = ")) || (flag2 && comparator.equals(" > ")) || (flag1 && comparator.equals(" >= ")) || (flag5 && comparator.equals(" < = "))) {
                    conditionHolds = "true";
                }

            } else if (exprIndex == 3) {
                int val1 = Math.abs(num1 + num2);
                int val2 = Math.abs(num1) + Math.abs(num2);
                boolean flag5 = false, flag4 = false, flag3 = false, flag1 = false;
                if (val1 == val2) {
                    flag3 = true;
                }
                else if (val1 < val2) {
                    flag4 = true;
                }
                if (val1 <= val2) {
                    flag5 = true;
                }
                if (val1 >= val2) {
                    flag1 = true;
                }

                if ((flag3 && comparator.equals(" = ")) || (flag4 && comparator.equals(" < ")) || (flag5 && comparator.equals(" < = ")) || (flag1 && comparator.equals(" >= "))) {
                    conditionHolds = "true";
                }
            }
        }
        Log.d(TAG, "Calculated answer = " + conditionHolds);
        return new String[]{displayString, conditionHolds};
    }

    private String[] nthPowerQuestions() {
        int power1 = (int)(Math.round(Math.random()*3 + 1));
        int power2 = (int)(Math.round(Math.random()*3 + 1));
        int base1, base2;
        String conditionHolds = "false";
        String comparator = comparatorString [(int)Math.round(Math.random()*2)];

        if (difficultyLevel.equals(Constants.EASY)) {
             base1 = (int)(Math.round(Math.random()*3) + 1);
             base2 = (int)(Math.round(Math.random()*3) + 1);
        } else if (difficultyLevel.equals(Constants.MEDIUM)) {
             base1 = (int)(Math.round(Math.random()*3) + 3);
             base2 = (int)(Math.round(Math.random()*3) + 3);
        } else {
             base1 = (int)(Math.round(Math.random()*4) + 4);
             base2 = (int)(Math.round(Math.random()*4) + 4);
        }
        String displayString = "("+base1 + ")<sup><small>" + "1/" + power1 + "</small></sup> " + comparator +
                " ("+base2 + ")<sup><small>" + "1/" + power2 + "</small></sup>";
        Log.d(TAG, "Math.pow(base1, 1f/power1) = " + Math.pow(base1, 1f/power1));
        Log.d(TAG, "Math.pow(base2, 1f/power2) = " + Math.pow(base2, 1f/power2));

        if (comparator.equals(" = ") && (Math.pow(base1, 1f/power1) == Math.pow(base2, 1f/power2))) {
            conditionHolds = "true";
        } else if (comparator.equals(" > ") && (Math.pow(base1, 1f/power1) > Math.pow(base2, 1f/power2))) {
            conditionHolds = "true";
        } else if (comparator.equals(" < ") && (Math.pow(base1, 1f/power1) < Math.pow(base2, 1f/power2))) {
            conditionHolds = "true";
        }
        return new String[] { displayString, conditionHolds};
    }

    private String[] primeNumberQuestions() {
        int index, randomNumber;
        if (difficultyLevel.equals(Constants.EASY)) {
            randomNumber = (int)(Math.round(Math.random()*100));
            index = (int)(Math.round(Math.random()*15));
        } else if (difficultyLevel.equals(Constants.MEDIUM)) {
            randomNumber = (int)(Math.round(Math.random()*500));
            index  = (int)(Math.round(Math.random()*70 + 15));
        } else {
            randomNumber = (int)(Math.round(Math.random()*1000));
            index = (int)(Math.round(Math.random()*80 + 85));
        }
        String displayString = randomNumber + " is a prime number";
        String isPrime = "false";
        for (int i = 0 ; i < primeNumbersArr.length; i++) {
            if (primeNumbersArr[i] == randomNumber) {
                isPrime = "true";
                break;
            }
        }
        return new String[] {displayString, isPrime};
    }

    private String[] evenNumberQuestions() {
        int index, randomNumber;
        if (difficultyLevel.equals(Constants.EASY)) {
            randomNumber = (int)(Math.round(Math.random()*100));
            index = (int)(Math.round(Math.random()*15));
        } else if (difficultyLevel.equals(Constants.MEDIUM)) {
            randomNumber = (int)(Math.round(Math.random()*500));
            index  = (int)(Math.round(Math.random()*70 + 15));
        } else {
            randomNumber = (int)(Math.round(Math.random()*1000));
            index = (int)(Math.round(Math.random()*80 + 85));
        }
        String displayString = randomNumber + " is an even number";
        String isEven = "false";
        if (randomNumber % 2 == 0) {
                isEven = "true";
            }
        return new String[] {displayString, isEven};
    }

    private String[] oddNumberQuestions() {
        int index, randomNumber;
        if (difficultyLevel.equals(Constants.EASY)) {
            randomNumber = (int)(Math.round(Math.random()*100));
            index = (int)(Math.round(Math.random()*15));
        } else if (difficultyLevel.equals(Constants.MEDIUM)) {
            randomNumber = (int)(Math.round(Math.random()*500));
            index  = (int)(Math.round(Math.random()*70 + 15));
        } else {
            randomNumber = (int)(Math.round(Math.random()*1000));
            index = (int)(Math.round(Math.random()*80 + 85));
        }
        String displayString = randomNumber + " is an odd number";
        String isOdd = "false";
        if (randomNumber % 2 != 0) {
            isOdd = "true";
        }
        return new String[] {displayString, isOdd};
    }
}
