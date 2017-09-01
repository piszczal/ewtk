package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Szymon on 13.12.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveConversations implements Parcelable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("participant_1_id")
    public int participant1ID;
    @JsonProperty("participant_2_id")
    public int participant2ID;
    @JsonProperty("created_at")
    public String createdAT;
    @JsonProperty("updated_at")
    public String updatedAT;
    @JsonProperty("unread_msg_counter")
    public int unreadMsgCounter;

    @JsonProperty("sendToUser")
    public User sendToUser;
    @JsonProperty("participant_1")
    public User participant1;

    public ActiveConversations() {

    }

    @Override
    public String toString() {
        return "ActiveConversations{" +
                "ID=" + ID +
                ", participant1ID=" + participant1ID +
                ", participant2ID=" + participant2ID +
                ", createdAT='" + createdAT + '\'' +
                ", updatedAT='" + updatedAT + '\'' +
                ", unreadMsgCounter=" + unreadMsgCounter +
                ", sendToUser=" + sendToUser +
                ", participant1=" + participant1 +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getParticipant1ID() {
        return participant1ID;
    }

    public void setParticipant1ID(int participant1ID) {
        this.participant1ID = participant1ID;
    }

    public int getParticipant2ID() {
        return participant2ID;
    }

    public void setParticipant2ID(int participant2ID) {
        this.participant2ID = participant2ID;
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

    public User getSendToUser() {
        return sendToUser;
    }

    public void setSendToUser(User sendToUser) {
        this.sendToUser = sendToUser;
    }

    public User getParticipant1() {
        return participant1;
    }

    public void setParticipant1(User participant1) {
        this.participant1 = participant1;
    }

    public int getUnreadMsgCounter() {
        return unreadMsgCounter;
    }

    public void setUnreadMsgCounter(int unreadMsgCounter) {
        this.unreadMsgCounter = unreadMsgCounter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeInt(this.participant1ID);
        dest.writeInt(this.participant2ID);
        dest.writeString(this.createdAT);
        dest.writeString(this.updatedAT);
        dest.writeInt(this.unreadMsgCounter);
        dest.writeParcelable(this.sendToUser, flags);
        dest.writeParcelable(this.participant1, flags);
    }

    protected ActiveConversations(Parcel in) {
        this.ID = in.readInt();
        this.participant1ID = in.readInt();
        this.participant2ID = in.readInt();
        this.createdAT = in.readString();
        this.updatedAT = in.readString();
        this.unreadMsgCounter = in.readInt();
        this.sendToUser = in.readParcelable(User.class.getClassLoader());
        this.participant1 = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<ActiveConversations> CREATOR = new Creator<ActiveConversations>() {
        @Override
        public ActiveConversations createFromParcel(Parcel source) {
            return new ActiveConversations(source);
        }

        @Override
        public ActiveConversations[] newArray(int size) {
            return new ActiveConversations[size];
        }
    };
}
