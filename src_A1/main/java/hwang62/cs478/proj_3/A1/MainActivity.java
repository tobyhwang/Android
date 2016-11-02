package hwang62.cs478.proj_3.A1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    //Intent
    public static final String intentToast = "hwang62.cs478.proj_3.A1.SHOW_TOAST";
    private int userChoice = 0;

    @Override

    //On create method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //OnClick when the user chooses a hotel option
    public void BtnHotelsOnClick(View view){
        userChoice = 0;
        BroadcastToReceivers();
    }

    //OnClick when the user chooses a restaurant option
    public void BtnRestaurantsOnClick(View View){
        userChoice = 1;
        BroadcastToReceivers();
    }

    //Sends the broadcast to the other activities
    public void BroadcastToReceivers(){
        Intent intent = new Intent(intentToast);
        intent.putExtra("btnKey", userChoice);
        sendOrderedBroadcast(intent, null);
    }

}
