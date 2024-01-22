package com.bignerdranch.android.geoquiz3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HardQuestionBankController {
    private static Question[] mHardQuestions = setQuestions();

    private static Question[] setQuestions() {
        // retrieve all questions
        QuestionBank mQuestions = new QuestionBank();
        // temporary arraylist because of the dynamic size collection to store the EASY questions
        ArrayList<Question> mHardQuestionsTemp = new ArrayList<>();
        for (int i = 0; i < mQuestions.getQuestions().length; i++) {
            if (mQuestions.getQuestions()[i].getDifficulty().equals(QuestionBank.HARD)) {
                mHardQuestionsTemp.add(mQuestions.getQuestions()[i]);
            }
        }
        // convert the array list to array and return
        return mHardQuestionsTemp.toArray(new Question[mHardQuestionsTemp.size()]);
    }

    public static Question[] getQuestions() {
        return mHardQuestions;
    }

    public static void resetHasAnswered() {
        // set all of the questions' property of hasAnswered to FALSE
        for (int index = 0; index < mHardQuestions.length; index++) {
            mHardQuestions[index].setHasAnswered(false);
        }
    }

    public static void shuffleQuestions() {
        // convert the array to list for shuffling and return as array
        List<Question> list = Arrays.asList(mHardQuestions);
        Collections.shuffle(list);
        list.toArray(mHardQuestions);
    }
}