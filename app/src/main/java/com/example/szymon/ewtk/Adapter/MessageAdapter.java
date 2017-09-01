package com.example.szymon.ewtk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.szymon.ewtk.Model.Message;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.SessionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 12.12.2016.
 */

public class MessageAdapter extends BaseAdapter  {

    public static final int DIRECTION_INCOMING = 1;
    public static final int DIRECTION_OUTGOING = 0;
    SessionManagement sessionManagement;
    List<Message> messagesList;
    private LayoutInflater layoutInflater;
    int userID;

    public MessageAdapter(Activity activity, List<Message> messagesList){
        this.layoutInflater = activity.getLayoutInflater();
        this.messagesList = messagesList;
        sessionManagement = new SessionManagement(activity.getApplicationContext());
        userID = Integer.parseInt(sessionManagement.getUserDetails().get(SessionManagement.USER_ID));
    }

    @Override
    public int getCount() {
        return messagesList.size();
    }

    @Override
    public Message getItem(int position) {
        return messagesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int i) {
        return messagesList.get(i).getDirection();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int direction = getItemViewType(position);
        if(convertView == null){
            int res = 0;
            if(direction == DIRECTION_INCOMING)
                res = R.layout.message_left;
            else if (direction == DIRECTION_OUTGOING || direction == userID)
                res = R.layout.message_right;

            convertView = layoutInflater.inflate(res,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.message.setText(getItem(position).getContent());

        return convertView;
    }

    private class ViewHolder {
        private TextView message;

        public ViewHolder(View v) {
            message = (TextView) v.findViewById(R.id.message_chat);
        }
    }
}