package com.example.szymon.ewtk.Fragment.Question;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Activity.LoginActivity;
import com.example.szymon.ewtk.Activity.MainActivity;
import com.example.szymon.ewtk.Activity.QuestionActivity;
import com.example.szymon.ewtk.Adapter.AnswerAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.DialogManager;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.Model.Question;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {

    private List<Answer> answersList;
    private HashMap<String, String> user;
    private AnswerAdapter answerAdapter;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private ListView summaryList;
    private FloatingActionButton sendButton;
    private FloatingActionButton cancelButton;
    private RestClient restClient;
    private HttpRequestTask mAuthTask = null;
    private SessionManagement sessionManagement;

    public static SummaryFragment newInstance(List<Answer> answersList) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ANSWERS_INSTANCE, (Serializable) answersList);
        fragment.setArguments(args);
        return fragment;
    }

    public List<Answer> getAnswers() {
        return (List<Answer>) getArguments().getSerializable(Constants.ANSWERS_INSTANCE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        ((QuestionActivity) getActivity()).changeToolbar(false);
        getActivity().setTitle(getString(R.string.summary));
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        user = sessionManagement.getUserDetails();
        restClient = new Methods(getActivity().getApplicationContext());
        summaryList = (ListView) view.findViewById(R.id.summaryList);
        sendButton = (FloatingActionButton) view.findViewById(R.id.send_sum);
        cancelButton = (FloatingActionButton) view.findViewById(R.id.cancel_sum);
        sendButton.setImageResource(R.drawable.ic_send);
        cancelButton.setImageResource(R.drawable.ic_close);
        ListView summaryList = (ListView) view.findViewById(R.id.summaryList);

        if (savedInstanceState != null) {
            answersList = (List<Answer>) savedInstanceState.getSerializable(Constants.ANSWERS_STATE);
        } else {
            answersList = getAnswers();
        }

        answerAdapter = new AnswerAdapter(getActivity().getApplicationContext(), R.layout.diseases_item, answersList);
        mDrawableBuilder = TextDrawable.builder()
                .roundRect(10);

        answerAdapter = new AnswerAdapter(getActivity().getApplicationContext(), R.layout.diseases_item, answersList);
        summaryList.setAdapter(answerAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOnline()){
                    Toast.makeText(getContext(),getResources().getString(R.string.on_wifi_or_data),Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuthTask = new HttpRequestTask(user, new Answer().serializerData(answersList), ((QuestionActivity) getActivity()).getModelID(), Integer.parseInt(user.get(SessionManagement.INQUIRY_ID)), ((QuestionActivity) getActivity()).getModelExecution());
                    mAuthTask.execute((Void) null);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((QuestionActivity) getActivity()).questionsExitDialog();
            }
        });
        return view;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        int modelID;
        int executionID;
        int inquiryID;
        String object;
        String access_token;
        ResponseEntity<Void> request;
        DialogManager manager;

        HttpRequestTask(HashMap<String, String> user, String object, int modelID, int inquiryID, int executionID) {
            this.modelID = modelID;
            this.access_token = user.get(SessionManagement.KEY_ACCESS_TOKEN);
            this.executionID = executionID;
            this.inquiryID = inquiryID;
            this.object = object;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                restClient.setBearerAuth(access_token);
                request = restClient.sendAnswers(object, inquiryID, modelID, executionID);
                return true;
            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                    getActivity().runOnUiThread(new Runnable(){
                        public void run() {
                            Toast.makeText(getContext(),getResources().getString(R.string.no_authorization),Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            manager.showToast(getContext(), getString(R.string.time_out));
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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.activity_question, new InfoFragment().newInstance(null,1), "MY FRAGMENT")
                        .addToBackStack(null);
                transaction.commit();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
