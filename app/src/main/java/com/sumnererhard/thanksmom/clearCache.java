package com.sumnererhard.thanksmom;

import android.content.Context;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class clearCache {

    private static final long CACHE_APP = Long.MAX_VALUE;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private CachePackageDataObserver mClearCacheObserver;

    public PackageManager setContext(Context context) {

        PackageManager PM = context.getPackageManager();
        clear(PM);
        return PM;
    }

    private void clear(PackageManager pm) {

        if (mClearCacheObserver == null) {
            mClearCacheObserver = new CachePackageDataObserver();
        }

        @SuppressWarnings("rawtypes")
        final Class[] classes = {Long.TYPE, IPackageDataObserver.class};

        Long localLong = Long.valueOf(CACHE_APP);

        try {
            Method localMethod =
                    pm.getClass().getMethod("freeStorageAndNotify", classes);
            try {
                localMethod.invoke(pm, localLong, mClearCacheObserver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
      /*
       * End of inner try-catch block
       */
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        }
        Log.d(LOG_TAG, "Cache has been cleared");
    }

    private class CachePackageDataObserver extends IPackageDataObserver.Stub {

        public void onRemoveCompleted(String packageName, boolean succeeded) {

        }
    }
}
