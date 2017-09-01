package com.example.szymon.ewtk.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Szymon on 21.11.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectionAnswer implements Serializable {

    @JsonProperty("id")
    public int ID;
    @JsonProperty("answer")
    public String answer;
    @JsonProperty("value")
    public String value;

    @Override
    public String toString() {
        return "selectionAnswer{" +
                "ID=" + ID +
                ", answer='" + answer + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
