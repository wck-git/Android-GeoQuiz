package com.bignerdranch.android.geoquiz3;

import java.io.Serializable;

public class Question implements Serializable {
    private int mTextResId;
    private char mCorrectAnswer;
    private String mDifficulty;
    private int[] mAnswerSelections;
    private boolean mHasAnswered = false;

    // EASY mode
    public Question(int textResId, char correctAnswer, String difficulty) {
        mTextResId = textResId;
        mCorrectAnswer = correctAnswer;
        mDifficulty = difficulty;
    }

    // HARD mode
    public Question(int textResId, char correctAnswer, String difficulty, int[] answerSelections) {
        mTextResId = textResId;
        mCorrectAnswer = correctAnswer;
        mDifficulty = difficulty;
        mAnswerSelections = answerSelections;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public char getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public String getDifficulty() { return mDifficulty;}

    public int[] getAnswerSelections() {
        return mAnswerSelections;
    }

    public boolean getHasAnswered() { return mHasAnswered; }

    public void setHasAnswered(boolean hasAnswered) { mHasAnswered = hasAnswered; }
}
