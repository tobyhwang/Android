package hwang62.cs478.proj_3.A3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tobyh on 10/23/2016.
 */
public class Receiver extends BroadcastReceiver{


    @Override
    //receive broadcast and make a decision based on the user's choice
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
        if(intent.getIntExtra("btnKey", 0) == 0 )
            i.putExtra("userChoice", 0);
        else
            i.putExtra("userChoice", 1);
        context.startActivity(i);
    }

}
