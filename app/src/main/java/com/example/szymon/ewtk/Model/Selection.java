package com.example.szymon.ewtk.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Szymon on 21.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Selection implements Serializable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;

    @JsonProperty("selection_answers")
    public List<SelectionAnswer> selectionAnswersList;

    @Override
    public String toString() {
        return "Selection{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", selectionsAnswerList=" + selectionAnswersList +
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

    public List<SelectionAnswer> getSelectionAnswersList() {
        return selectionAnswersList;
    }

    public void setSelectionAnswersList(List<SelectionAnswer> selectionAnswersList) {
        this.selectionAnswersList = selectionAnswersList;
    }
}

