package com.example.tobyh.project_2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.text.style.LineHeightSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//Create the image adapter
public class ImageAdapter extends BaseAdapter{
    private static final int PADDING = 4;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private final String[] carCompany;
    private final Integer[] carPics;
    private LayoutInflater layoutInflater;
    private Context context;

    //constructor
    public ImageAdapter(Context c, Integer[] carPics, String[] carCompany)
    {
        this.carCompany = carCompany;
        this.carPics = carPics;
        context = c;
        layoutInflater = LayoutInflater.from(c);
    }

    //get count of the array
    @Override
    public int getCount(){
       return carCompany.length;
    }

    //get item from the array
    @Override
    public Object getItem(int position)
    {
        return null;
    }

    //get itemID from the array
    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    //get the actual view
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //establish a gridView
        View gridView;

        //if there is no view to recycle, then create it
        if(convertView == null) {
            //Set the inflater
            gridView = layoutInflater.inflate(R.layout.car_layout, parent, false);
            setImageGrid(gridView, position);
        }

        else{
            //recycle the view
            gridView = convertView;
            setImageGrid(gridView, position);

        }

        //return the view
        return gridView;
    }

    public void setImageGrid(View gridView, int position)
    {
        //identify the text and image views
        TextView textView = (TextView) gridView.findViewById(R.id.textView);
        textView.setLines(2); //add additional line from being cut off
        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView);

        //populate the text and image views
        textView.setText(carCompany[position]);

        //Set the attributes of the images
        imageView.setImageResource(carPics[position]);
        gridView.setLayoutParams(new GridView.LayoutParams(WIDTH, HEIGHT));
        gridView.setPadding(PADDING, PADDING, PADDING, PADDING);
    }



}
