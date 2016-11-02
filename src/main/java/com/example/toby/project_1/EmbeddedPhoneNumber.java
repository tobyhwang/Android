package com.example.toby.project_1;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import java.util.regex.*;

public class EmbeddedPhoneNumber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedded_phone_number);
    }

    //When the user clicks to call the embedded number
    public void clkCallNum(View view){
        //Go through the text to find the number
        String phoneText = getNumberFromString();
        try {
            String phoneNumber = PhoneNumberUtils.normalizeNumber(phoneText);

            //If it is not a valid phone number throw an exception
            if(phoneNumber.length()<1)
                throw new Exception();

            //Create an implicit intent for calling a number
            Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            startActivity(intentPhone);
        }
        catch(Exception e)
        {
            //display message that no phone number was found
            //Used code from android site
            Context context = getApplicationContext();
            CharSequence errorMessage = "No number found";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, errorMessage, duration);
            toast.show();
        }
    }

    //Function to extract phone number in (###)###-#### and (###) ###-#### format
    public String getNumberFromString(){
        EditText text = (EditText) findViewById(R.id.editText);
        System.out.println(text.getText().toString());
        Pattern patternNoSpace = Pattern.compile("\\([0-9]{3}\\)[0-9]{3}-[0-9]{4}");
        Matcher matcherNoSpace = patternNoSpace.matcher(text.getText().toString());
        Pattern patternWithSpace = Pattern.compile("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}");
        Matcher matcherWithSpace = patternWithSpace.matcher(text.getText().toString());

        //return the number if found
        if(matcherNoSpace.find()) {
            return matcherNoSpace.group(0);
        }
        else if(matcherWithSpace.find()) {
            return matcherWithSpace.group(0);
        }
        else
            return "";
    }
}
