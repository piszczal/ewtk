package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Szymon on 05.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Disease implements Parcelable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;

    public Disease() {

    }

    @Override
    public String toString() {
        return "Disease{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    protected Disease(Parcel in) {
        this.ID = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Disease> CREATOR = new Parcelable.Creator<Disease>() {
        @Override
        public Disease createFromParcel(Parcel source) {
            return new Disease(source);
        }

        @Override
        public Disease[] newArray(int size) {
            return new Disease[size];
        }
    };
}
