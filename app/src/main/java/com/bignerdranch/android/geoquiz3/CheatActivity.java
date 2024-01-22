package com.bignerdranch.android.geoquiz3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz3.answer_shown";
    private static final String EXTRA_CORRECT_ANSWER = "com.bignerdranch.android.geoquiz3.answer_is_true";
    private static final String EXTRA_CURRENT_QUESTION = "com.bignerdranch.android.geoquiz3.current_question";
    private final Map<Character, Integer> mEasyModeCorrectAnswerMap = new HashMap<Character, Integer>() {{
        put('A', R.string.true_button);
        put('B', R.string.false_button);
    }};
    private final Map<Character, Integer> mHardModeCorrectAnswerIndexMap = new HashMap<Character, Integer>() {{
        put('A', 0);
        put('B', 1);
        put('C', 2);
        put('D', 3);
    }};
    private boolean mIsAnswerShown = false;
    private char mCorrectAnswer;
    private Question mCurrentQuestion;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, char correctAnswer, Question currentQuestion) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_CORRECT_ANSWER, correctAnswer);
        intent.putExtra(EXTRA_CURRENT_QUESTION, currentQuestion);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mIsAnswerShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
            setAnswerShownResult(mIsAnswerShown);
        }

        // retrieve data
        mCorrectAnswer = getIntent().getCharExtra(EXTRA_CORRECT_ANSWER, 'E');
        mCurrentQuestion = (Question) getIntent().getSerializableExtra(EXTRA_CURRENT_QUESTION);

        // Answer Text View
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        // Show Answer Button
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
                setAnswerShown();
                setAnswerShownResult(mIsAnswerShown);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

        private void showAnswer() {
        // Easy Mode
        if (mCurrentQuestion.getDifficulty().equals(QuestionBank.EASY)) {
            // loops the hashmap and find the the correct answer
            for (Map.Entry<Character, Integer> easyCorrectAnswerIndexMap : mEasyModeCorrectAnswerMap.entrySet()) {
                if (mCorrectAnswer == easyCorrectAnswerIndexMap.getKey()) {
                    mAnswerTextView.setText(easyCorrectAnswerIndexMap.getValue());
                }
            }
        }
        // Hard Mode
        else if (mCurrentQuestion.getDifficulty().equals(QuestionBank.HARD)) {
            // loops the hashmap, find the respective index (key's value) for the correct answer and display it
            for (Map.Entry<Character, Integer> hardCorrectAnswerIndexMap : mHardModeCorrectAnswerIndexMap.entrySet()) {
                if (mCorrectAnswer == hardCorrectAnswerIndexMap.getKey()) {
                    mAnswerTextView.setText(mCurrentQuestion.getAnswerSelections()[hardCorrectAnswerIndexMap.getValue()]);
                }
            }
        }
    }

    private void setAnswerShown() {
        mIsAnswerShown = true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(EXTRA_ANSWER_SHOWN, mIsAnswerShown);
    }
}