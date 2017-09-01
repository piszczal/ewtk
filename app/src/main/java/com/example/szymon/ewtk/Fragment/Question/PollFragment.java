package com.example.szymon.ewtk.Fragment.Question;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
public class PollFragment extends Fragment implements View.OnClickListener {

    private int currentQuestion;
    private List<Question> questionsList;
    private List<Answer> answersList;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView circlePoll;
    private FloatingActionButton buttonNext;
    private RadioGroup radioGroup;
    private View view;

    public static PollFragment newInstance(List<Question> questionsList, List<Answer> answersList, int currentQuestion) {
        PollFragment fragment = new PollFragment();
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
        view = inflater.inflate(R.layout.fragment_poll, container, false);
        questionsList = getQuestionsList();
        answersList = getAnswersList();
        currentQuestion = getCurrentQuestion();

        mTitle = (TextView) view.findViewById(R.id.titlePoll);
        mDescription = (TextView) view.findViewById(R.id.descriptionPoll);
        circlePoll = (ImageView) view.findViewById(R.id.circlePoll);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonPoll);
        buttonNext = (FloatingActionButton) view.findViewById(R.id.next_fabPoll);
        buttonNext.setImageResource(R.drawable.ic_arrow_next);
        mTitle.setText(questionsList.get(currentQuestion).getTitle());
        mDescription.setText(questionsList.get(currentQuestion).getDescription());

        String firstLetter = String.valueOf(questionsList.get(currentQuestion).getTitle().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(currentQuestion);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        circlePoll.setImageDrawable(drawable);
        buttonNext = ((FloatingActionButton) view.findViewById(R.id.next_fabPoll));
        buttonNext.setOnClickListener(this);
        createPoll();
        getActivity().setTitle(getResources().getString(R.string.question) + " " + (currentQuestion + 1) + "/" + questionsList.size());
        return view;
    }

    @Override
    public void onClick(View v) {
        int selectedID = radioGroup.getCheckedRadioButtonId();
        if (selectedID <= 0) {
            TextView errorText = (TextView) view.findViewById(R.id.errorText);
            errorText.setText(getResources().getString(R.string.null_check_poll));
        } else {
            RadioButton radioButton = (RadioButton) view.findViewById(selectedID);
            answersList.add(new Answer(questionsList.get(currentQuestion).getDescription(), String.valueOf(radioButton.getId()), radioButton.getText().toString(), questionsList.get(currentQuestion).getID(), questionsList.get(currentQuestion).getQuestionTypeID()));
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

    private void createPoll() {
        int countButtons = questionsList.get(currentQuestion).getSelection().getSelectionAnswersList().size();
        final RadioButton[] radioButtons = new RadioButton[countButtons];
        for (int i = 0; i < countButtons; i++) {
            radioButtons[i] = new RadioButton(getActivity());
            radioGroup.addView(radioButtons[i]);
            radioButtons[i].setText(questionsList.get(currentQuestion).getSelection().getSelectionAnswersList().get(i).getAnswer());
            radioButtons[i].setTextSize(17);
            radioButtons[i].setId(questionsList.get(currentQuestion).getSelection().getSelectionAnswersList().get(i).getID());
        }
    }
}
