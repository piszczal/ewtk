package com.example.szymon.ewtk.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Szymon on 22.11.2016.
 */

public class Answer implements Serializable {

    public String title;
    public String data;
    public String value;
    public int questionID;
    public int bodyPartID;
    public int questionTypeID;

    public Answer(String title, String data, int questionID, int questionTypeID, int bodyPartID) {
        this.title = title;
        this.data = data;
        this.value = data;
        this.questionID = questionID;
        this.questionTypeID = questionTypeID;
        this.bodyPartID = bodyPartID;
    }

    public Answer(String title, String data, String value, int questionID, int questionTypeID) {
        this.title = title;
        this.data = data;
        this.value = value;
        this.questionID = questionID;
        this.questionTypeID = questionTypeID;
        this.bodyPartID = 13;
    }

    public Answer() {

    }

    public String serializerData(List<Answer> answerList) {
        try {
            JSONObject jsonObject = null;
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < answerList.size(); i++) {
                jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("data", answerList.get(i).getData());
                    jsonObject.accumulate("question_id", answerList.get(i).getQuestionID());
                    if (answerList.get(i).getQuestionTypeID() == 5) {
                        jsonObject.accumulate("body_part_id", answerList.get(i).getBodyPartID());
                    }
                } catch (JSONException ex) {
                    Log.e("Access serializer: ", ex.getLocalizedMessage(), ex);
                }
                jsonArray.put(jsonObject);
            }
            JSONObject answerObject = new JSONObject();
            answerObject.accumulate("answers", jsonArray);
            return answerObject.toString();
        } catch (JSONException ex) {
            Log.e("Access serializer: ", ex.getLocalizedMessage(), ex);
            return null;
        }
    }

    @Override
    public String toString() {
        return "Answer{" +
                "title='" + title + '\'' +
                ", data='" + data + '\'' +
                ", value='" + value + '\'' +
                ", questionID=" + questionID +
                ", bodyPartID=" + bodyPartID +
                ", questionTypeID=" + questionTypeID +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getBodyPartID() {
        return bodyPartID;
    }

    public void setBodyPartID(int bodyPartID) {
        this.bodyPartID = bodyPartID;
    }

    public int getQuestionTypeID() {
        return questionTypeID;
    }

    public void setQuestionTypeID(int questionTypeID) {
        this.questionTypeID = questionTypeID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
