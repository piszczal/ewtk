package com.example.szymon.ewtk.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Time;

/**
 * Created by Szymon on 14.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelExecution implements Serializable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("title")
    public String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("day_of_week")
    public int dayOfWeek;
    @JsonProperty("executionTime")
    public Time executionTime;

    public ModelExecution(){

    }

    @Override
    public String toString() {
        return "ModelExecution{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", executionTime=" + executionTime +
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
    public int getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public Time getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(Time executionTime) {
        this.executionTime = executionTime;
    }
}
