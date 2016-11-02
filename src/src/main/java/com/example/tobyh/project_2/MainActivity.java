package com.example.tobyh.project_2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Hashtable;

import javax.net.ssl.SSLEngineResult;

public class MainActivity extends AppCompatActivity {

    //Get each one of the car pictures
    Integer[] carPics = { R.drawable.atsv, R.drawable.camaro,
            R.drawable.evo, R.drawable.focus, R.drawable.grandcherokee,
            R.drawable.gtr, R.drawable.r8, R.drawable.wrx
    };

    //High density pictures
    Integer[] carPicsHDPI = {R.drawable.atsv_hdpi, R.drawable.camaro_hdpi,
            R.drawable.evo_hdpi, R.drawable.focus_hdpi, R.drawable.grandcherokee_hdpi,
            R.drawable.gtr_hdpi, R.drawable.r8_hdpi, R.drawable.wrx_hdpi
    };

    //global variables
    String[] carCompany;
    String[] homepage;
    String[] dealerLocation;
    Hashtable <Integer, String[]> address = new Hashtable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get all the company names
        carCompany = getResources().getStringArray(R.array.car_company);

        //Create hashtable of address
        CreateAddressHashtable();

        //Get the homepages of the car companies
        homepage = getResources().getStringArray(R.array.manufacturer_website);


        //Set the gridview
        GridView gridView = (GridView) findViewById(R.id.carGrid);
        gridView.setAdapter(new ImageAdapter(this, carPics, carCompany));


        //set on lick functionality
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DisplayFullPicture(position);
            }
        });

        //register the long click for the
        registerForContextMenu(gridView);

    }

    //Create the context menu for long clicks on the images
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle(getResources().getString(R.string.selectOption));
        menu.add(0, v.getId(), 0, getResources().getString(R.string.viewPicture));
        menu.add(0, v.getId(), 0, getResources().getString(R.string.companyWebPage));
        menu.add(0, v.getId(), 0, getResources().getString(R.string.carDealers));

    }

    //Actions based on the options selected from the context menu
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getTitle().toString()){
            case "View Picture":
                DisplayFullPicture(info.position);
                break;

            case "Company Web Page":
                CompanyPage(info.position);
                break;

            case "Car Dealers":
                DealershipLocations(info.position);
                break;

            default:
                return false;

        }
        return true;
    }

    //Explicity Intent to display the enlarged picture of the car
    public void DisplayFullPicture(int pictureClicked){
        Intent intentFullPic = new Intent(MainActivity.this, FullPicture.class);
        intentFullPic.putExtra("carPicked", pictureClicked);
        intentFullPic.putExtra("carPic", carPicsHDPI[pictureClicked]);
        startActivity(intentFullPic);
    }

    //Implicit intent to navigate to the company webpage
    public void CompanyPage(int position){
        Intent intentWebsite = new Intent(Intent.ACTION_VIEW);
        intentWebsite.setData(Uri.parse(homepage[position]));
        startActivity(intentWebsite);
    }

    //Explicit Intent to display the dealership locations for the selected vehicle
    public void DealershipLocations(int position){
        dealerLocation = address.get(position);
        Intent intentLoc = new Intent(MainActivity.this, DealershipList.class);
        intentLoc.putExtra("dealerAddr", dealerLocation);
        startActivity(intentLoc);
    }

    //Hashtable to reference the location string arrays in the resource file
    public void CreateAddressHashtable(){
        address.put(0, getResources().getStringArray(R.array.Cadillac));
        address.put(1, getResources().getStringArray(R.array.Chevrolet));
        address.put(2, getResources().getStringArray(R.array.Mitsubishi));
        address.put(3, getResources().getStringArray(R.array.Ford));
        address.put(4, getResources().getStringArray(R.array.Jeep));
        address.put(5, getResources().getStringArray(R.array.Nissan));
        address.put(6, getResources().getStringArray(R.array.Audi));
        address.put(7, getResources().getStringArray(R.array.Subaru));
    }
}
