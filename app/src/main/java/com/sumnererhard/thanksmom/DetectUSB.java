package com.sumnererhard.thanksmom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Created by sumnererhard on 1/2/17.
 */

public class DetectUSB extends BroadcastReceiver {

    private static final String TAG = "DetectUSB";

    public DetectUSB() {}

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "The device is charging", Toast.LENGTH_SHORT).show();
        } else {
            intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED);
            makeText(context, "The device is not charging", Toast.LENGTH_SHORT).show();
        }

    }

}
