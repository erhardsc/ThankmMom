package com.sumnererhard.thanksmom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by sumnererhard on 1/16/17.
 */
public class Tab2Info extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_info_fragment, container, false);

        Button btnTraverse = (Button) rootView.findViewById(R.id.btn_traverse);

        btnTraverse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clearCache clearCache = new clearCache();
                clearCache.setContext(getContext()); //Need to pass the context of this fragment in order for the PackageManager to work
            }
        });

        return rootView;
    }

}
