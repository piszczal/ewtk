package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 13.12.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Parcelable{

    @JsonProperty("id")
    public int ID;
    @JsonProperty("content")
    public String content;
    @JsonProperty("is_read")
    public Boolean isRead;
    @JsonProperty("creator_id")
    public int creatorID;
    @JsonProperty("conversation_id")
    public int conversationID;
    @JsonProperty("created_at")
    public String createdAT;
    @JsonProperty("updated_at")
    public String updatedAT;

    public int direction;


    public Message() {

    }

    public Message(String content, int direction){
        this.content = content;
        this.direction = direction;
    }

    public int getAnswerId(String object){
        try {
            JSONObject jObject = new JSONObject(object);

            String messageID = jObject.getString("message_id");
            return Integer.parseInt(messageID);
        }catch(JSONException e){
            Log.d("Anser parser: ", e.getLocalizedMessage(), e);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Message{" +
                "ID=" + ID +
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                ", creatorID=" + creatorID +
                ", conversationID=" + conversationID +
                ", createdAT='" + createdAT + '\'' +
                ", updatedAT='" + updatedAT + '\'' +
                ", direction=" + direction +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public String getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(String createdAT) {
        this.createdAT = createdAT;
    }

    public String getUpdatedAT() {
        return updatedAT;
    }

    public void setUpdatedAT(String updatedAT) {
        this.updatedAT = updatedAT;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.content);
        dest.writeValue(this.isRead);
        dest.writeInt(this.creatorID);
        dest.writeInt(this.conversationID);
        dest.writeString(this.createdAT);
        dest.writeString(this.updatedAT);
        dest.writeInt(this.direction);
    }

    protected Message(Parcel in) {
        this.ID = in.readInt();
        this.content = in.readString();
        this.isRead = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.creatorID = in.readInt();
        this.conversationID = in.readInt();
        this.createdAT = in.readString();
        this.updatedAT = in.readString();
        this.direction = in.readInt();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
