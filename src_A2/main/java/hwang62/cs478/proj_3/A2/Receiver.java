package hwang62.cs478.proj_3.A2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tobyh on 10/22/2016.
 */
public class Receiver extends BroadcastReceiver {

    //Receives the first broadcast
    public void onReceive(Context context, Intent intent)
    {
        //choose between hotel or restaurant
        if(intent.getIntExtra("btnKey",0) == 0) {
            Toast.makeText(context, "Hotel Button Clicked", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(context, "Restaurant Button Clicked", Toast.LENGTH_SHORT).show();
    }


}
