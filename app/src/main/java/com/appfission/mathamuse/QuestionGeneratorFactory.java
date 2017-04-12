package com.appfission.mathamuse;

import android.util.Log;

import com.appfission.utils.ArithmeticQuestion;
import com.appfission.utils.Constants;
import com.appfission.utils.EquationsQuestion;
import com.appfission.utils.ExponentsQuestion;
import com.appfission.utils.FractionsQuestion;
import com.appfission.utils.LogExponentsQuestion;
import com.appfission.utils.MultiplicationsQuestion;
import com.appfission.utils.PercentagesQuestion;
import com.appfission.utils.StatisticsQuestion;
import com.appfission.utils.TrignometryQuestion;
import com.appfission.utils.TrueFalseQuestion;

/**
 * Created by srikanthmannepalle on 2/22/17.
 */

public class QuestionGeneratorFactory {

    private static String TAG = QuestionGeneratorFactory.class.getName();

    public static String[] getQuestion(String categoryName, String difficultyLevel) {

        Log.d(TAG, "Category name passed = " + categoryName);
        if (categoryName.equals(Constants.CATEGORY_ARITHMETIC)) {
            return new ArithmeticQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_LOGARITHM)) {
            return new LogExponentsQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_EXPONENTS)) {
            return new ExponentsQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_PERCENTAGES)) {
            return new PercentagesQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_EQUATIONS)) {
            return new EquationsQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_FRACTIONS)) {
            return new FractionsQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_TRIGONOMETRY)) {
            return new TrignometryQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_MULTIPLICATIONS)) {
            return new MultiplicationsQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_STATISTICS)) {
            return new StatisticsQuestion(difficultyLevel).createQuestion();
        } else if (categoryName.equals(Constants.CATEGORY_TRUEORFALSE)) {
            return new TrueFalseQuestion(difficultyLevel).createQuestion();
        }
        return new String[] {"Question", "Answer"};
    }
}
