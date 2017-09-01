package com.example.szymon.ewtk;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.szymon.ewtk.Fragment.Question.BodyFragment;
import com.example.szymon.ewtk.Fragment.Question.FileFragment;
import com.example.szymon.ewtk.Fragment.Question.PollFragment;
import com.example.szymon.ewtk.Fragment.Question.ScaleFragment;
import com.example.szymon.ewtk.Fragment.Question.SummaryFragment;
import com.example.szymon.ewtk.Fragment.Question.TextInputFragment;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.Model.Question;

import java.util.List;

/**
 * Created by Szymon on 16.11.2016.
 */

public class QuestionsManager {

    private int currentQuestion;
    private List<Question> questionsList;
    private List<Answer> answersList;

    public QuestionsManager(List<Question> questionsList, List<Answer> answersList, int currentQuestion) {
        this.questionsList = questionsList;
        this.answersList = answersList;
        this.currentQuestion = currentQuestion;
    }

    public Fragment nextQuestion() {
        Fragment fragment = null;
        if (questionsList.size() > currentQuestion) {
            int questionType = questionsList.get(currentQuestion).getQuestionTypeID();
            switch (questionType) {
                case 1:
                    fragment = new TextInputFragment().newInstance(questionsList, answersList, currentQuestion);
                    break;
                case 2:
                    fragment = new FileFragment().newInstance(questionsList, answersList, currentQuestion);
                    break;
                case 3:
                    fragment = new PollFragment().newInstance(questionsList, answersList, currentQuestion);
                    break;
                case 4:
                    fragment = new ScaleFragment().newInstance(questionsList, answersList, currentQuestion);
                    break;
                case 5:
                    fragment = new BodyFragment().newInstance(questionsList, answersList, currentQuestion);
                    break;
                default:
                    break;
            }

        } else {
            fragment = new SummaryFragment().newInstance(answersList);
        }
        return fragment;
    }

    public boolean checkValidationQuestion(int typeQuestion){
        if(typeQuestion == 1){
            Log.d("fragm text", "true");
        }
        return true;
    }
}
