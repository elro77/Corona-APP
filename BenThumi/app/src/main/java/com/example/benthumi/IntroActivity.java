package com.example.benthumi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {
    ImageView imgView;
    private Handler mHandler=new Handler();
    private int cnt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        imgView=(ImageView)findViewById(R.id.screenImage);
        final MediaPlayer introSound= MediaPlayer.create(this,R.raw.sound);
        //introSound.start();
        mHandler.postDelayed(mToastRunnable,100);
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            if(cnt<21){
                imgView.setAlpha((float)(cnt*0.05));
                cnt++;
                mHandler.postDelayed(mToastRunnable,100);
            }
            else if(cnt<27){
                //Toast.makeText(IntroActivity.this,cnt+"",Toast.LENGTH_SHORT).show();
                cnt++;
                mHandler.postDelayed(mToastRunnable,500);
            }
            else if(cnt>=24){
               // Toast.makeText(IntroActivity.this,"Yo",Toast.LENGTH_SHORT).show();
                yo();
            }
        }
    };
    private void yo(){
        //Toast.makeText(IntroActivity.this,"Go TO Face Reco ",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, faceRecognizer.class);
        //Intent intent = new Intent(this, Questions.class);
        startActivity(intent);
    }
}
