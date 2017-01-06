package com.sumnererhard.thanksmom;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detectCharge();

       // instance = this;
//        try {
//            clearApplicationData();
//        } catch (Exception e){
//            Toast.makeText(getApplicationContext(), "Sorry, not enough cache to delete right now", Toast.LENGTH_LONG).show();
//        }

    }

    public void detectCharge() {

        //create an IntentFilter object that matches battery change action
        IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        //register broadcast receiver to receive a sticky intent
        Intent intent=this.registerReceiver(null, filter);

        //get battery status from the intent
        int status=intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        if(status==BatteryManager.BATTERY_STATUS_CHARGING)
            Toast.makeText(this,"The battery is charging.", Toast.LENGTH_LONG).show();

        else if (status==BatteryManager.BATTERY_STATUS_DISCHARGING)
            Toast.makeText(this,"The battery is discharging.", Toast.LENGTH_LONG).show();

        else if(status==BatteryManager.BATTERY_STATUS_FULL)
            Toast.makeText(this,"The battery is full.", Toast.LENGTH_LONG).show();
    }

    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
            Toast.makeText(getApplicationContext(), "Deleting Application Cache", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Thanks Mom", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = false;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
}
