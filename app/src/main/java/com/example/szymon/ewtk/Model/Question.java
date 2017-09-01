package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 16.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements Serializable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("title")
    public String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("is_picture")
    public int isPicture;
    @JsonProperty("is_positive")
    public int isPositive;
    @JsonProperty("module_id")
    public int moduleID;
    @JsonProperty("question_type_id")
    public int questionTypeID;
    @JsonProperty("selection_id")
    public int selectionID;
    @JsonProperty("created_at")
    public String createdAT;
    @JsonProperty("updated_at")
    public String updatedAT;

    @JsonProperty("selection")
    public Selection selection;
    @JsonProperty("body_parts")
    public List<BodyPart> bodyPartsList;

    @Override
    public String toString() {
        return "Question{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPicture=" + isPicture +
                ", isPositive=" + isPositive +
                ", moduleID=" + moduleID +
                ", questionTypeID=" + questionTypeID +
                ", selectionID=" + selectionID +
                ", createdAT='" + createdAT + '\'' +
                ", updatedAT='" + updatedAT + '\'' +
                ", selection=" + selection +
                ", bodyPartsList=" + bodyPartsList +
                '}';
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getModuleID() {
        return moduleID;
    }
    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }
    public int getQuestionTypeID() {
        return questionTypeID;
    }
    public void setQuestionTypeID(int questionTypeID) {
        this.questionTypeID = questionTypeID;
    }
    public int getSelectionID() {
        return selectionID;
    }
    public void setSelectionID(int selectionID) {
        this.selectionID = selectionID;
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
    public Selection getSelection() {
        return selection;
    }
    public void setSelection(Selection selection) {
        this.selection = selection;
    }
    public List<BodyPart> getBodyPartsList() {
        return bodyPartsList;
    }
    public void setBodyPartsList(List<BodyPart> bodyPartsList) {
        this.bodyPartsList = bodyPartsList;
    }
    public int getIsPicture() {
        return isPicture;
    }
    public void setIsPicture(int isPicture) {
        this.isPicture = isPicture;
    }
    public int getIsPositive() {
        return isPositive;
    }

    public void setIsPositive(int isPositive) {
        this.isPositive = isPositive;
    }
}
