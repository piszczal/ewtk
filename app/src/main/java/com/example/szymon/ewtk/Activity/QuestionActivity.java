package com.example.szymon.ewtk.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.DialogManager;
import com.example.szymon.ewtk.Fragment.Question.InfoFragment;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.Model.Model;
import com.example.szymon.ewtk.QuestionsManager;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {

    private int MODEL_ID;
    private static int modelExecution;
    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private Model model;
    private RestClient restClient;
    private HttpRequestTask mAuthTask = null;
    private String gender;
    private ProgressBar questionProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionProgress = (ProgressBar) findViewById(R.id.question_progress);
        sessionManagement = new SessionManagement(getApplicationContext());
        user = sessionManagement.getUserDetails();
        restClient = new Methods(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MODEL_ID = extras.getInt(Constants.MODEL_ID);
            gender = extras.getString(Constants.GENDER);
            modelExecution = extras.getInt(Constants.MODEL_EXECUTION_ID);
        }

        changeToolbar(true);
        if(savedInstanceState==null) {

            if(isOnline()) {
                showProgress(true);
                mAuthTask = new HttpRequestTask(user, MODEL_ID);
                mAuthTask.execute((Void) null);
            }else{
                Toast.makeText(this,getResources().getString(R.string.on_wifi_or_data),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        questionsExitDialog();
        return false;
    }

    @Override
    public void onBackPressed() {
        questionsExitDialog();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            questionProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            questionProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    questionProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            questionProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        int modelID;
        String access_token;

        HttpRequestTask(HashMap<String, String> user, int modelID) {
            this.modelID = modelID;
            this.access_token = user.get(SessionManagement.KEY_ACCESS_TOKEN);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                restClient.setBearerAuth(access_token);
                model = restClient.getQuestionsModel(modelID);
                return true;
            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                    runOnUiThread(new Runnable(){
                        public void run() {
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_authorization),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(),getString(R.string.time_out),Toast.LENGTH_LONG).show();
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
        protected void onPostExecute(Boolean status) {
            mAuthTask = null;
            if (status) {
                showProgress(false);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.activity_question, new InfoFragment().newInstance(model.getQuestionList(),0), "MY FRAGMENT")
                        .addToBackStack(null);
                transaction.commit();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public boolean startRequest() {
        if (!mayRequestStorage()) {
            return false;
        }else{
            return true;
        }
    }

    private boolean mayRequestStorage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 0);
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 1);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch(requestCode){
            case 0:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else{

                }
                break;
            case 1:
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }else {

                }
                break;
        }
    }

    public void changeToolbar(boolean status) {
        if(status){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        }else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void questionsExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_dialog));
        builder.setMessage(getString(R.string.message_dialog));

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });

        //No Button
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public String getGender() {
        return gender;
    }

    public int getModelID() {
        return MODEL_ID;
    }

    public int getModelExecution() {
        return modelExecution;
    }
}
