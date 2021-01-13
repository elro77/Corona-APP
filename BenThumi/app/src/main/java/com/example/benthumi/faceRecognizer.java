package com.example.benthumi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class faceRecognizer extends AppCompatActivity {

    private ImageDataBase imageDataBase;
    Button btnTryAgain;
    Python py;
    PyObject pyObject;
    String result;
    DataSetes myDataSet;
    String imgStr;

    Button btnAcceptProfile;
    Button btnRejectProfile;
    TextView txtPatitentName;
    ImageView imgBoxProflie;

    ConstraintLayout controlLayout;
    ConstraintLayout waitingLayout;
    ConstraintLayout AceeptLayOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognizer);
       // Toast.makeText(faceRecognizer.this,"Face Recognizer Here!",Toast.LENGTH_LONG).show();

        imageDataBase=ImageDataBase.getInstance();

        controlLayout=(ConstraintLayout)findViewById(R.id.controlLayout);
        waitingLayout=(ConstraintLayout)findViewById(R.id.waitingLayout);
        AceeptLayOut=(ConstraintLayout)findViewById(R.id.layerAcceptance);



        btnTryAgain=(Button)findViewById(R.id.btnTryAgian);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFunc();
            }
        });


        btnAcceptProfile=(Button)findViewById(R.id.btnPateinetYes);
        btnRejectProfile=(Button)findViewById(R.id.btnPateinetNo);
        txtPatitentName=(TextView)findViewById(R.id.txtPatientAgreeName);
        imgBoxProflie=(ImageView)findViewById(R.id.imgProfiePatient);


        myDataSet=DataSetes.getInstance();
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }
        py = Python.getInstance();
        pyObject = py.getModule("script"); // here we will give name of our python file

        if(ContextCompat.checkSelfPermission(faceRecognizer.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(faceRecognizer.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },105);
        }
        else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,105);
            // Toast.makeText(this, "already allowed!!", Toast.LENGTH_SHORT).show();
        }
        btnAcceptProfile.setOnTouchListener(new View.OnTouchListener() {
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

        btnRejectProfile.setOnTouchListener(new View.OnTouchListener() {
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



        btnAcceptProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approved();
            }
        });

        btnRejectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decline();
            }
        });

        //Quiz quiz= Quiz.getInstance();
       // quiz.setPatientName("bananaMan");
       // Intent intent = new Intent(this, Thermal.class);
       // startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Toast.makeText(this,"resultCode="+resultCode,Toast.LENGTH_LONG).show();
        if(requestCode==105 && resultCode==-1){
            //get capture image
            //String result;
            Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
            imgStr = myDataSet.getStringImage(capturedImage);
            //PyObject obj = pyObject.callAttr("main",imgStr,myDataSet.getImageDataSet().toArray(),myDataSet.getLabels().toArray());
            //result=obj.toString();

           // ExampleThread myThread = new ExampleThread();
            //myThread.start();

            new MyTask().execute("start");
            controlLayout.setVisibility(View.GONE);
            waitingLayout.setVisibility(View.VISIBLE);
            return;

        }
        else if(resultCode==0){ //abort camera
            waitingLayout.setVisibility(View.GONE);
            controlLayout.setVisibility(View.VISIBLE);
        }

    }

    private void finishRunningPython(){

            controlLayout.setVisibility(View.VISIBLE);
            waitingLayout.setVisibility(View.GONE);


            if(result.equals("to many")){
                //controlLayout.setVisibility(View.VISIBLE);
                Toast.makeText(this,"too many face found, Please try again",Toast.LENGTH_LONG).show();
            }
            else if(result.equals("no face found") || result.equals("Unknown Person")){
                //controlLayout.setVisibility(View.VISIBLE);
                Toast.makeText(faceRecognizer.this,"Un-Known Recognition, Please try again",Toast.LENGTH_LONG).show();
            }
            else{
                controlLayout.setVisibility(View.GONE);
                AceeptLayOut.setVisibility(View.VISIBLE);
                txtPatitentName.setText("Are you "+result+"?");
                Bitmap img;
                imgBoxProflie.setImageBitmap(imageDataBase.getImageFromName(result));
            }
    }


    private void approved(){
        Quiz quiz= Quiz.getInstance();
        quiz.setPatientName(result);
        Intent intent = new Intent(this, Thermal.class);
        startActivity(intent);
    }

    private void decline(){
        AceeptLayOut.setVisibility(View.GONE);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,105);
    }

    private void btnFunc(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,105);
        //Intent intent = new Intent(this, Questions.class);
       // startActivity(intent);
    }

    /*class ExampleThread extends Thread{
        ExampleThread(){
        }

        @Override
        public void run(){
            PyObject obj = pyObject.callAttr("main",imgStr,myDataSet.getImageDataSet().toArray(),myDataSet.getLabels().toArray());
            result=obj.toString();
            finishRunningPython();

        }
    }*/


    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            PyObject obj = pyObject.callAttr("main",imgStr,myDataSet.getImageDataSet().toArray(),myDataSet.getLabels().toArray());
            result=obj.toString();
            return "finish";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // do something with result
            finishRunningPython();
        }
    }


}


