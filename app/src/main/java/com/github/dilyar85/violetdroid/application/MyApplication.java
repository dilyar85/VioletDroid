package com.github.dilyar85.violetdroid.application;
import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Application class
 */

public class MyApplication extends Application {

    private static MyApplication sInstance;



    /**
     * Get application instance
     * @return the application instance
     */
    public static Application getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        //Initialize LeanCloud service, please do not modify this line.
        sInstance = this;
        AVOSCloud.initialize(this,"Tmir9DjTPi8gcE83mSclCKAb-MdYXbMMI","sntOwsBRtiBAkBoW3yOWVf7B");
        AVOSCloud.useAVCloudUS();

    }
}