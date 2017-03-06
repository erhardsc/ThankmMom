package com.sumnererhard.thanksmom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by sumnererhard on 1/16/17.
 */
public class Tab2Info extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_info_fragment, container, false);

        Button btnTraverse = (Button)rootView.findViewById(R.id.btn_traverse);

        btnTraverse.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Btn Click", Toast.LENGTH_SHORT).show();
                Log.d("BTN CLICK", "vWoooooow: ");
                try {
                    Runtime.getRuntime().exec("su");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File dir = new File(".");

                traverse(dir);

            }
        });

        return rootView;
    }


    public void traverse (File dir) {
        if (dir.exists()) {
            File[] parentDir = dir.listFiles();

            for (int i = 0; i < parentDir.length; ++i) {

                File file = parentDir[i];

                if (file.isDirectory() && file.getName().equals("data")) { /// FIGURE OUT HOW TO LIST THE DIRECTORIES IN THE ANDROID ECOSYSTEM

                    Log.d("Directories", "Directory value: " + file);
                    File[] dataDir = file.listFiles();

                    for (int j = 0; j < dataDir.length; ++j){
                        File dataFile = dataDir[j];
                        Log.d("FILES", "value: " + dataFile);
                    }

                } else {
                    // do something here with the file

                }
            }
        }
    }

}
