package com.sumnererhard.thanksmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private batteryService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (myService == null){
             //start the service
             //int oldRuntime = myService.getSystemTime();
             Intent intent = new Intent(this, batteryService.class);
             startService(intent);
         }

    }
}


