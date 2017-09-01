package com.example.szymon.ewtk.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.szymon.ewtk.Model.ActiveConversations;
import com.example.szymon.ewtk.R;

import java.util.List;


/**
 * Created by Szymon on 13.12.2016.
 */

public class ListConversationAdapter extends ArrayAdapter<ActiveConversations> {

    private Context activity;
    private List<ActiveConversations> conversationsList;

    public ListConversationAdapter(Context context, int resource, List<ActiveConversations> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.conversationsList = objects;
    }

    @Override
    public int getCount() {
        return conversationsList.size();
    }

    @Override
    public ActiveConversations getItem(int position) {
        return conversationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.conversation_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.email.setText(getItem(position).getSendToUser().getEmail());
        holder.userName.setText(getItem(position).getSendToUser().getName()+" "+getItem(position).getSendToUser().getSurname());

        if(getItem(position).getUnreadMsgCounter()>0){
            holder.fireImageChat.setVisibility(View.VISIBLE);
            holder.unreadMSG.setVisibility(View.VISIBLE);
            holder.unreadMSG.setText(String.valueOf(getItem(position).getUnreadMsgCounter()));
            holder.chatActive.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
        }else{
            holder.fireImageChat.setVisibility(View.INVISIBLE);
            holder.unreadMSG.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView fireImageChat;
        private TextView email;
        private TextView userName;
        private TextView unreadMSG;
        private LinearLayout chatActive;

        public ViewHolder(View v) {
            fireImageChat = (ImageView) v.findViewById(R.id.firechat);
            email = (TextView) v.findViewById(R.id.emailUserChat);
            userName = (TextView) v.findViewById(R.id.userChatName);
            unreadMSG = (TextView) v.findViewById(R.id.unreadMSG);
            chatActive = (LinearLayout) v.findViewById(R.id.chat_active);
        }
    }
}
