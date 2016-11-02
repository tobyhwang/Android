package com.example.tobyh.project_2;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FullPicture extends AppCompatActivity {

    //stores the list of homepages
    String[] homepage;
    //the car that is selected
    int carPicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_picture);

        //get the car selected
        int enlargedCarPic = getIntent().getIntExtra("carPic", 0);
        carPicked = getIntent().getIntExtra("carPicked", 0);

        //set the enlarged image of the vehicle
        ImageView image = (ImageView) findViewById(R.id.imgButton);
        image.setImageResource(enlargedCarPic);

        //save the array of company homepages
        homepage = getResources().getStringArray(R.array.manufacturer_website);
    }

    //Implicit intent to open company homepage
    public void ToHomePage(View view){
        Intent intentWebsite = new Intent(Intent.ACTION_VIEW);
        intentWebsite.setData(Uri.parse(homepage[carPicked]));
        startActivity(intentWebsite);
    }

}
