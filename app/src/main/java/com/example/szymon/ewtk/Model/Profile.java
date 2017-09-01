package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Szymon on 20.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile implements Parcelable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("user_id")
    public int userID;
    @JsonProperty("pesel")
    public String pesel;
    @JsonProperty("phone")
    public String phone;
    @JsonProperty("birth_date")
    public String birthDate;
    @JsonProperty("age")
    public String age;
    @JsonProperty("weight")
    public float weight;
    @JsonProperty("width")
    public int width;
    @JsonProperty("bloodtype")
    public String bloodType;
    @JsonProperty("gender")
    public String gender;
    @JsonProperty("street")
    public String street;
    @JsonProperty("zipcode")
    public String zipcode;
    @JsonProperty("town")
    public String town;
    @JsonProperty("aboutme")
    public String aboutMe;
    @JsonProperty("diseases")
    public List<Disease> diseases;
    @JsonProperty("addictions")
    public List<Disease> addictions;

    public Profile(){

    }


    @Override
    public String toString() {
        return "Profile{" +
                "ID=" + ID +
                ", userID=" + userID +
                ", pesel='" + pesel + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", age='" + age + '\'' +
                ", weight=" + weight +
                ", width=" + width +
                ", bloodType='" + bloodType + '\'' +
                ", gender='" + gender + '\'' +
                ", street='" + street + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", town='" + town + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", diseases=" + diseases +
                ", addictions=" + addictions +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public List<Disease> getAddictions() {
        return addictions;
    }

    public void setAddictions(List<Disease> addictions) {
        this.addictions = addictions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ID);
        dest.writeInt(this.userID);
        dest.writeString(this.pesel);
        dest.writeString(this.phone);
        dest.writeString(this.birthDate);
        dest.writeString(this.age);
        dest.writeFloat(this.weight);
        dest.writeInt(this.width);
        dest.writeString(this.bloodType);
        dest.writeString(this.gender);
        dest.writeString(this.street);
        dest.writeString(this.zipcode);
        dest.writeString(this.town);
        dest.writeString(this.aboutMe);
        dest.writeTypedList(this.diseases);
        dest.writeTypedList(this.addictions);
    }

    protected Profile(Parcel in) {
        this.ID = in.readInt();
        this.userID = in.readInt();
        this.pesel = in.readString();
        this.phone = in.readString();
        this.birthDate = in.readString();
        this.age = in.readString();
        this.weight = in.readFloat();
        this.width = in.readInt();
        this.bloodType = in.readString();
        this.gender = in.readString();
        this.street = in.readString();
        this.zipcode = in.readString();
        this.town = in.readString();
        this.aboutMe = in.readString();
        this.diseases = in.createTypedArrayList(Disease.CREATOR);
        this.addictions = in.createTypedArrayList(Disease.CREATOR);
    }

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
