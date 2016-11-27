package com.github.dilyar85.violetdroid;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Login Activity to allow users log in with their accounts
 */
public class LoginActivity extends Activity {

    static final  String LOG_TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.log_in_username_editText)
    EditText mUsernameEditText;
    @BindView(R.id.log_in_password_editText)
    EditText mPasswordEditText;

    private String inputUserName;
    private String inputPassword;

    private AlertDialog verifiedResultDialog;

    static final String EXTRA_KEY_TYPED_USERNAME = "extra_key_typed_username";
    static final String EXTRA_KEY_TYPED_PASSWORD = "extra_key_typed_password";






    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        String typedUsername = intent.getStringExtra(EXTRA_KEY_TYPED_USERNAME);
        if(typedUsername != null) mUsernameEditText.setText(typedUsername);
        String typedPassword = intent.getStringExtra(EXTRA_KEY_TYPED_PASSWORD);
        if(typedPassword != null) mPasswordEditText.setText(typedPassword);

    }

    /**
     * Check user's input info and try to log in.
     */
    @OnClick(R.id.log_in_button)
    public void clickLogInButton() {

        if(checkEditTextBlank()) {
            showLoadingDialog();
            verifyAccount();
        }

    }



    /**
     * Helper method to check if user's input is blank
     * @return true if there is no blank, otherwise return false
     */
    private boolean checkEditTextBlank() {

        inputUserName = mUsernameEditText.getText().toString();
        inputPassword = mPasswordEditText.getText().toString();

        if (inputUserName.length() <=0) {
            showShortToast(R.string.toast_username_blank);
            return false;
        }
        if (inputPassword.length()<=0) {
            showShortToast(R.string.toast_password_blank);
            return false;
        }
        return true;
    }



    /**
     * Start the sign up activity, carrying username and password if typed
     */
    @OnClick(R.id.log_in_sign_up_textView)
    public void clickSignUpTextView() {

        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra(EXTRA_KEY_TYPED_USERNAME, mUsernameEditText.getText().toString());
        intent.putExtra(EXTRA_KEY_TYPED_PASSWORD, mPasswordEditText.getText().toString());
        startActivity(intent);
    }



    /**
     * Helper method to verify user input account info
     */
    private void verifyAccount() {

        AVUser.logInInBackground(inputUserName, inputPassword, new LogInCallback<AVUser>() {

            @Override
            public void done(AVUser avUser, AVException e) {
                verifiedResultDialog.dismiss();
                if (e == null) {
                    showShortToast(R.string.toast_login_successfully);
                    TimerTask task = new TimerTask() {

                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 1000);
                } else {

                    String originalMessage = e.getMessage();
                    int startIndexOfError = originalMessage.lastIndexOf("error") + 7;
                    String errorMessage = originalMessage.substring(startIndexOfError, originalMessage.length() - 1);
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, "Fail to Log In. Full Message from Cloud: " + e.getMessage());

                }
            }
        });

    }



    /**
     * A helper method to show loading dialog
     */
    private void showLoadingDialog() {

        verifiedResultDialog = new AlertDialog.Builder(this).create();
        ImageView loadingImageview = new ImageView(this);
        loadingImageview.setBackgroundResource(R.drawable.loading_anim);
        verifiedResultDialog.setView(loadingImageview);
        verifiedResultDialog.setCancelable(false);
        verifiedResultDialog.show();
        AnimationDrawable loadingAnimationDrawable = (AnimationDrawable) loadingImageview.getBackground();
        loadingAnimationDrawable.start();
        WindowManager.LayoutParams lp = verifiedResultDialog.getWindow().getAttributes();
        lp.height = 500;
        lp.width = 500;
        verifiedResultDialog.getWindow().setAttributes(lp);
    }



    /**
     * Helper method to show the toast
     *
     * @param stringResource the id of string resource
     */
    private void showShortToast(int stringResource) {

        Toast toast = Toast.makeText(this, getString(stringResource), Toast.LENGTH_SHORT);
        toast.show();
    }


}
