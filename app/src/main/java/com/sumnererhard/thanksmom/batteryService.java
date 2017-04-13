package com.sumnererhard.thanksmom;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.MutableDateTime;

import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;

/**
 * Created by sumnererhard on 1/6/17.
 */

public class batteryService extends Service {

    private clearCache mClearCache = new clearCache();

    private BroadcastReceiver mBatteryStatus = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            // grabbing the days integer value
            SharedPreferences fetchInt = getSharedPreferences("integers.xml", 0);
            int oldTime = fetchInt.getInt("oldSystemTime", 0);
            MutableDateTime epoch = new MutableDateTime();
            epoch.setDate(0);
            DateTime now = new DateTime();

            Days days = Days.daysBetween(epoch, now);
            int newTime = days.getDays();
            int oneWeekLater = oldTime + 7;

            if (intent.getAction().equals(ACTION_POWER_CONNECTED) && (newTime >= oneWeekLater || oldTime == 0)) {
                Toast.makeText(context, "Juicin'", Toast.LENGTH_SHORT).show();
                getSystemTime();

                mClearCache.setContext(context);

            } else {
                Toast.makeText(context, "Not Juicin'", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_POWER_CONNECTED);
        filter.addAction(ACTION_POWER_DISCONNECTED);
        this.registerReceiver(mBatteryStatus, filter);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public int getSystemTime() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        Days days = Days.daysBetween(epoch, now);
//        Weeks weeks = weeksBetween(epoch, now);
//        Months months = Months.monthsBetween(epoch, now);

        SharedPreferences.Editor editor = getSharedPreferences("integers.xml", MODE_PRIVATE).edit();
        editor.putInt("oldSystemTime", days.getDays());
        editor.commit();

        return days.getDays();
    }

}

