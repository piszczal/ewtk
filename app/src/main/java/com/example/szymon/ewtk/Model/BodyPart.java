package com.example.szymon.ewtk.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Szymon on 21.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BodyPart implements Serializable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("label")
    public String label;

    @Override
    public String toString() {
        return "bodyParts{" +
                "ID=" + ID +
                ", label='" + label + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
