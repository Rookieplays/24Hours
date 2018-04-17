package ie.ul.o.daysaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Ali on 17/04/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();

    }

}

