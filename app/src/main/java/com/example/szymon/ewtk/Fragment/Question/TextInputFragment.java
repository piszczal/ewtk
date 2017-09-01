package com.example.szymon.ewtk.Fragment.Question;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.Model.Question;
import com.example.szymon.ewtk.QuestionsManager;
import com.example.szymon.ewtk.R;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextInputFragment extends Fragment implements View.OnClickListener {

    private int currentQuestion;
    private List<Question> questionsList;
    private List<Answer> answersList;
    private TextView mTitle;
    private TextView mDescription;
    private EditText mAnswerText;
    private ImageView mCircleTitle;
    private FloatingActionButton buttonNext;
    private View view;

    public static TextInputFragment newInstance(List<Question> questionsList, List<Answer> answersList, int currentQuestion) {
        TextInputFragment fragment = new TextInputFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.QUESTIONS_INSTANCE, (Serializable) questionsList);
        args.putSerializable(Constants.ANSWERS_INSTANCE, (Serializable) answersList);
        args.putInt(Constants.QUESTION_CURRENT, currentQuestion);
        fragment.setArguments(args);
        return fragment;
    }

    public List<Question> getQuestionsList() {
        return (List<Question>) getArguments().getSerializable(Constants.QUESTIONS_INSTANCE);
    }

    public List<Answer> getAnswersList() {
        return (List<Answer>) getArguments().getSerializable(Constants.ANSWERS_INSTANCE);
    }

    public int getCurrentQuestion() {
        return getArguments().getInt(Constants.QUESTION_CURRENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_text_input, container, false);
        questionsList = getQuestionsList();
        answersList = getAnswersList();
        currentQuestion = getCurrentQuestion();
        mTitle = (TextView) view.findViewById(R.id.titleText);
        mDescription = (TextView) view.findViewById(R.id.descriptionText);
        mCircleTitle = (ImageView) view.findViewById(R.id.textCirlce);
        mAnswerText = (EditText) view.findViewById(R.id.inputText);
        mTitle.setText(questionsList.get(currentQuestion).getTitle());
        mDescription.setText(questionsList.get(currentQuestion).getDescription());
        String firstLetter = String.valueOf(questionsList.get(currentQuestion).getTitle().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(currentQuestion);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        mCircleTitle.setImageDrawable(drawable);
        buttonNext = ((FloatingActionButton) view.findViewById(R.id.next_fabText));
        buttonNext.setImageResource(R.drawable.ic_arrow_next);
        buttonNext.setOnClickListener(this);
        getActivity().setTitle(getResources().getString(R.string.question) + " " + (currentQuestion + 1) + "/" + questionsList.size());
        return view;
    }

    @Override
    public void onClick(View v) {
        boolean cancel = false;
        View focusView = null;
        mAnswerText.setError(null);
        String answerText = mAnswerText.getText().toString();
        if (TextUtils.isEmpty(answerText)) {
            mAnswerText.setError(getString(R.string.error_field_required));
            focusView = mAnswerText;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            Answer answer = new Answer(questionsList.get(currentQuestion).getDescription(), mAnswerText.getText().toString(), mAnswerText.getText().toString(), questionsList.get(currentQuestion).getID(), questionsList.get(currentQuestion).getQuestionTypeID());
            answersList.add(answer);
            QuestionsManager questionsManager = new QuestionsManager(questionsList, answersList, currentQuestion + 1);
            Fragment fragment = questionsManager.nextQuestion();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                    .replace(R.id.activity_question, fragment, "MY FRAGMENT")
                    .addToBackStack(null);
            transaction.commit();
        }
    }
}
