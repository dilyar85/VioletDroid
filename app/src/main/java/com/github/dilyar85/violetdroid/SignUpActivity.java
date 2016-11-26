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
import com.avos.avoscloud.SignUpCallback;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity class to allow user sign up a new account
 */
public class SignUpActivity extends Activity {

    static final String LOG_TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.sign_up_username_editText)
    public EditText mUsernameEditText;
    @BindView(R.id.sign_up_email_editText)
    public EditText mEmailEditText;
    @BindView(R.id.sign_up_password_editText)
    public EditText mPasswordEditText;
    @BindView(R.id.sign_up_password_confirm_editText)
    public EditText mConfirmPasswordEditText;

    private AlertDialog verifiedResultDialog;

    String inputUsername;
    String inputEmail;
    String inputPassword;
    String inputConfirmedPassword;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    }



    /**
     * Sign up a new account for user
     */
    @OnClick(R.id.sign_up_button)
    public void signUpNewAccount() {

        inputUsername = mUsernameEditText.getText().toString();
        inputEmail = mEmailEditText.getText().toString();
        inputPassword = mPasswordEditText.getText().toString();
        inputConfirmedPassword = mConfirmPasswordEditText.getText().toString();

        if (isCorrectInput()) {
            showLoadingDialog();
            AVUser user = new AVUser();
            user.setUsername(inputUsername);
            user.setEmail(inputEmail);
            user.setPassword(inputPassword);
            user.signUpInBackground(new SignUpCallback() {

                public void done(AVException e) {

                    if (e == null) {

                        verifiedResultDialog.dismiss();
                        showShortToast(R.string.toast_signup_successfully);
                        TimerTask task = new TimerTask() {

                            @Override
                            public void run() {

                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                finish();
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 2500);

                    } else {
                        verifiedResultDialog.dismiss();
                        String message = e.getMessage();
                        Log.d(LOG_TAG, "Failed to create account. Message: " + message);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }



    /**
     * Go back to LoginActivity
     */
    @OnClick(R.id.sign_up_return_textView)
    public void returnLogInActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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



    /**
     * A helper method to check if user has correct inputs
     *
     * @return true if user input correctly, otherwise return false
     */
    public boolean isCorrectInput() {

        if (inputUsername.isEmpty()) {
            showShortToast(R.string.type_username_hint);
            return false;
        } else if (inputEmail.isEmpty()) {
            showShortToast(R.string.type_email_hint);
            return false;
        } else if (inputPassword.isEmpty()) {
            showShortToast(R.string.type_password_hint);
            return false;
        } else if (inputConfirmedPassword.isEmpty() || !inputConfirmedPassword.equals(inputPassword)) {
            showShortToast(R.string.no_match_password_hint);
            return false;
        }
        return true;
    }

}
