package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.szymon.ewtk.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Model User like a return JSON.
 * This class implements getter, setter, variables and functions.
 * Created by Szymon on 14.08.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Parcelable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("email")
    public String email;
    @JsonProperty("name")
    public String name;
    @JsonProperty("surname")
    public String surname;
    @JsonProperty("score_points")
    public float scorePoints;
    @JsonProperty("verified")
    public int verified;
    @JsonProperty("is_profile_filled")
    public int isProfileFilled;
    @JsonProperty("created_at")
    public String createdAT;
    @JsonProperty("updated_at")
    public String updatedAT;
    @JsonProperty("have_active_inquiry")
    public Boolean haveActiveInquiry;
    @JsonProperty("active_inquiry_id")
    public int activeInquiryID;
    @JsonProperty("active_inquiry_group_id")
    public int activeInquiryGroupID;
    @JsonProperty("role")
    public Role role;
    @JsonProperty("profile")
    public Profile profile;

    public User() {

    }

    public String serializerLogin(String email, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("password", password);
            return jsonObject.toString();
        } catch (JSONException ex) {
            Log.e("User serializer: ", ex.getLocalizedMessage(), ex);
            return null;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", scorePoints=" + scorePoints +
                ", verified='" + verified + '\'' +
                ", isProfileFilled='" + isProfileFilled + '\'' +
                ", createdAT='" + createdAT + '\'' +
                ", updatedAT='" + updatedAT + '\'' +
                ", haveActiveInquiry=" + haveActiveInquiry +
                ", activeInquiryID=" + activeInquiryID +
                ", activeInquiryGroupID=" + activeInquiryGroupID +
                ", role=" + role +
                ", profile=" + profile +
                '}';
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public float getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(float scorePoints) {
        this.scorePoints = scorePoints;
    }

    public int getIsProfileFilled() {
        return isProfileFilled;
    }
    public void setIsProfileFilled(int isProfileFilled) {
        this.isProfileFilled = isProfileFilled;
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
    public Boolean getHaveActiveInquiry() {
        return haveActiveInquiry;
    }
    public void setHaveActiveInquiry(Boolean haveActiveInquiry) {
        this.haveActiveInquiry = haveActiveInquiry;
    }
    public int getActiveInquiryID() {
        return activeInquiryID;
    }
    public void setActiveInquiryID(int inquiryID) {
        this.activeInquiryID = inquiryID;
    }
    public int getActiveInquiryGroupID() {
        return activeInquiryGroupID;
    }
    public void setActiveInquiryGroupID(int groupID) {
        this.activeInquiryGroupID = groupID;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeFloat(this.scorePoints);
        dest.writeInt(this.verified);
        dest.writeInt(this.isProfileFilled);
        dest.writeString(this.createdAT);
        dest.writeString(this.updatedAT);
        dest.writeValue(this.haveActiveInquiry);
        dest.writeInt(this.activeInquiryID);
        dest.writeInt(this.activeInquiryGroupID);
        dest.writeSerializable(this.role);
        dest.writeParcelable(this.profile, flags);
    }

    protected User(Parcel in) {
        this.ID = in.readInt();
        this.email = in.readString();
        this.name = in.readString();
        this.surname = in.readString();
        this.scorePoints = in.readFloat();
        this.verified = in.readInt();
        this.isProfileFilled = in.readInt();
        this.createdAT = in.readString();
        this.updatedAT = in.readString();
        this.haveActiveInquiry = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.activeInquiryID = in.readInt();
        this.activeInquiryGroupID = in.readInt();
        this.role = (Role) in.readSerializable();
        this.profile = in.readParcelable(Profile.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
