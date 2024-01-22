package com.bignerdranch.android.geoquiz3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class HardQuizActivity extends AppCompatActivity {
    private static final String TAG = "HardQuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String HAS_SHUFFLED = "has shuffled";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final int TOTAL_CHEAT_ATTEMPT = 3;
    private final int TOTAL_NUM_QUESTIONS = HardQuestionBankController.getQuestions().length;
    private static int mScoreResult = 0;
    private static int mAnsweredCounter = 0;
    private static int mCheatCounter = 0;
    private static Map<Integer, Boolean> mCheatingQuestionMap = new HashMap<Integer, Boolean>();
    private boolean mHasShuffled = false;
    private int mCurrentIndex = 0;
    private boolean mIsCheaterForThisQuestion = false;

    private Button mAButton;
    private Button mBButton;
    private Button mCButton;
    private Button mDButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    private Button mResetButton;
    private Button mResultSummaryButton;
    private Toast mQuizResultToast;
    private TextView mQuestionHeaderTextView;
    private TextView mQuestionTextView;
    private TextView mRemainingCheatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_hard_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mHasShuffled = savedInstanceState.getBoolean(HAS_SHUFFLED, false);
        }

        // Text View
        mQuestionHeaderTextView = (TextView) findViewById(R.id.question_header_text_view);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mRemainingCheatTextView = (TextView) findViewById(R.id.remaining_cheat_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateNextQuestion();
            }
        });

        // Answer Selections button
        mAButton = (Button) findViewById(R.id.A_button);
        mBButton= (Button) findViewById(R.id.B_button);
        mCButton= (Button) findViewById(R.id.C_button);
        mDButton= (Button) findViewById(R.id.D_button);

        mAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer('A');
                displayResult();
            }
        });
        mBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer('B');
                displayResult();
            }
        });
        mCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer('C');
                displayResult();
            }
        });
        mDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer('D');
                displayResult();
            }
        });

        // NEXT button
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateNextQuestion();
            }
        });

        // PREVIOUS button
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigatePrevQuestion();
            }
        });

        // CHEAT button
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char correctAnswer = HardQuestionBankController.getQuestions()[mCurrentIndex].getCorrectAnswer();
                Question currentQuestion = HardQuestionBankController.getQuestions()[mCurrentIndex];
                Intent intent = CheatActivity.newIntent(HardQuizActivity.this, correctAnswer, currentQuestion);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        // RESET button
        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        // RESULT SUMMARY button
        mResultSummaryButton = (Button) findViewById(R.id.result_summary_button);
        mResultSummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ResultSummaryActivity.newIntent(HardQuizActivity.this, TOTAL_NUM_QUESTIONS, mAnsweredCounter, mScoreResult, mCheatCounter);
                startActivity(intent);
            }
        });

        updateQuestionHeader();
        shuffleQuestions();
        updateQuestion();
        updatePrevButtonVisibility();
        updateAnswerButtonsVisibility();
        updateAnswerSelections();
        updateRemainingCheatText();
        updateCheatSystemVisibility();
    }

    private void updateQuestionHeader() {
        String questionHeader = String.format(getResources().getString(R.string.question_header), (mCurrentIndex + 1), TOTAL_NUM_QUESTIONS);
        mQuestionHeaderTextView.setText(questionHeader);
    }

    private void updateQuestion() {
        int question = HardQuestionBankController.getQuestions()[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void updateRemainingCheatText() {
        String remainingCheat = String.format(getResources().getString(R.string.remaining_cheat_text), (TOTAL_CHEAT_ATTEMPT - mCheatCounter));
        mRemainingCheatTextView.setText(remainingCheat);
    }

    private void updatePrevButtonVisibility() {
        if (mCurrentIndex == 0) { mPreviousButton.setVisibility(View.INVISIBLE); }
        else { mPreviousButton.setVisibility(View.VISIBLE); }
    }

    private void updateAnswerButtonsVisibility() {
        if (HardQuestionBankController.getQuestions()[mCurrentIndex].getHasAnswered()) {
            mAButton.setVisibility(View.INVISIBLE);
            mBButton.setVisibility(View.INVISIBLE);
            mCButton.setVisibility(View.INVISIBLE);
            mDButton.setVisibility(View.INVISIBLE);
        }
        else {
            mAButton.setVisibility(View.VISIBLE);
            mBButton.setVisibility(View.VISIBLE);
            mCButton.setVisibility(View.VISIBLE);
            mDButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateCheatSystemVisibility() {
        mIsCheaterForThisQuestion = checkIsCheaterThisQuestion();
        if (mCheatCounter == TOTAL_CHEAT_ATTEMPT || mIsCheaterForThisQuestion || HardQuestionBankController.getQuestions()[mCurrentIndex].getHasAnswered()) {
            mCheatButton.setEnabled(false);
        }
        else {
            mCheatButton.setEnabled(true);
        }
    }

    private void updateAnswerSelections() {
        mAButton.setText(HardQuestionBankController.getQuestions()[mCurrentIndex].getAnswerSelections()[0]);
        mBButton.setText(HardQuestionBankController.getQuestions()[mCurrentIndex].getAnswerSelections()[1]);
        mCButton.setText(HardQuestionBankController.getQuestions()[mCurrentIndex].getAnswerSelections()[2]);
        mDButton.setText(HardQuestionBankController.getQuestions()[mCurrentIndex].getAnswerSelections()[3]);
    }

    private void checkAnswer(char userPressedTrue) {
        char correctAnswer = (HardQuestionBankController.getQuestions())[mCurrentIndex].getCorrectAnswer();
        int messageResId = 0;

        // get the boolean value of the user cheated for this question
        mIsCheaterForThisQuestion = checkIsCheaterThisQuestion();
        if (mIsCheaterForThisQuestion) {
            messageResId = R.string.judgement_toast;
        }
        else {
            if (userPressedTrue == correctAnswer) {
                messageResId = R.string.correct_toast;
                mScoreResult++;
            }
            else {
                messageResId = R.string.incorrect_toast;
            }
        }
        mAnsweredCounter++;

        HardQuestionBankController.getQuestions()[mCurrentIndex].setHasAnswered(true);
        updateAnswerButtonsVisibility();
        updateCheatSystemVisibility();

        // displays the results of the question in TOAST
        mQuizResultToast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        mQuizResultToast.setGravity(Gravity.CENTER, 0, 0);
        mQuizResultToast.show();
    }

    private void navigateQuestions() {
        updateQuestionHeader();
        updateQuestion();
        updateAnswerButtonsVisibility();
        updateAnswerSelections();
        updatePrevButtonVisibility();
        updateRemainingCheatText();
        updateCheatSystemVisibility();
    }

    private void navigateNextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % HardQuestionBankController.getQuestions().length;
        navigateQuestions();
    }

    private void navigatePrevQuestion() {
        mCurrentIndex = (mCurrentIndex - 1) % HardQuestionBankController.getQuestions().length;
        navigateQuestions();
    }

    private void displayResult() {
        if (mAnsweredCounter == TOTAL_NUM_QUESTIONS) {
            Toast.makeText(this, "Score: " + ((mScoreResult*100)/TOTAL_NUM_QUESTIONS) + "%", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset() {
        mCurrentIndex = 0;
        mScoreResult = 0;
        mAnsweredCounter = 0;
        mCheatCounter = 0;
        mHasShuffled = false;
        mCheatingQuestionMap.clear();

        HardQuestionBankController.resetHasAnswered();
        updateQuestionHeader();
        shuffleQuestions();
        updateQuestion();
        updateAnswerButtonsVisibility();
        updatePrevButtonVisibility();
        updateAnswerSelections();
        updateRemainingCheatText();
        updateCheatSystemVisibility();
        Toast.makeText(this, R.string.reset_toast, Toast.LENGTH_SHORT).show();
    }

    private void shuffleQuestions() {
        // ONLY shuffles when the user has not answered and cheated
        // DOES NOT shuffle when the app is rotated
        if (mAnsweredCounter == 0 && !mHasShuffled && mCheatCounter == 0) {
            HardQuestionBankController.shuffleQuestions();
            mHasShuffled = true;
        }
    }

    private boolean checkIsCheaterThisQuestion() {
        // checks if the question has been cheated by the user
        // display the "cheating is wrong!" toast if the user has cheated on the questions
        for (Map.Entry<Integer, Boolean> cheatingQuestionMap : mCheatingQuestionMap.entrySet()) {
            if (mCurrentIndex == cheatingQuestionMap.getKey() && cheatingQuestionMap.getValue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(HAS_SHUFFLED, mHasShuffled);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheaterForThisQuestion = CheatActivity.wasAnswerShown(data);
            mCheatCounter++;
            mCheatingQuestionMap.put(mCurrentIndex, mIsCheaterForThisQuestion);
            updateRemainingCheatText();
            updateCheatSystemVisibility();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}