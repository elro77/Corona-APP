package com.example.benthumi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.w3c.dom.Text;

import java.util.Random;

public class Summary extends AppCompatActivity {
    private Quiz quiz;
    private Object[] answers;
    private String[] questionsArray;
    final int min = 0;
    final int max = 100;
    private  Results result;
    private Double[] resultsArray;

    Python py;
    PyObject pyObject;



    TableLayout tbSummaryTable;
    Button btnFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        result=Results.getInstance();

        quiz= Quiz.getInstance();
        answers=quiz.getAnswersArray();
        resultsArray=new Double[quiz.getAmountQuestions()];
        for(int i=0;i<quiz.getAmountQuestions();i++){
            resultsArray[i]=-1.0;
        }
        questionsArray=quiz.getQuestionsArray();



       // Toast.makeText(Summary.this,answers[1].toString(),Toast.LENGTH_LONG).show();
        tbSummaryTable=(TableLayout)findViewById(R.id.tbSummaryTable);
        btnFinish=(Button)findViewById(R.id.btnFinish);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcFinish();
            }
        });
        btnFinish.setOnTouchListener(new View.OnTouchListener() {
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
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        TextView text1 = new TextView(this);
        text1.setLayoutParams(new TableRow.LayoutParams(1));
        text1.setText("Body Temperture");
        text1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        text1.setTextColor(getResources().getColor(R.color.colorPattern2));
        tableRow.addView(text1);

        TextView text2 = new TextView(this);
        text2.setLayoutParams(new TableRow.LayoutParams(1));
        text2.setText(quiz.getFever()+"");
        text2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        text2.setTextColor(getResources().getColor(R.color.colorPattern2));
        tableRow.addView(text2);
        tbSummaryTable.addView(tableRow);

        for(int i=0;i<quiz.getAmountQuestions();i++){
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView txt1 = new TextView(this);
            txt1.setLayoutParams(new TableRow.LayoutParams(1));
            txt1.setText(questionsArray[i]);
            txt1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            txt1.setTextColor(getResources().getColor(R.color.colorPattern2));
            tr.addView(txt1);

            TextView txt2 = new TextView(this);
            txt2.setLayoutParams(new TableRow.LayoutParams(1));
            txt2.setText(answers[i].toString());
            txt2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            txt2.setTextColor(getResources().getColor(R.color.colorPattern2));
            txt2.setId(10+i);
            tr.addView(txt2);

            Button btn = new Button(this);
            btn.setLayoutParams(new TableRow.LayoutParams(1));
            btn.setText("Change");
            btn.setBackground(getResources().getDrawable(R.drawable.change_btn));
            btn.setTextColor(getResources().getColor(R.color.colorPattern3));
            btn.setId(i);
            //btn.setTextColor(getResources().getColor(R.color.colorPattern2));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeAnswer(v.getId());
                }
            });
            btn.setOnTouchListener(new View.OnTouchListener() {
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
            tr.addView(btn);
            tbSummaryTable.addView(tr);
        }


        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        py = Python.getInstance();
        pyObject = py.getModule("eyal"); // here we will give name of our python file

    }

    private void changeAnswer(int btnID){
        //Toast.makeText(this,"hello from "+btnID,Toast.LENGTH_SHORT).show();
        TextView txt = (TextView) findViewById(10+btnID);
        //txt.setText(""+btnID);
        if(txt.getText().equals("Yes")){
            txt.setText("No");
            quiz.updateAnswer(btnID,new String("No"));
        }
        else if(txt.getText().equals("No")){
            txt.setText("Yes");
            quiz.updateAnswer(btnID,new String("Yes"));
        }
        else if(txt.getText().equals("Dry")){
            txt.setText("Wet");
            quiz.updateAnswer(btnID,new String("Wet"));
        }
        else if(txt.getText().equals("Wet")){
            txt.setText("None");
            quiz.updateAnswer(btnID,new String("None"));
        }
        else if(txt.getText().equals("None")){
            txt.setText("Dry");
            quiz.updateAnswer(btnID,new String("Dry"));
        }
        else{
            int num = Integer.parseInt(txt.getText().toString());
            if(num==10) {
                num=0;
            }
            else
                num++;
            txt.setText(""+num);
            quiz.updateAnswer(btnID,new Integer(num));

        }
       // Toast.makeText(this,quiz.getAnswersArray()[btnID].toString(),Toast.LENGTH_SHORT).show();
    }

    private void funcFinish(){
        int n=0;
        double sum=0;
        double precentageResult=0.0;
        answers=quiz.getAnswersArray();
        for(int i=0;i<quiz.getAmountQuestions();i++){
            if(answers[i] instanceof  String){
                String str=(String)answers[i];
                if(str.equals("Yes") || str.equals("Wet")){
                    n++;
                    resultsArray[i]=quiz.getPrecentage(i);
                }
            }

            else if(answers[i] instanceof Integer){
                int num =(Integer)answers[i];
                if(num>=7){
                    n++;
                    resultsArray[i]=quiz.getPrecentage(i);
                }
            }
        }

        PyObject feverResult = pyObject.callAttr("scale_feature",quiz.getFever(),41,36.5);
        quiz.setFever(feverResult.toDouble()*100);

        precentageResult=getMax();
        result.setStatus(precentageResult>=70);


        Toast.makeText(this,""+precentageResult,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,Result.class);
        startActivity(intent);
    }

    private double getMax(){
        double max=quiz.getFever();
        for(int i=0;i<quiz.getAmountQuestions();i++){
            if(max<resultsArray[i]) {
                max = resultsArray[i] ;
            }
        }
        return max;

    }
}
