package com.example.benthumi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    ConstraintLayout myLayout;
    Button btnConfirm;
    TextView txtResult;
    ImageView imgResult;
    private Quiz quiz=Quiz.getInstance();

    private  Results result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result=Results.getInstance();
        myLayout=(ConstraintLayout)findViewById(R.id.clayMainLayout);

        btnConfirm=(Button)findViewById(R.id.btnConfirm);

        txtResult=(TextView)findViewById(R.id.txtResultText);

        imgResult=(ImageView)findViewById(R.id.imgResultImage);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcConfirm();
            }
        });
        btnConfirm.setOnTouchListener(new View.OnTouchListener() {
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

        if(result.getStatus()==true){
            txtResult.setText("Show The Results To\nYour Doctor For More\nConsultant.");
            imgResult.setImageDrawable(getResources().getDrawable(R.drawable.doctor));
        }
        else {
            txtResult.setText("You Are Clean From\n Corona!");
            imgResult.setImageDrawable(getResources().getDrawable(R.drawable.calm));
        }
    }
    private void funcConfirm(){
        quiz.resetQuiz();
        Intent intent = new Intent(this,faceRecognizer.class);
        startActivity(intent);
    }
}
