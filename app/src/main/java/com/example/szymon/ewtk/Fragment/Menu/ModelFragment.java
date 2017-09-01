package com.example.szymon.ewtk.Fragment.Menu;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.szymon.ewtk.Activity.MainActivity;
import com.example.szymon.ewtk.Adapter.ModelAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Fragment.ConnectionFragment;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.szymon.ewtk.Model.Model;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModelFragment extends Fragment implements ModelAdapter.ViewHolder.ClickListener {

    private SessionManagement sessionManagement;
    private HashMap<String, String> user;
    private List<Model> modelsList;
    private RecyclerView recyclerView;
    private ModelFragment.HttpRequestTask mAuthTask = null;
    private ModelAdapter modelAdapter;
    private RestClient restClient;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    public ModelFragment() {
    }

    @Override
    public final void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        modelAdapter = new ModelAdapter(this);
        if(((MainActivity)getActivity()).isOnline()) {
            if (savedInstanceState == null) {
                restClient = new Methods(getActivity().getApplicationContext());
                sessionManagement = new SessionManagement(getActivity().getApplicationContext());
                user = sessionManagement.getUserDetails();
                mAuthTask = new HttpRequestTask(user);
                mAuthTask.execute((Void) null);
            } else {
                modelsList = savedInstanceState.getParcelableArrayList(Constants.MODELS_LIST_STATE);
            }
        }
        else{
            ((MainActivity)getActivity()).setIsOnline(false);
            fragmentManager = getActivity().getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction()
                    .replace(R.id.content_main, new ConnectionFragment(), "MY FRAGMENT")
                    .addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_model, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.my_modules);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        setModelAdapter(modelsList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.MODELS_LIST_STATE, (ArrayList<? extends Parcelable>)modelsList);
        super.onSaveInstanceState(outState);
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
    public void onItemClicked(int position) {
        showModelDetails(position);
    }

    private void showModelDetails(int position) {
        fragmentManager = getActivity().getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.content_main, new ModelDetailsFragment().newInstance(modelsList.get(position)), "MY FRAGMENT")
                .addToBackStack(null);
        transaction.commit();
    }

    private void setModelAdapter(List<Model> modelsList){
        if (modelsList != null) {
            modelAdapter.setModelList(modelsList);
            recyclerView.setAdapter(modelAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        String inquiryID;
        String access_token;

        HttpRequestTask(HashMap<String, String> user) {
            this.inquiryID = user.get(SessionManagement.INQUIRY_ID);
            this.access_token = user.get(SessionManagement.KEY_ACCESS_TOKEN);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                restClient.setBearerAuth(access_token);
                modelsList = restClient.getModelsList(Integer.parseInt(inquiryID));
                return true;
            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getResources().getString(R.string.expired_session), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getResources().getString(R.string.time_out), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                }
            } catch (ResourceAccessException e) {
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
                setModelAdapter(modelsList);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
