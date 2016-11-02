package com.example.tobyh.project_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class DealershipList extends AppCompatActivity {

    //store the specific dealerships for the vehicle selected
    String[] arrAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealership_list);

        //save the address for the dealer for the specific vehicle
        arrAddress = getIntent().getStringArrayExtra("dealerAddr");

        //Used the array adapter to tie the addresses
        ListView lstAddr = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<String> (this, R.layout.activity_address_listing, arrAddress);
        lstAddr.setAdapter(adapter);
    }
}
