package com.appfission.utils;

import com.appfission.mathamuse.GenerateQuestion;

/**
 * Created by srikanthmannepalle on 2/24/17.
 */

public class MultiplicationsQuestion extends GenerateQuestion implements Question {

    public MultiplicationsQuestion(String gameLevel) {
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
        int number1 = (int)Math.round((Math.random()*30));
        int number2 = (int) Math.round((Math.random()*10 + 1));
        String expressionString = number1 + " x " + number2;
        return new String[] {expressionString, String.valueOf(number1*number2)};
    }

    private String[] mediumQuestion() {
        int number1 = (int)Math.round((Math.random()*50));
        int number2 = (int) Math.round((Math.random()*20 + 1));
        String expressionString = number1 + " x " + number2;
        return new String[] {expressionString, String.valueOf(number1*number2)};
    }

    private String[] hardQuestion() {
        int number1 = (int)Math.round((Math.random()*100));
        int number2 = (int) Math.round((Math.random()*20 + 1));
        String expressionString = number1 + " x " + number2;
        return new String[] {expressionString, String.valueOf(number1*number2)};
    }

}
