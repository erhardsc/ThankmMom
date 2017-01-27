package com.sumnererhard.thanksmom;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.util.LruCache;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.io.File;
import java.lang.reflect.Method;

import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;
import static org.joda.time.Weeks.weeksBetween;

/**
 * Created by sumnererhard on 1/6/17.
 */

public class batteryService extends Service {

    private MainActivity main;
    private LruCache mCache;

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
            int mOldTime = oldTime + 7;

            if (intent.getAction().equals(ACTION_POWER_CONNECTED) && (newTime >= mOldTime || oldTime == 0)) {
                Toast.makeText(context, "Juicin'", Toast.LENGTH_SHORT).show();
                getSystemTime();
                clearApplicationData();

            } else {

                //intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED);
                Toast.makeText(context, "Not Juicin'", Toast.LENGTH_SHORT).show();
                Log.d("System Run time", "value: " + oldTime);
                Log.d("System Run time", "value: " + days.getDays());
                clearApplicationData();
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
        Weeks weeks = weeksBetween(epoch, now);
        Months months = Months.monthsBetween(epoch, now);

        SharedPreferences.Editor editor = getSharedPreferences("integers.xml", MODE_PRIVATE).edit();
        editor.putInt("oldSystemTime", days.getDays());
        editor.commit();

        return days.getDays();
    }

    public void clearApplicationData() {
        PackageManager pm = getPackageManager();
        Method[] methods = pm.getClass().getDeclaredMethods();

        for (Method m : methods) {
            if (m.getName().equals("freeStorage")) {
                try {
                    long desiredFreeStorage = 8 * 1024 * 1024 * 1024;
                    m.invoke(pm, desiredFreeStorage, null);
                } catch (Exception e) {
                    deleteCache(this);
                }
                break;
            }
        }

    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}

