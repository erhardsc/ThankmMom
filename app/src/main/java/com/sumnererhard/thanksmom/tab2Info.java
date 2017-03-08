package com.sumnererhard.thanksmom;

import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sumnererhard on 1/16/17.
 */
public class Tab2Info extends Fragment {

    private static final long CACHE_APP = Long.MAX_VALUE;

    private CachePackageDataObserver mClearCacheObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab2_info_fragment, container, false);

        Button btnTraverse = (Button) rootView.findViewById(R.id.btn_traverse);

        btnTraverse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clearCache();
            }
        });

        return rootView;
    }

    void clearCache()
    {
        if (mClearCacheObserver == null)
        {
            mClearCacheObserver=new CachePackageDataObserver();
        }

        PackageManager mPM=getContext().getPackageManager();

        @SuppressWarnings("rawtypes")
        final Class[] classes= { Long.TYPE, IPackageDataObserver.class };

        Long localLong=Long.valueOf(CACHE_APP);

        try
        {
            Method localMethod=
                    mPM.getClass().getMethod("freeStorageAndNotify", classes);

      /*
       * Start of inner try-catch block
       */
            try
            {
                localMethod.invoke(mPM, localLong, mClearCacheObserver);
            }
            catch (IllegalArgumentException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
      /*
       * End of inner try-catch block
       */
        }
        catch (NoSuchMethodException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }//End of clearCache() method

    private class CachePackageDataObserver extends IPackageDataObserver.Stub
    {
        public void onRemoveCompleted(String packageName, boolean succeeded)
        {

        }//End of onRemoveCompleted() method
    }//End of CachePackageDataObserver instance inner class



//    public void traverse (File dir) {
//        if (dir.exists()) {
//            File[] parentDir = dir.listFiles();
//
//            for (int i = 0; i < parentDir.length; ++i) {
//
//                File file = parentDir[i];
//
//                if (file.isDirectory() ) { /// FIGURE OUT HOW TO LIST THE DIRECTORIES IN THE ANDROID ECOSYSTEM
//
//                    Log.d("Directories", "Directory value: " + file);
//                    File[] dataDir = file.listFiles();
////
////                    for (int j = 0; j < dataDir.length; ++j){
////                        File dataFile = dataDir[j];
////                        Log.d("FILES", "value: " + dataFile);
////                    }
//
//                } else {
//                    // do something here with the file
//
//                }
//            }
//        }
 //   }

}
