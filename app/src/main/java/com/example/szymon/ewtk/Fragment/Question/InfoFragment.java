package com.example.szymon.ewtk.Fragment.Question;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.szymon.ewtk.Activity.MainActivity;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.Model.Question;
import com.example.szymon.ewtk.QuestionsManager;
import com.example.szymon.ewtk.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener{


    private Button startQuestion;
    private TextView text;
    private ImageView image;
    private List<Question> questionsList;
    private int currentQuestion;
    private QuestionsManager questionsManager;
    private Fragment fragment;
    private Bitmap bmpOriginal;

    public static InfoFragment newInstance(List<Question> questionsList, int currentQuestion) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.QUESTIONS_INSTANCE,(Serializable)questionsList);
        args.putInt(Constants.QUESTIONS_CURRENT, currentQuestion);
        fragment.setArguments(args);
        return fragment;
    }

    public List<Question> getQuestionsList(){
        return (List<Question>)getArguments().getSerializable(Constants.QUESTIONS_INSTANCE);
    }

    public int getCurrentQuestion(){
        return getArguments().getInt(Constants.QUESTIONS_CURRENT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        startQuestion = (Button) view.findViewById(R.id.startQuestion);
        text = (TextView) view.findViewById(R.id.textInfoQuestion);
        image = (ImageView) view.findViewById(R.id.summaryImage);
        if (savedInstanceState != null) {
            questionsList = (List<Question>)savedInstanceState.getSerializable(Constants.QUESTIONS_STATE);
            currentQuestion = savedInstanceState.getInt(Constants.QUESTONS_CURRENT_STATE);
        } else {
            questionsList = getQuestionsList();
            currentQuestion = getCurrentQuestion();
        }
        if(currentQuestion!=0){
            text.setText(getResources().getString(R.string.thank_for_send_answers));
            bmpOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.thank_you);
            image.setImageBitmap(bmpOriginal);
            startQuestion.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            startQuestion.setText(getResources().getString(R.string.go_to_menu));
            startQuestion.setTextColor(getResources().getColor(R.color.white));
        }
        if (questionsList != null) {
            if (questionsList.size() == 0) {
                text.setText(getResources().getString(R.string.lack_questions));
                startQuestion.setText(getResources().getString(R.string.come_back));
            }
        }
        startQuestion.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (currentQuestion == 0 && questionsList.size() != 0) {
            questionsManager = new QuestionsManager(questionsList, new ArrayList<Answer>(), 0);
            fragment = questionsManager.nextQuestion();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.activity_question, fragment, "MY FRAGMENT")
                    .addToBackStack(null);
            transaction.commit();
        }else{
            Intent mainActivity = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            startActivity(mainActivity);
            getActivity().finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.QUESTIONS_STATE, (Serializable) questionsList);
        outState.putInt(Constants.QUESTONS_CURRENT_STATE, currentQuestion);
        super.onSaveInstanceState(outState);
    }
}
