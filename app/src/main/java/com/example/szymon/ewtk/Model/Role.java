package com.example.szymon.ewtk.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Szymon on 28.09.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role implements Serializable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("name")
    public String name;
    @JsonProperty("label")
    public String label;
    @JsonProperty("polish_label")
    public String polishLabel;

    public Role() {

    }

    @Override
    public String toString() {
        return "Role{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", polishLabel='" + polishLabel + '\'' +
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
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getPolishLabel() {
        return polishLabel;
    }
    public void setPolishLabel(String polishLabel) {
        this.polishLabel = polishLabel;
    }
}
