package com.example.benthumi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Questions extends AppCompatActivity {
    /*private String[] questionsArray= {
            "Have You Been In Contact\nWith A Corona Patient\nRecently?",
            "How Strong Is Your\nHead Ache?",
            "Do You Feel Any Muscle\nOr Body Pain?",
            "Do You Suffer From\nCongestion Or Runny\nNose?",
            "Do you Feel Loss\nOf Smell?",
            "Do You Have Sore Nort?",
            "Do You Feel Loss\nOf Taste?",
            "Do You Have Nausea Or\nVomiting?",
            "Do You Feel Any Breath Difficulty?",
            "Do You Have Chills\nRecently?",
            "Do You Suffer From\nDiarrhea?"
    };
    private Object[] answersArray=new Object[questionsArray.length];
    private int current=0;*/



    private Quiz quiz;
    TextView txtProgress;
    SeekBar sbProgress;

    Button btnYes;
    Button btnNo;
    Button btnNone;
    ImageView btnBack;
    ImageView btnNext;
    SeekBar sbRate;
    TextView txtPatient;
    TextView txtQuestion;
    ConstraintLayout laySeekBarRate;
    ConstraintLayout layYesNoBtns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        quiz= Quiz.getInstance();

        btnYes=(Button)findViewById(R.id.btnYes);
        btnNo=(Button)findViewById(R.id.btnNo);
        btnNone=(Button)findViewById(R.id.btnNone);

        btnBack=(ImageView)findViewById(R.id.btnBackQues);
        btnNext=(ImageView)findViewById(R.id.btnNextQues);

        txtPatient=(TextView)findViewById(R.id.lblPatiendName);
        txtPatient.setText("Patient: "+quiz.getPatientName());


        txtQuestion=(TextView)findViewById(R.id.lblQuestion);
        txtProgress=(TextView)findViewById(R.id.txtProgress);

        laySeekBarRate=(ConstraintLayout)findViewById(R.id.laySekkBar);
        layYesNoBtns=(ConstraintLayout)findViewById(R.id.layYesNoBtns);

        sbRate=(SeekBar)findViewById(R.id.seekBar2);
        sbProgress=(SeekBar)findViewById(R.id.sbarProgress);

        sbRate.setProgress(4);
        sbRate.incrementProgressBy(1);
        sbRate.setMax(10);

        sbProgress.incrementProgressBy(1);
        sbProgress.setMax(quiz.getAmountQuestions());
        //sbProgress.setEnabled(false);
        sbProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        sbProgress.getThumb().mutate().setAlpha(0);
        updateProgress();




        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesFunction();
            }
        });
        btnYes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noFunction();
            }
        });
        btnNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFunction();

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFunction();
            }
        });



        btnNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noneFunction();
            }
        });
        btnNone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });


        refresh();
    }
    private void updateProgress(){
        sbProgress.setProgress(quiz.getCurrent());
        txtProgress.setText((quiz.getCurrent())+" out of "+quiz.getAmountQuestions());
    }

    private void refresh(){
        updateProgress();
        if(quiz.getCurrent()==0){
            btnBack.setVisibility(View.GONE);
        }
        else{
            btnBack.setVisibility(View.VISIBLE);
        }
        if(quiz.getCurrent()==1){
            laySeekBarRate.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            layYesNoBtns.setVisibility(View.GONE);
        }
        else{
            laySeekBarRate.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            layYesNoBtns.setVisibility(View.VISIBLE);
        }
        if(quiz.getCurrent()==11){
            btnYes.setText("Wet");
            btnNo.setText("Dry");
            btnNone.setVisibility(View.VISIBLE);
        }
        else{
            btnYes.setText("Yes");
            btnNo.setText("No");
            btnNone.setVisibility(View.GONE);
        }
        if (quiz.hasNext()) {
            txtQuestion.setText(quiz.getNextQuesiton());
        } else {
            Intent intent = new Intent(this, Summary.class);
            startActivity(intent);
        }

    }

    private void yesFunction(){
       quiz.setObjectToArray(new String(btnYes.getText().toString()));
        refresh();

    }
    private void noFunction(){
        quiz.setObjectToArray(new String(btnNo.getText().toString()));
        refresh();
    }
    private void noneFunction(){
        quiz.setObjectToArray(new String(btnNone.getText().toString()));
        refresh();
    }
    private void backFunction(){
        quiz.getPrevQuesiton();
        refresh();
    }
    private void nextFunction(){
        if(quiz.getCurrent()==1){
            quiz.setObjectToArray(new Integer(sbRate.getProgress()));
            refresh();
        }
        else {
            if (quiz.hasNext()) {
                refresh();
            } else {
                Intent intent = new Intent(this, Summary.class);
                startActivity(intent);
            }
        }

    }

}
