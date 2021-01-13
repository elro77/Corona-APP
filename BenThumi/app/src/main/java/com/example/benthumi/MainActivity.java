package com.example.benthumi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },105);
        }
        else{
            Intent intent = new Intent(this,IntroActivity.class);
            startActivity(intent);
           // Toast.makeText(this, "already allowed!!", Toast.LENGTH_SHORT).show();
        }
        initilaizeData();


    }

    private void initilaizeData(){
        DataSetes myDataSet=DataSetes.getInstance();
        ImageDataBase imageDataBase=ImageDataBase.getInstance();
        Bitmap img;

        //img = BitmapFactory.decodeResource(getResources(), R.drawable.elro);
       // myDataSet.addToImageList(img);
        //myDataSet.addToLabelList("Elroye Cahana");

        img = BitmapFactory.decodeResource(getResources(), R.drawable.barak);
        imageDataBase.addData("Barak Obama",img);
        myDataSet.addToLabelList("Barak Obama");
        myDataSet.addToImageList(img);

        //img = BitmapFactory.decodeResource(getResources(), R.drawable.bil_gates);
        //myDataSet.addToImageList(img);
        //myDataSet.addToLabelList("Bill Gates");

       // img = BitmapFactory.decodeResource(getResources(), R.drawable.or_lustig);
       // myDataSet.addToImageList(img);
        //myDataSet.addToLabelList("Or Lustig");

        //img = BitmapFactory.decodeResource(getResources(), R.drawable.nirit_gabish);
        //myDataSet.addToImageList(img);
        //myDataSet.addToLabelList("Nirit Gabish");

        img = BitmapFactory.decodeResource(getResources(), R.drawable.beto_katz);
        imageDataBase.addData("Beto Katz",img);
        myDataSet.addToImageList(img);
        myDataSet.addToLabelList("Beto Katz");

        //img = BitmapFactory.decodeResource(getResources(), R.drawable.bar_katz);
        //myDataSet.addToImageList(img);
        //myDataSet.addToLabelList("Bar Katz");

        //img = BitmapFactory.decodeResource(getResources(), R.drawable.eyal_romano);
        //myDataSet.addToImageList(img);
        //myDataSet.addToLabelList("Eyal Romano");

       // img = BitmapFactory.decodeResource(getResources(), R.drawable.reem_tap);
       // myDataSet.addToImageList(img);
        //myDataSet.addToLabelList("Reem Tapash");



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==105){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, proceed to the normal flow.
                Intent intent = new Intent(this,IntroActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "is allowed!!", Toast.LENGTH_SHORT).show();
            } else {
               // Toast.makeText(this, "must allow!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
