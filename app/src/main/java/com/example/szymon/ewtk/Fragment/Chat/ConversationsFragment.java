package com.example.szymon.ewtk.Fragment.Chat;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.szymon.ewtk.Activity.ChatActivity;
import com.example.szymon.ewtk.Activity.MainActivity;
import com.example.szymon.ewtk.Adapter.ListConversationAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Fragment.ConnectionFragment;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.Model.Chat;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationsFragment extends Fragment {

    private SessionManagement sessionManagement;
    private RestClient restClient;
    private HttpRequestTask mAsyncTask = null;
    private Chat chat;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private ListView listView;
    private TextView lackConversations;

    public ConversationsFragment() {

    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversations, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getActivity().getResources().getString(R.string.conversations));
        listView = (ListView) view.findViewById(R.id.conversions_list);
        lackConversations = (TextView) view.findViewById(R.id.lack_onversations);
        sessionManagement = new SessionManagement(view.getContext());
        restClient = new Methods(getActivity().getApplicationContext());
        if (((MainActivity) getActivity()).isOnline()) {
            mAsyncTask = new HttpRequestTask(sessionManagement.getUserDetails());
            mAsyncTask.execute((Void) null);
        } else {
            ((MainActivity) getActivity()).setIsOnline(false);
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction()
                    .replace(R.id.content_main, new ConnectionFragment(), "MY FRAGMENT")
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        String access_token;
        String user_id;

        HttpRequestTask(HashMap<String, String> session) {
            this.access_token = session.get(SessionManagement.KEY_ACCESS_TOKEN);
            this.user_id = session.get(SessionManagement.USER_ID);
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                restClient.setBearerAuth(access_token);
                chat = restClient.getConversations();
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
            mAsyncTask = null;
            if (status) {
                if (chat.getActiveConversations().size() == 0)
                    lackConversations.setText(getResources().getString(R.string.lack_conversations));
                else
                    lackConversations.setText(getResources().getString(R.string.count_conversations) + ": " + chat.getActiveConversations().size());
                ListConversationAdapter conversationAdapter = new ListConversationAdapter(getActivity().getApplicationContext(), R.layout.conversation_item, chat.getActiveConversations());
                listView.setAdapter(conversationAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        Intent chatIntent = new Intent(getActivity().getApplicationContext(), ChatActivity.class);
                        chatIntent.putExtra(Constants.USER_ID, user_id);
                        chatIntent.putExtra(Constants.CONVERSATION_ID, chat.getActiveConversations().get(arg2).getID());
                        chatIntent.putExtra(Constants.CONVERSATION_USER, chat.getActiveConversations().get(arg2).getSendToUser().getName() + " " + chat.getActiveConversations().get(arg2).getSendToUser().getSurname());
                        startActivity(chatIntent);
                    }
                });
            }
        }

        @Override
        protected void onCancelled() {
            mAsyncTask = null;
        }
    }
}

