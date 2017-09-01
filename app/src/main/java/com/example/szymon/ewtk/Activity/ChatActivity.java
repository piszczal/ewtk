package com.example.szymon.ewtk.Activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.szymon.ewtk.Adapter.MessageAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.Model.Chat;
import com.example.szymon.ewtk.Model.Message;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.util.HttpAuthorizer;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Date;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText messageInput;
    private Button sendButton;
    private HashMap<String, String> user;
    private MessageAdapter messageAdapter;
    private Pusher pusher;
    private SessionManagement sessionManagement;
    private String user_id;
    private int conversation_id;
    private HttpRequestTask mAsyncTask=null;
    private RestClient restClient;
    private Chat messages;
    private ListView chatList;
    private Message messageAnswer;
    private String userSendName;
    private int message_id;
    private int index, top;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sessionManagement = new SessionManagement(getApplicationContext());
        user = sessionManagement.getUserDetails();
        messageInput = (EditText) findViewById(R.id.message_input);
        sendButton = (Button) findViewById(R.id.send_message);
        chatList = (ListView) findViewById(R.id.chat_list);
        sendButton.setOnClickListener(this);
        restClient = new Methods(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getString(Constants.USER_ID);
            conversation_id = extras.getInt(Constants.CONVERSATION_ID);
            userSendName = extras.getString(Constants.CONVERSATION_USER);
        }
        if (savedInstanceState != null) {
            messages = savedInstanceState.getParcelable(Constants.CHAT_STATE);
            v = chatList.getChildAt(chatList.getHeaderViewsCount());
            index = chatList.getFirstVisiblePosition() + messages.getMessages().size();
            top = (v == null) ? 0 : v.getTop();
            messageAdapter = new MessageAdapter(this,messages.getMessages());
            chatList.setAdapter(messageAdapter);
            chatList.setSelectionFromTop(index, top);
        } else {
            mAsyncTask = new HttpRequestTask(sessionManagement.getUserDetails(),null,0,this);
            mAsyncTask.execute((Void) null);
        }
        setTitle(getResources().getString(R.string.chat)+" - "+userSendName);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAuthorization(new HttpAuthentication() {
            @Override
            public String getHeaderValue() {
                return ("Bearer " + user.get(SessionManagement.KEY_ACCESS_TOKEN));
            }
        });
        HttpAuthorizer authorizer = new HttpAuthorizer(Constants.ADDRESS_URL.concat(Constants.ENDPOINT_CHAT_URL));
        authorizer.setHeaders(httpHeaders.toSingleValueMap());
        PusherOptions options = new PusherOptions().setAuthorizer(authorizer);
        options.setEncrypted(true);
        options.setCluster("eu");
        pusher = new Pusher(Constants.PUSHER_KEY, options);
        Channel channel = pusher.subscribe("for_user_" + user.get(SessionManagement.USER_ID));
        channel.bind("new_message", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                messageAnswer = new Message();
                message_id = messageAnswer.getAnswerId(data.toString());
                mAsyncTask = new HttpRequestTask(sessionManagement.getUserDetails(),null,1,null);
                mAsyncTask.execute((Void) null);
            }
        });
        pusher.connect();
    }

    @Override
    public void onBackPressed() {
        pusher.disconnect();
        super.onBackPressed();
    }

    @Override
    public void onDestroy(){
        pusher.disconnect();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        postMessage();
    }

    private void postMessage() {
        String text = messageInput.getText().toString();
        if(isOnline()) {
            if (text.equals("")) {
                return;
            } else {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("content", text);
                    String message = jsonObject.toString();
                    mAsyncTask = new HttpRequestTask(sessionManagement.getUserDetails(), message, 2, this);
                    mAsyncTask.execute((Void) null);
                } catch (JSONException ex) {
                    Log.e("Message serializer: ", ex.getLocalizedMessage(), ex);
                    return;
                }
            }
        }else{
            Toast.makeText(
                    getApplicationContext(),
                    getResources().getString(R.string.on_wifi_or_data),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    //type = 0 get all messages for conversation
    //type = 1 get answer
    //type = 2 send message
    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        String access_token;
        String message;
        ResponseEntity<Void> request;
        int type;
        String userID;
        Activity activity;

        HttpRequestTask(HashMap<String, String> session, String message, int type, Activity activity) {
            this.access_token = session.get(SessionManagement.KEY_ACCESS_TOKEN);
            this.userID = session.get(SessionManagement.USER_ID);
            this.message = message;
            this.type = type;
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                restClient.setBearerAuth(access_token);
                if(type==0)
                    messages = restClient.getAllMessages(conversation_id);
                if(type==1)
                    messageAnswer = restClient.getMessage(conversation_id,message_id);
                if(type==2)
                    request = restClient.sendMessage(message,conversation_id);
                return true;
            } catch (HttpClientErrorException ex) {
                Log.d("Chat data Error: ", ex.getLocalizedMessage(), ex);
                return false;
            } catch (HttpServerErrorException e) {
                Log.d("Chat Error: ", e.getLocalizedMessage(), e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean status) {
            mAsyncTask = null;
            if (status) {
                index = chatList.getFirstVisiblePosition() + messages.getMessages().size();
                v = chatList.getChildAt(chatList.getHeaderViewsCount());
                top = (v == null) ? 0 : v.getTop();
                if(type==0) {
                    messages = messages.setDirection(messages, Integer.parseInt(userID));
                    messageAdapter = new MessageAdapter(activity,messages.getMessages());
                    chatList.setAdapter(messageAdapter);
                    chatList.setSelectionFromTop(index, top);
                }
                if(type==1){
                    messageAnswer.setDirection(1);
                    messages.getMessages().add(messageAnswer);
                    messageAdapter.notifyDataSetChanged();
                    chatList.setSelectionFromTop(index, top);
                }
                if(type==2) {
                    messages.getMessages().add(new Message(messageInput.getText().toString(), 0));
                    messageAdapter.notifyDataSetChanged();
                    messageInput.setText("");
                    chatList.setSelectionFromTop(index, top);
                }
            }
            else{
                Toast.makeText(
                        getApplicationContext(),
                        getResources().getString(R.string.something_wrong),
                        Toast.LENGTH_LONG
                ).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAsyncTask = null;
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.CHAT_STATE, messages);
        super.onSaveInstanceState(outState);
    }

}
