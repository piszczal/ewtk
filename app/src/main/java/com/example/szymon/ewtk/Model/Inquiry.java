package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 18.10.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Inquiry implements Parcelable {
    @JsonProperty("id")
    public int ID;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("age_from")
    public String ageFrom;
    @JsonProperty("age_to")
    public String ageTo;
    @JsonProperty("weight_from")
    public String weightFrom;
    @JsonProperty("weight_to")
    public String weightTo;
    @JsonProperty("width_from")
    public String widthFrom;
    @JsonProperty("width_to")
    public String widthTo;
    @JsonProperty("blood_type")
    public String bloodType;
    @JsonProperty("startSingUpDate")
    public String startSingUpDate;
    @JsonProperty("endSingUpDate")
    public String endSingUpDate;
    @JsonProperty("startDate")
    public String startDate;
    @JsonProperty("endDate")
    public String endDate;
    @JsonProperty("is_active")
    public int isActive;
    @JsonProperty("is_enrollment_open")
    public int isEnrollmentOpen;
    @JsonProperty("is_visible")
    public int isVisible;
    @JsonProperty("creator_id")
    public int creatorID;
    @JsonProperty("created_at")
    public String createdAT;
    @JsonProperty("updated_at")
    public String updatedAT;

    @JsonProperty("categories")
    public List<Category> categories;
    @JsonProperty("modules")
    public List<Model> modules;
    @JsonProperty("diseases")
    public List<Disease> diseases;
    @JsonProperty("addictions")
    public List<Disease> addictions;

    public Inquiry(){

    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ageFrom='" + ageFrom + '\'' +
                ", ageTo='" + ageTo + '\'' +
                ", weightFrom='" + weightFrom + '\'' +
                ", weightTo='" + weightTo + '\'' +
                ", widthFrom='" + widthFrom + '\'' +
                ", widthTo='" + widthTo + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", startSingUpDate='" + startSingUpDate + '\'' +
                ", endSingUpDate='" + endSingUpDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", isActive=" + isActive +
                ", isEnrollmentOpen=" + isEnrollmentOpen +
                ", isVisible=" + isVisible +
                ", creatorID=" + creatorID +
                ", createdAT='" + createdAT + '\'' +
                ", updatedAT='" + updatedAT + '\'' +
                ", categories=" + categories +
                ", modules=" + modules +
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAgeFrom() {
        return ageFrom;
    }
    public void setAgeFrom(String ageFrom) {
        this.ageFrom = ageFrom;
    }
    public String getAgeTo() {
        return ageTo;
    }
    public void setAgeTo(String ageTo) {
        this.ageTo = ageTo;
    }
    public String getWeightFrom() {
        return weightFrom;
    }
    public void setWeightFrom(String weightFrom) {
        this.weightFrom = weightFrom;
    }
    public String getWeightTo() {
        return weightTo;
    }
    public void setWeightTo(String weightTo) {
        this.weightTo = weightTo;
    }
    public String getWidthFrom() {
        return widthFrom;
    }
    public void setWidthFrom(String widthFrom) {
        this.widthFrom = widthFrom;
    }
    public String getWidthTo() {
        return widthTo;
    }
    public void setWidthTo(String widthTo) {
        this.widthTo = widthTo;
    }
    public String getBloodType() {
        return bloodType;
    }
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    public String getStartSingUpDate() {
        return startSingUpDate;
    }
    public void setStartSingUpDate(String startSingUpDate) {
        this.startSingUpDate = startSingUpDate;
    }
    public String getEndSingUpDate() {
        return endSingUpDate;
    }
    public void setEndSingUpDate(String endSingUpDate) {
        this.endSingUpDate = endSingUpDate;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getIsActive() {
        return isActive;
    }
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
    public int getIsEnrollmentOpen() {
        return isEnrollmentOpen;
    }
    public void setIsEnrollmentOpen(int isEnrollmentOpen) {
        this.isEnrollmentOpen = isEnrollmentOpen;
    }
    public int getIsVisible() {
        return isVisible;
    }
    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }
    public int getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
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
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public List<Model> getModules() {
        return modules;
    }
    public void setModules(List<Model> modules) {
        this.modules = modules;
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
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.ageFrom);
        dest.writeString(this.ageTo);
        dest.writeString(this.weightFrom);
        dest.writeString(this.weightTo);
        dest.writeString(this.widthFrom);
        dest.writeString(this.widthTo);
        dest.writeString(this.bloodType);
        dest.writeString(this.startSingUpDate);
        dest.writeString(this.endSingUpDate);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeInt(this.isActive);
        dest.writeInt(this.isEnrollmentOpen);
        dest.writeInt(this.isVisible);
        dest.writeInt(this.creatorID);
        dest.writeString(this.createdAT);
        dest.writeString(this.updatedAT);
        dest.writeList(this.categories);
        dest.writeList(this.modules);
        dest.writeTypedList(this.diseases);
        dest.writeTypedList(this.addictions);
    }

    protected Inquiry(Parcel in) {
        this.ID = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.ageFrom = in.readString();
        this.ageTo = in.readString();
        this.weightFrom = in.readString();
        this.weightTo = in.readString();
        this.widthFrom = in.readString();
        this.widthTo = in.readString();
        this.bloodType = in.readString();
        this.startSingUpDate = in.readString();
        this.endSingUpDate = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.isActive = in.readInt();
        this.isEnrollmentOpen = in.readInt();
        this.isVisible = in.readInt();
        this.creatorID = in.readInt();
        this.createdAT = in.readString();
        this.updatedAT = in.readString();
        this.categories = new ArrayList<Category>();
        in.readList(this.categories, Category.class.getClassLoader());
        this.modules = new ArrayList<Model>();
        in.readList(this.modules, Model.class.getClassLoader());
        this.diseases = in.createTypedArrayList(Disease.CREATOR);
        this.addictions = in.createTypedArrayList(Disease.CREATOR);
    }

    public static final Parcelable.Creator<Inquiry> CREATOR = new Parcelable.Creator<Inquiry>() {
        @Override
        public Inquiry createFromParcel(Parcel source) {
            return new Inquiry(source);
        }

        @Override
        public Inquiry[] newArray(int size) {
            return new Inquiry[size];
        }
    };
}
