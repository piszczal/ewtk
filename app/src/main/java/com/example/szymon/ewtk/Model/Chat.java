package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 13.12.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chat implements Parcelable {

    @JsonProperty("activeConversations")
    public List<ActiveConversations> activeConversations;

    @JsonProperty("messages")
    public List<Message> messages;

    public Chat() {

    }

    @Override
    public String toString() {
        return "Chat{" +
                "activeConversations=" + activeConversations +
                ", messages=" + messages +
                '}';
    }

    public List<ActiveConversations> getActiveConversations() {
        return activeConversations;
    }

    public void setActiveConversations(List<ActiveConversations> activeConversations) {
        this.activeConversations = activeConversations;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Chat setDirection(Chat message, int userID){
        for(int i = 0; i < message.getMessages().size(); i++){
            if(message.getMessages().get(i).getCreatorID()==userID)
                message.getMessages().get(i).setDirection(0);
            else
                message.getMessages().get(i).setDirection(1);
        }
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.activeConversations);
        dest.writeList(this.messages);
    }

    protected Chat(Parcel in) {
        this.activeConversations = new ArrayList<ActiveConversations>();
        in.readList(this.activeConversations, ActiveConversations.class.getClassLoader());
        this.messages = new ArrayList<Message>();
        in.readList(this.messages, Message.class.getClassLoader());
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel source) {
            return new Chat(source);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };
}
