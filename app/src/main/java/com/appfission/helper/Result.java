package com.appfission.helper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srikanthmannepalle on 3/5/17.
 */

public class Result implements Parcelable {

    public String mQuestionNumber;
    public String mNameOfImage;
    public String mCorrectAnswer;
    public String mYourAnswer;

    public Result() {

    }

    public Result(String mQuestionNumber, String mNameOfImage, String mCorrectAnswer, String mYourAnswer) {
        this.mQuestionNumber = mQuestionNumber;
        this.mNameOfImage = mNameOfImage;
        this.mCorrectAnswer = mCorrectAnswer;
        this.mYourAnswer = mYourAnswer;
    }

    public String getmQuestionNumber() {
        return mQuestionNumber;
    }

    public void setmQuestionNumber(String mQuestionNumber) {
        this.mQuestionNumber = mQuestionNumber;
    }

    public String getmNameOfImage() {
        return mNameOfImage;
    }

    public void setmNameOfImage(String mNameOfImage) {
        this.mNameOfImage = mNameOfImage;
    }

    public String getmCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setmCorrectAnswer(String mCorrectAnswer) {
        this.mCorrectAnswer = mCorrectAnswer;
    }

    public String getmYourAnswer() {
        return mYourAnswer;
    }

    public void setmYourAnswer(String mYourAnswer) {
        this.mYourAnswer = mYourAnswer;
    }

    @SuppressWarnings("unused")
    public Result(Parcel in) {
        this();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.mQuestionNumber = in.readString();
        this.mNameOfImage = in.readString();
        this.mCorrectAnswer = in.readString();
        this.mYourAnswer = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final static Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestionNumber);
        dest.writeString(mNameOfImage);
        dest.writeString(mCorrectAnswer);
        dest.writeString(mYourAnswer);
    }
}
