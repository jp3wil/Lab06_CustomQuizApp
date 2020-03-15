package com.wiluszjp.lab06_customquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    TextView header, previous, question;
    EditText writeAns;
    RadioGroup fourAns, twoAns;
    RadioButton ansOne, ansTwo, ansThree, ansFour, ansYes, ansNo;
    Button proceedButton;
    SharedPreferences sharedPreferences;
    Resources res;
    String[] responses;
    int questionNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        header = findViewById(R.id.header);
        previous = findViewById(R.id.prevResps);
        question = findViewById(R.id.questionBox);
        writeAns = findViewById(R.id.writeResponse);
        fourAns = findViewById(R.id.fourResponse);
        ansOne = findViewById(R.id.check1);
        ansTwo = findViewById(R.id.check2);
        ansThree = findViewById(R.id.check3);
        ansFour = findViewById(R.id.check4);
        twoAns = findViewById(R.id.twoResponse);
        ansYes = findViewById(R.id.trueResp);
        ansNo = findViewById(R.id.falseResp);
        proceedButton = findViewById(R.id.proceedButton);
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        res = getResources();

        questionNo = 0;
        header.setText(res.getString(R.string.app_name));
        previous.setText(sharedPreferences.getString("previousResponsesGSON", ""));
        question.setText(res.getQuantityText(R.plurals.headerForm, 0));

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionNo > 3) {
                    question.setText(res.getQuantityText(R.plurals.headerForm, 0));

                }
            }
        });
    }
}
