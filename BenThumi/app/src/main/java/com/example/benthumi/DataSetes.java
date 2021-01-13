package com.example.benthumi;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataSetes {
    private static DataSetes single_instance = null;


    private List< String >imagesList = new ArrayList<>();

    private List<String>labelsString = new ArrayList<>();


    private DataSetes() {}
    public static DataSetes getInstance()
    {
        if (single_instance == null)
            single_instance = new DataSetes();

        return single_instance;
    }


    public List<String > getImageDataSet(){
        return imagesList;
    }



    public List<String> getLabels(){
        return labelsString;
    }

    public void addToImageList(Bitmap img){

        String imgStr = getStringImage(img);
        this.imagesList.add(imgStr);
    }

    public void addToLabelList(String label){ this.labelsString.add(label);}

    public String getStringImage(Bitmap bp) {
        String result;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bp.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] imageBytes=baos.toByteArray();

        result= Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return result;

    }





}
