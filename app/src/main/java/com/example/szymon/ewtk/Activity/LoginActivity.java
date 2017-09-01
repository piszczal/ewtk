package com.example.szymon.ewtk.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.szymon.ewtk.DialogManager;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

import com.example.szymon.ewtk.Model.AccessToken;
import com.example.szymon.ewtk.Model.User;

public class LoginActivity extends Activity implements View.OnClickListener {

    DialogManager manager;
    private HttpRequestTask mAuthTask = null;
    private RestClient restClient;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private SessionManagement sessionManagement;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.title_activity_login));
        setContentView(R.layout.activity_login);
        this.restClient = new Methods(getApplicationContext());
        cardView = (CardView) findViewById(R.id.loginCard);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        sessionManagement = new SessionManagement(getApplicationContext());
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    onClick(textView);
                    return true;
                }
                return false;
            }
        });
        mEmailView.setText("normaluser@gmail.com");
        mPasswordView.setText("normaluser");
        cardView.setOnClickListener(this);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (mAuthTask != null) {
            return;
        }
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new HttpRequestTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean checkProfile(User user){
        if(user.getRole().getName().equals("user") && user.getHaveActiveInquiry()==true && user.getIsProfileFilled()==1  && user.getVerified()==1) {
            return true;
        } else if (user.getIsProfileFilled() == 0) {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.not_complete_profile),Toast.LENGTH_LONG).show();
            return false;
        }else if(!user.getRole().getName().equals("user")){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.not_patient),Toast.LENGTH_LONG).show();
            return false;
        }else if(!user.getHaveActiveInquiry()){
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.active_research),Toast.LENGTH_LONG).show();
            return false;
        }else{
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    private class HttpRequestTask extends  AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private AccessToken accessToken;
        private User user;

        HttpRequestTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            manager = new DialogManager();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                user = restClient.login(new User().serializerLogin(mEmail, mPassword));
                accessToken = restClient.getAccessToken(new AccessToken().serializer(user, mPassword));
                restClient.setBearerAuth(accessToken.getAccessToken());
                return true;
            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                    runOnUiThread(new Runnable(){
                        public void run() {
                            if(e.getResponseBodyAsString().equals("{\"error\":\"Please verify your e-mail adress\"}")){
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_activate_account),Toast.LENGTH_LONG).show();
                            }else{
                                manager.showToast(getApplicationContext(), getString(R.string.error_email_or_password));
                            }
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            manager.showToast(getApplicationContext(), getString(R.string.time_out));
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                }
            }catch (ResourceAccessException e) {
                if (e.getCause() instanceof SocketTimeoutException) {
                    Log.e("Request time out", e.getMessage(), e);
                }
            }
            return false;
        }
        @Override
        protected  void onPostExecute(Boolean status) {
            mAuthTask = null;
            if(status){
                if(checkProfile(user)){
                    sessionManagement.createLoginSession(String.valueOf(user.getID()), accessToken.getAccessToken(), String.valueOf(user.getActiveInquiryID()));
                    Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }else
                    showProgress(false);
            }
            else
                showProgress(false);
        }
    }
}
