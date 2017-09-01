package com.example.szymon.ewtk.Fragment.Question;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.Model.Question;
import com.example.szymon.ewtk.Model.SelectionAnswer;
import com.example.szymon.ewtk.QuestionsManager;
import com.example.szymon.ewtk.R;
import com.triggertrap.seekarc.SeekArc;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScaleFragment extends Fragment implements View.OnClickListener {

    private int currentQuestion;
    private List<Question> questionsList;
    private List<Answer> answersList;
    private List<SelectionAnswer> selectionsList;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView mScaleCircle;
    private ImageView mSeekArcFace;
    private FloatingActionButton buttonNext;
    private SeekArc mSeekArc;
    private TextView mSeekArcProgress;
    private String value;
    private Bitmap bmpOriginal;
    private String answerValue;
    private int valueStep, currentImage=0;
    private int[] faces = {R.drawable.face_1, R.drawable.face_2, R.drawable.face_3, R.drawable.face_4, R.drawable.face_5, R.drawable.face_6, R.drawable.face_7, R.drawable.face_8,
                            R.drawable.face_9, R.drawable.face_10};

    public static ScaleFragment newInstance(List<Question> questionsList, List<Answer> answersList, int currentQuestion) {
        ScaleFragment fragment = new ScaleFragment();
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
        View view = inflater.inflate(R.layout.fragment_scale, container, false);
        questionsList = getQuestionsList();
        answersList = getAnswersList();
        currentQuestion = getCurrentQuestion();
        selectionsList = questionsList.get(currentQuestion).getSelection().getSelectionAnswersList();
        value = selectionsList.get(0).getValue();
        mScaleCircle = (ImageView) view.findViewById(R.id.scaleCircle);
        mTitle = (TextView) view.findViewById(R.id.titleScale);
        mDescription = (TextView) view.findViewById(R.id.descriptionScale);
        buttonNext = (FloatingActionButton) view.findViewById(R.id.next_fabScale);
        mSeekArc = (SeekArc) view.findViewById(R.id.seekArc);
        mSeekArc.setMax(selectionsList.size() - 1);
        mSeekArcProgress = (TextView) view.findViewById(R.id.seekArcProgress);
        mSeekArcFace = (ImageView) view.findViewById(R.id.seekArcFace);
        buttonNext.setImageResource(R.drawable.ic_arrow_next);
        if(questionsList.get(currentQuestion).getIsPicture()==1){
            mSeekArcProgress.setText("");
            mSeekArc.setMax(selectionsList.size()*10);
            valueStep = selectionsList.size();
            if(questionsList.get(currentQuestion).getIsPositive()==1) {
                faces = reverseFaces(faces);
            }
            changeFace(currentImage);
            answerValue = selectionsList.get(0).getAnswer();
        }
        else
            mSeekArcProgress.setText(selectionsList.get(0).getAnswer());


        mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress, boolean b) {
                if(questionsList.get(currentQuestion).getIsPicture()==1) {
                   if(0<=progress && progress <=valueStep)
                       changeFace(0);
                    if(valueStep<progress && progress <=valueStep*2)
                        changeFace(1);
                    if(valueStep*2<=progress && progress <=valueStep*3)
                        changeFace(2);
                    if(valueStep*3<=progress && progress <=valueStep*4)
                        changeFace(3);
                    if(valueStep*4<=progress && progress <=valueStep*5)
                        changeFace(4);
                    if(valueStep*5<=progress && progress <=valueStep*6)
                        changeFace(5);
                    if(valueStep*6<=progress && progress <=valueStep*7)
                        changeFace(6);
                    if(valueStep*7<=progress && progress <=valueStep*8)
                        changeFace(7);
                    if(valueStep*8<=progress && progress <=valueStep*9)
                        changeFace(8);
                    if(valueStep*9<=progress && progress <=valueStep*10)
                        changeFace(9);

                    if(progress<valueStep*10) {
                        value = selectionsList.get((((progress - 10) / 10) + 1)).getValue();
                        answerValue = selectionsList.get((((progress - 10) / 10) + 1)).getAnswer();
                    }
                    else {
                        value = selectionsList.get((progress / 10) - 1).getValue();
                        answerValue = selectionsList.get((progress / 10) - 1).getAnswer();
                    }
                }
                else {
                    mSeekArcProgress.setText(selectionsList.get(progress).getAnswer());
                    value = selectionsList.get(progress).getValue();
                    answerValue = selectionsList.get(progress).getValue();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {

            }
        });

        mTitle.setText(questionsList.get(currentQuestion).getTitle());
        mDescription.setText(questionsList.get(currentQuestion).getDescription());
        String firstLetter = String.valueOf(questionsList.get(currentQuestion).getTitle().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(currentQuestion);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        mScaleCircle.setImageDrawable(drawable);
        buttonNext.setOnClickListener(this);
        getActivity().setTitle(getResources().getString(R.string.question) + " " + (currentQuestion + 1) + "/" + questionsList.size());
        return view;
    }

    @Override
    public void onClick(View v) {
        int answerID = 0;
        for (int i = 0; i < selectionsList.size(); i++) {
            if (value.equals(selectionsList.get(i).getValue()))
                answerID = selectionsList.get(i).getID();
        }
        Answer answer = new Answer(questionsList.get(currentQuestion).getDescription(), String.valueOf(answerID), answerValue, questionsList.get(currentQuestion).getID(), questionsList.get(currentQuestion).getQuestionTypeID());
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

    private int[] reverseFaces(int tab[]){
        int[] tabNew = new int[10];
        for(int i = 0; i < tab.length; i++){
            tabNew[i] = tab[tab.length-1-i];
        }
        return tabNew;
    }

    private void changeFace(int position){
        bmpOriginal = BitmapFactory.decodeResource(getResources(), faces[position]);
        mSeekArcFace.setImageBitmap(bmpOriginal);
    }
}
