package com.example.toby.project_1;
import android.content.ComponentName;
import android.net.Uri;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //if user clicked the embedded phone number button
    //explicit intent
    public void clkPhoneNumber(View view) {
        Intent intentPhoneNum = new Intent(this, EmbeddedPhoneNumber.class);
        startActivity(intentPhoneNum);
    }

    //if the user clicks on the button for the link
    //implicit intent
    public void clkGoToLink(View view){
        String URL = "https://developer.android.com/index.html";
        Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        intentBrowser.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
        startActivity(intentBrowser);
    }


}
