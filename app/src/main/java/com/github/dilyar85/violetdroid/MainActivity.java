package com.github.dilyar85.violetdroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * First launched activity class
 */

public class MainActivity extends AppCompatActivity {

    final static String LOG_TAG = MainActivity.class.getSimpleName();



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addMainFragment();
    }


    /**
     * Add Main Fragment to this activity
     */
    private void addMainFragment() {

        MainFragment fragment = new MainFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

}

