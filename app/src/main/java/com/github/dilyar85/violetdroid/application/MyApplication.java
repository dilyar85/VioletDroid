package com.github.dilyar85.violetdroid.application;
import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Application class for the project, to initialize LeanCloud backend service
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        //Initialize LeanCloud service, please do not modify this line.
        AVOSCloud.initialize(this,"Tmir9DjTPi8gcE83mSclCKAb-MdYXbMMI","sntOwsBRtiBAkBoW3yOWVf7B");
        AVOSCloud.useAVCloudUS();
    }
}
