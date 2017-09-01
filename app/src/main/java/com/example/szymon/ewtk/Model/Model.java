package com.example.szymon.ewtk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 04.10.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Model implements Parcelable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("module_execution")
    public List<ModelExecution> modelExecution;
    @JsonProperty("questions")
    public List<Question> questionList;


    @Override
    public String toString() {
        return "Model{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", modelExecution=" + modelExecution +
                ", questionList=" + questionList +
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
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public List<ModelExecution> getModelExecution() {
        return modelExecution;
    }
    public void setModelExecution(List<ModelExecution> modelExecution) {
        this.modelExecution = modelExecution;
    }
    public List<Question> getQuestionList() {
        return questionList;
    }
    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }


    public Model() {
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
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeList(this.modelExecution);
        dest.writeList(this.questionList);
    }

    protected Model(Parcel in) {
        this.ID = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.modelExecution = new ArrayList<ModelExecution>();
        in.readList(this.modelExecution, ModelExecution.class.getClassLoader());
        this.questionList = new ArrayList<Question>();
        in.readList(this.questionList, Question.class.getClassLoader());
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
