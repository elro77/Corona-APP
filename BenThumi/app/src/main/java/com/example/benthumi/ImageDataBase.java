package com.example.benthumi;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ImageDataBase {
    private static ImageDataBase imageDataBaseInstance = null;


    private List<String> nameList=new ArrayList<>();
    private List<Bitmap> imgList=new ArrayList<>();


    // private constructor restricted to this class itself
    private ImageDataBase()
    {

    }
    public static ImageDataBase getInstance()
    {
        if (imageDataBaseInstance == null)
            imageDataBaseInstance = new ImageDataBase();

        return imageDataBaseInstance;
    }

    public void addData(String name,Bitmap img){
        nameList.add(name);
        imgList.add(img);
    }

    public Bitmap getImageFromName(String name){
        return imgList.get(nameList.indexOf(name));
    }



}
