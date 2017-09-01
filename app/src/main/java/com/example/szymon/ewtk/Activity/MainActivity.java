package com.example.szymon.ewtk.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.szymon.ewtk.Fragment.Chat.ConversationsFragment;
import com.example.szymon.ewtk.Fragment.Menu.AboutAppFragment;
import com.example.szymon.ewtk.Fragment.ConnectionFragment;
import com.example.szymon.ewtk.Fragment.Menu.ContactsFragment;
import com.example.szymon.ewtk.Fragment.Menu.DashboardFragment;
import com.example.szymon.ewtk.Fragment.Menu.ModelFragment;
import com.example.szymon.ewtk.Fragment.Menu.ProfileFragment;
import com.example.szymon.ewtk.Fragment.Menu.ResearchFragment;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.Model.User;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManagement sessionManagement;
    private RestClient restClient;
    private User userData;
    private HttpRequestTask mAsyncTask;
    private String gender;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private Bundle saveInstance;
    private boolean isOnline = true;
    private ProgressBar dashboardProgress;
    private int ACCTUAL_MENU_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dashboardProgress = (ProgressBar) findViewById(R.id.dashboard_progress);
        sessionManagement = new SessionManagement(getApplicationContext());
        Log.d("Sessia: ", sessionManagement.getUserDetails().toString());
        restClient = new Methods(getApplicationContext());
        if (!sessionManagement.isLoggedIn()) {
            finish();
        }
        sessionManagement.checkLogin();

        if(!isOnline()) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction()
                    .replace(R.id.content_main, new ConnectionFragment(), "MY FRAGMENT")
                    .addToBackStack(null);
            transaction.commit();
        }else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            showProgress(true);
            mAsyncTask = new HttpRequestTask(sessionManagement.getUserDetails());
            mAsyncTask.execute((Void) null);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            saveInstance = savedInstanceState;

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(isOnline) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(isOnline) {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_logout:
                    sessionManagement.logoutUser();
                    this.finish();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public String getGender() {
        return gender;
    }
    public void setIsOnline(boolean status){
        this.isOnline = false;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(isOnline) {
            int id = item.getItemId();
            fragment = null;
            switch (id) {
                case R.id.nav_home:
                    switchFragment(new DashboardFragment().newInstance(userData), R.id.nav_research, "HOME");
                    break;
                case R.id.nav_research:
                    switchFragment(new ResearchFragment(),R.id.nav_research, "RESEARCH");
                    break;
                case R.id.nav_models:
                    switchFragment(new ModelFragment(),R.id.nav_models, "MODELS");
                    break;
                case R.id.nav_profile:
                    switchFragment(new ProfileFragment().newInstance(userData),R.id.nav_profile, "PROFILE");
                    break;
                case R.id.nav_contact:
                    switchFragment(new ContactsFragment(),R.id.nav_contact,"CONTACT");
                    fragment = new ContactsFragment();
                    break;
                case R.id.nav_logout:
                    sessionManagement.logoutUser();
                    this.finish();
                    break;
                case R.id.nav_chat:
                    switchFragment(new ConversationsFragment(),R.id.nav_chat,"CHAT");
                    fragment = new ConversationsFragment();
                    break;
                case R.id.nav_aboutApp:
                    switchFragment(new AboutAppFragment(),R.id.nav_aboutApp,"ABOUT_APP");
                    fragment = new AboutAppFragment();
                    break;
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void switchFragment(Fragment fragment, int menuID, String TAG){
        if (fragment != null) {
            ACCTUAL_MENU_ID = menuID;
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.content_main, fragment, TAG).addToBackStack(null);
            transaction.commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            dashboardProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            dashboardProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    dashboardProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            dashboardProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        String access_token;

        HttpRequestTask(HashMap<String, String> session) {
            this.access_token = session.get(SessionManagement.KEY_ACCESS_TOKEN);
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                restClient.setBearerAuth(access_token);
                userData = restClient.userData();
                gender = userData.getProfile().getGender();
                return true;
            } catch (HttpClientErrorException ex) {
                Log.d("User data Error: ", ex.getLocalizedMessage(), ex);
                return false;
            } catch (HttpServerErrorException e) {
                Log.d("User data Error: ", e.getLocalizedMessage(), e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean status) {
            mAsyncTask = null;
            if (!status) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                setFinishOnTouchOutside(false);
                builder.setTitle(getResources().getString(R.string.expired_session));
                builder.setMessage(getResources().getString(R.string.you_logged_out));

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManagement.logoutUser();
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else{
                showProgress(false);
                if (saveInstance == null) {
                    fragmentManager = getSupportFragmentManager();
                    transaction = fragmentManager.beginTransaction();
                    //   .replace(R.id.content_main, new DashboardFragment(), "MY FRAGMENT")
                    //   .addToBackStack(null);
                    transaction.add(R.id.content_main, new DashboardFragment().newInstance(userData)).commit();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAsyncTask = null;
        }
    }

}