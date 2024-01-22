package com.bignerdranch.android.geoquiz3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mEasyQuizButton;
    private Button mHardQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEasyQuizButton = (Button) findViewById(R.id.easy_button);
        mEasyQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateEasyQuiz();
            }
        });

        mHardQuizButton = (Button) findViewById(R.id.hard_button);
        mHardQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateHardQuiz();
            }
        });
    }

    public void navigateEasyQuiz() {
        startActivity(new Intent(MainActivity.this, EasyQuizActivity.class));
    }

    public void navigateHardQuiz() {
        startActivity(new Intent(MainActivity.this, HardQuizActivity.class));
    }
}
