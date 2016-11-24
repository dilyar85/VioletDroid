package com.github.dilyar85.violetdroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;

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
        if (AVUser.getCurrentUser() == null) createDummyUser();
    }


    /**
     * A method to create a user, will be optimized in sign up activity.
     */
    private void createDummyUser() {

        AVUser user = new AVUser();
        user.setUsername("Dummy100");
        user.setPassword("123");
        user.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(AVException e) {

                if (e == null) {
                    Log.e(LOG_TAG, "Created a dummy user");
                } else {
                    Log.e(LOG_TAG, "Failed to create a dummy user");
                }
            }
        });

    }


    /**
     * Add Main Fragment to this activity
     */
    private void addMainFragment() {

        MainFragment fragment = new MainFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

}

