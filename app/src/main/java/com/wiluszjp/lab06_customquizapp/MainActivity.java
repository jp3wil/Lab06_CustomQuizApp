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
    SharedPreferences.Editor editor;
    Resources res;
    Gson gson;
    String[] responses;
    String prevResp;
    int questionNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        res = getResources();
        gson = new Gson();

        setContentView(R.layout.activity_main);
        header = findViewById(R.id.header);
        previous = findViewById(R.id.prevResps);
        question = findViewById(R.id.questionBox);
        writeAns = findViewById(R.id.writeResponse);
        fourAns = findViewById(R.id.fourResponse);
        ansOne = findViewById(R.id.check1);
        ansOne.setText(res.getStringArray(R.array.four_check_layout)[0]);
        ansTwo = findViewById(R.id.check2);
        ansTwo.setText(res.getStringArray(R.array.four_check_layout)[1]);
        ansThree = findViewById(R.id.check3);
        ansThree.setText(res.getStringArray(R.array.four_check_layout)[2]);
        ansFour = findViewById(R.id.check4);
        ansFour.setText(res.getStringArray(R.array.four_check_layout)[3]);
        twoAns = findViewById(R.id.twoResponse);
        ansYes = findViewById(R.id.trueResp);
        ansYes.setText(res.getStringArray(R.array.true_false_layout)[0]);
        ansNo = findViewById(R.id.falseResp);
        ansNo.setText(res.getStringArray(R.array.true_false_layout)[1]);
        proceedButton = findViewById(R.id.proceedButton);

        responses = new String[3];
        questionNo = 0;
        header.setText(res.getQuantityString(R.plurals.headerForm, 0));
        prevResp = sharedPreferences.getString("previousResponsesGSON", "");
        if(!prevResp.equals("")) {
            responses = gson.fromJson(prevResp, String[].class);
            prevResp = "";
            for(int i = 0; i < 3; i++) {
                prevResp += responses[i];
                if(i < 2)
                    prevResp += "\n";
            }
        }
        else {
            prevResp = "";
        }
        previous.setText(prevResp);
        question.setText(res.getString(R.string.question_null));

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeAns.setVisibility(View.GONE);
                fourAns.setVisibility(View.GONE);
                ansOne.setVisibility(View.GONE);
                ansTwo.setVisibility(View.GONE);
                ansThree.setVisibility(View.GONE);
                ansFour.setVisibility(View.GONE);
                twoAns.setVisibility(View.GONE);
                ansYes.setVisibility(View.GONE);
                ansNo.setVisibility(View.GONE);
                if(questionNo < 3) {
                    header.setText(res.getQuantityString(R.plurals.headerForm,1, questionNo + 1));
                    question.setText(res.getStringArray(R.array.questions)[questionNo]);
                    if(questionNo == 0) {
                        writeAns.setVisibility(View.VISIBLE);
                    }
                    if(questionNo == 1) {
                        responses[0] = (writeAns.getText()).toString();
                        fourAns.setVisibility(View.VISIBLE);
                        ansOne.setVisibility(View.VISIBLE);
                        ansTwo.setVisibility(View.VISIBLE);
                        ansThree.setVisibility(View.VISIBLE);
                        ansFour.setVisibility(View.VISIBLE);
                    }
                    if(questionNo == 2) {
                        RadioButton resp_four = findViewById(fourAns.getCheckedRadioButtonId());
                        responses[1] = (resp_four.getText()).toString();
                        twoAns.setVisibility(View.VISIBLE);
                        ansYes.setVisibility(View.VISIBLE);
                        ansNo.setVisibility(View.VISIBLE);
                        proceedButton.setText(R.string.proceedButtOptTwo);
                    }
                }
                else if(questionNo == 3) {
                    header.setText(res.getQuantityString(R.plurals.headerForm,2));
                    question.setText(res.getString(R.string.question_full));
                    RadioButton resp_two = findViewById(twoAns.getCheckedRadioButtonId());
                    responses[2] = (resp_two.getText()).toString();
                    editor.putString("previousResponsesGSON", gson.toJson(responses));
                    editor.apply();
                    proceedButton.setText(R.string.proceedButtOptThree);
                }
                else {
                    responses = new String[3];
                    header.setText(res.getQuantityString(R.plurals.headerForm, 0));
                    prevResp = sharedPreferences.getString("previousResponsesGSON", "");
                    if(!prevResp.equals("")) {
                        responses = gson.fromJson(prevResp, String[].class);
                        prevResp = "";
                        for(int i = 0; i < 3; i++) {
                            prevResp += responses[i];
                            if(i < 2)
                                prevResp += "\n";
                        }
                    }
                    else {
                        prevResp = "";
                    }
                    previous.setText(prevResp);
                    question.setText(res.getQuantityText(R.plurals.headerForm, 0));
                    questionNo = -1;
                }
                questionNo++;
            }
        });
    }
}
