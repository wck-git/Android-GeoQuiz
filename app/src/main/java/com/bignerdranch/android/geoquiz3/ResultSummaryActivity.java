package com.bignerdranch.android.geoquiz3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultSummaryActivity extends AppCompatActivity {
    private static final String EXTRA_TOTAL_NUM_QUESTIONS = "com.bignerdranch.android.geoquiz3.total_questions";
    private static final String EXTRA_ANSWERED_COUNTER = "com.bignerdranch.android.geoquiz3.answered_counter";
    private static final String EXTRA_SCORE_RESULT = "com.bignerdranch.android.geoquiz3.score_result";
    private static final String EXTRA_CHEAT_COUNTER = "com.bignerdranch.android.geoquiz3.cheat_counter";
    private int mTotalNumQuestions;
    private int mAnsweredCounter;
    private int mScoreResult;
    private int mCheatCounter;

    private TextView mAnsweredCounterTextView;
    private TextView mScoreCounterTextView;
    private TextView mCheatCounterTextView;

    public static Intent newIntent(Context packageContext, int totalNumQuestions, int answeredCounter, int scoreResult, int cheatCounter) {
        Intent intent = new Intent(packageContext, ResultSummaryActivity.class);
        intent.putExtra(EXTRA_TOTAL_NUM_QUESTIONS, totalNumQuestions);
        intent.putExtra(EXTRA_ANSWERED_COUNTER, answeredCounter);
        intent.putExtra(EXTRA_SCORE_RESULT, scoreResult);
        intent.putExtra(EXTRA_CHEAT_COUNTER, cheatCounter);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_summary);

        // Text View
        mAnsweredCounterTextView = (TextView) findViewById(R.id.question_answered_text_view);
        mScoreCounterTextView = (TextView) findViewById(R.id.score_text_view);
        mCheatCounterTextView = (TextView) findViewById(R.id.cheat_attempts_text_view);

        // Retrieve data
        mTotalNumQuestions = getIntent().getIntExtra(EXTRA_TOTAL_NUM_QUESTIONS, 0);
        mAnsweredCounter = getIntent().getIntExtra(EXTRA_ANSWERED_COUNTER, 0);
        mScoreResult = getIntent().getIntExtra(EXTRA_SCORE_RESULT, 0);
        mCheatCounter = getIntent().getIntExtra(EXTRA_CHEAT_COUNTER, 0);

        // Set text
        mAnsweredCounterTextView.setText(String.format(getResources().getString(R.string.question_answered_text), mAnsweredCounter, mTotalNumQuestions));
        mScoreCounterTextView.setText(String.format(getResources().getString(R.string.score_text), mScoreResult));
        mCheatCounterTextView.setText(String.format(getResources().getString(R.string.cheat_attempts_text), mCheatCounter));

    }
}
