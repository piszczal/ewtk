package com.example.szymon.ewtk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.szymon.ewtk.Activity.LoginActivity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by Szymon on 02.10.2016.
 */

public class SessionManagement extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RestClient restClient;
    Context context_;
    private HttpRequestTask mAuthTask = null;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SESSION_USER";
    private static final String IS_LOGIN = "is_login";
    public static final String USER_ID = "user_id";
    public static final String INQUIRY_ID = "inquiry_id";
    public static final String KEY_ACCESS_TOKEN = "access_token";

    public SessionManagement(Context context) {
        this.restClient = new Methods(context);
        this.context_ = context;
        sharedPreferences = context_.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String userID, String access_token, String inquiryID) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_ID, userID);
        editor.putString(KEY_ACCESS_TOKEN, access_token);
        editor.putString(INQUIRY_ID, inquiryID);
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(context_, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context_.startActivity(intent);
        }
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        user.put(KEY_ACCESS_TOKEN, sharedPreferences.getString(KEY_ACCESS_TOKEN, null));
        user.put(INQUIRY_ID, sharedPreferences.getString(INQUIRY_ID, null));
        return user;
    }

    public void logoutUser() {
        mAuthTask = new HttpRequestTask( );
        mAuthTask.execute((Void) null);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params){
            String access_token = "";
            access_token = sharedPreferences.getString("access_token", access_token);
            try {
                restClient.setBearerAuth(access_token);
                restClient.logout();
                return true;
            }
            catch(HttpClientErrorException e) {
                Log.e("Logout: ", e.getLocalizedMessage(), e);
                return true;
            }catch(ResourceAccessException ex){
                return true;
            }
        }
        @Override
        protected  void onPostExecute(Boolean status) {
            mAuthTask = null;
            if(status==true)
            {
                editor.clear();
                editor.commit();
                Intent intent = new Intent(context_, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context_.startActivity(intent);
            }
        }
    }
}
