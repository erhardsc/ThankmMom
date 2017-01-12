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
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.lang.reflect.Method;

import static android.content.Intent.ACTION_POWER_CONNECTED;
import static android.content.Intent.ACTION_POWER_DISCONNECTED;
import static org.joda.time.Weeks.weeksBetween;

/**
 * Created by sumnererhard on 1/6/17.
 */

public class batteryService extends Service {

    private MainActivity main;

    private BroadcastReceiver mBatteryStatus = new BroadcastReceiver() {
        public int runTime;
        @Override
        public void onReceive(Context context, Intent intent) {

            // grabbing the days integer value
            SharedPreferences fetchInt = getSharedPreferences("integers.xml", 0);
            int oldTime = fetchInt.getInt("oldSystemTime", 0);

            int weekLater = oldTime + 7;

            if (intent.getAction().equals(ACTION_POWER_CONNECTED) && oldTime == weekLater) {
                Toast.makeText(context, "Juicin'", Toast.LENGTH_SHORT).show();
                clearApplicationData();
                getSystemTime();

            } else {

                intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED);
                Toast.makeText(context, "Not Juicin'", Toast.LENGTH_SHORT).show();
                runTime = getSystemTime();
                Log.d("New Run time", "value: " + runTime);
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
            if (m.getName().equals("freeStorage")){
                try{
                    Log.i("Method Package", m.toGenericString());
                } catch (Exception e){

                }
                break;
            }
        }

//        try {
//            File cacheDirectory = getCacheDir();
//            File applicationDirectory = new File(cacheDirectory.getParent());
//            if (applicationDirectory.exists()) {
//                String[] fileNames = applicationDirectory.list();
//                for (String fileName : fileNames) {
//                    if (!fileName.equals("lib")) {
//                        deleteFile(new File(applicationDirectory, fileName));
//                    }
//                }
//                Toast.makeText(getApplicationContext(), "Deleting Application Cache", Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "Thanks Mom", Toast.LENGTH_LONG).show();
//            }
//        }catch(Exception e){
//            Toast.makeText(getApplicationContext(), "Sorry, not enough cache to delete right now", Toast.LENGTH_LONG).show();
//        }
    }

//    public static boolean deleteFile(File file) {
//        boolean deletedAll = false;
//        if (file != null) {
//            if (file.isDirectory()) {
//                String[] children = file.list();
//                for (int i = 0; i < children.length; i++) {
//                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
//                }
//            } else {
//                deletedAll = file.delete();
//            }
//        }
//
//        return deletedAll;
//    }
}
