package com.example.szymon.ewtk.Fragment.Question;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Activity.MainActivity;
import com.example.szymon.ewtk.Activity.QuestionActivity;
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
public class BodyFragment extends Fragment implements View.OnClickListener {

    private View view;
    private int currentQuestion;
    private List<Question> questionsList;
    private List<Answer> answersList;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView circleBody;
    private FloatingActionButton buttonNext;
    private Switch switchFrontBack;
    private LinearLayout linearLayout;
    private Bitmap bmpOriginal;
    private BitmapDrawable bmpBackground;
    private RadioGroup bodyRadioGroup;
    private RadioButton headButton;
    private RadioButton armRightButton;
    private RadioButton armLeftButton;
    private RadioButton stomach;
    private RadioButton buttocks;
    private RadioButton rightLeg;
    private RadioButton leftLeg;
    private RadioButton rightFoot;
    private RadioButton leftFoot;
    private CheckBox nothingCheckBox;
    private TextView selectPart;
    private TextView textError;
    private String selectValue;
    private Boolean checkBoxStatus = false;
    private Boolean front_back = false;
    private EditText painDiseasesText;
    private String gender;
    private int selectBodyID;

    public static BodyFragment newInstance(List<Question> questionsList, List<Answer> answersList, int currentQuestion) {
        BodyFragment fragment = new BodyFragment();
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
        view = inflater.inflate(R.layout.fragment_body, container, false);
        gender = ((QuestionActivity) getActivity()).getGender();
        questionsList = getQuestionsList();
        answersList = getAnswersList();
        currentQuestion = getCurrentQuestion();
        mTitle = (TextView) view.findViewById(R.id.titlePoll);
        mDescription = (TextView) view.findViewById(R.id.descriptionPoll);
        circleBody = (ImageView) view.findViewById(R.id.circlePoll);
        buttonNext = (FloatingActionButton) view.findViewById(R.id.next_fabPoll);
        switchFrontBack = (Switch) view.findViewById(R.id.switch_font);
        linearLayout = (LinearLayout) view.findViewById(R.id.bodyLayout);
        bodyRadioGroup = (RadioGroup) view.findViewById(R.id.bodyRadioGroup);
        selectPart = (TextView) view.findViewById(R.id.selectPart);
        textError = (TextView) view.findViewById(R.id.textError);
        nothingCheckBox = (CheckBox) view.findViewById(R.id.nothing);
        painDiseasesText = (EditText) view.findViewById(R.id.painDiseases);

        headButton = (RadioButton) view.findViewById(R.id.head);
        armRightButton = (RadioButton) view.findViewById(R.id.arm_right);
        armLeftButton = (RadioButton) view.findViewById(R.id.arm_left);
        stomach = (RadioButton) view.findViewById(R.id.stomach);
        buttocks = (RadioButton) view.findViewById(R.id.buttocks);
        rightLeg = (RadioButton) view.findViewById(R.id.right_leg);
        leftLeg = (RadioButton) view.findViewById(R.id.left_leg);
        rightFoot = (RadioButton) view.findViewById(R.id.right_foot);
        leftFoot = (RadioButton) view.findViewById(R.id.left_foot);

        buttonNext.setImageResource(R.drawable.ic_arrow_next);
        buttocks.setVisibility(View.INVISIBLE);

        if (gender.equals("M"))
            bmpOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.front_male);
        else
            bmpOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.front_female);

        bmpBackground = new BitmapDrawable(getResources(), bmpOriginal);
        linearLayout.setBackgroundDrawable(bmpBackground);

        nothingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchFrontBack.setEnabled(false);
                    for (int i = 0; i < bodyRadioGroup.getChildCount(); i++) {
                        bodyRadioGroup.getChildAt(i).setEnabled(false);
                    }
                    painDiseasesText.setText(null);
                    painDiseasesText.setEnabled(false);
                    checkBoxStatus = true;
                } else {
                    switchFrontBack.setEnabled(true);
                    for (int i = 0; i < bodyRadioGroup.getChildCount(); i++) {
                        bodyRadioGroup.getChildAt(i).setEnabled(true);
                    }
                    painDiseasesText.setText(null);
                    painDiseasesText.setEnabled(true);
                    checkBoxStatus = false;
                }
                bodyRadioGroup.clearCheck();
                selectPart.setText("");
            }
        });

        bodyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.head:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(0).getLabel();
                        break;
                    case R.id.neck:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(1).getLabel();
                        break;
                    case R.id.arm_right:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(6).getLabel();
                        break;
                    case R.id.arm_left:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(5).getLabel();
                        break;
                    case R.id.chest_back:
                        if (front_back)
                            selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(2).getLabel();
                        else
                            selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(3).getLabel();
                        break;
                    case R.id.stomach:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(4).getLabel();
                        break;
                    case R.id.buttocks:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(7).getLabel();
                        break;
                    case R.id.right_leg:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(11).getLabel();
                        break;
                    case R.id.left_leg:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(10).getLabel();
                        break;
                    case R.id.right_foot:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(9).getLabel();
                        break;
                    case R.id.left_foot:
                        selectValue = questionsList.get(currentQuestion).getBodyPartsList().get(8).getLabel();
                        break;
                }
                selectPart.setTextColor(getResources().getColor(R.color.black));
                selectPart.setText(getResources().getString(R.string.check_body) + ": " + selectValue);
            }
        });

        switchFrontBack.setChecked(false);
        switchFrontBack.setText(getResources().getString(R.string.front_body));
        switchFrontBack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    if (gender.equals("M"))
                        bmpOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.back_male);
                    else
                        bmpOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.back_female);
                    switchFrontBack.setText(getResources().getString(R.string.back_body));
                    front_back = true;
                } else {
                    if (gender.equals("M"))
                        bmpOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.front_male);
                    else
                        bmpOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.front_female);
                    switchFrontBack.setText(getResources().getString(R.string.front_body));
                    front_back = false;
                }
                setRadioButtons(isChecked);
                bmpBackground = new BitmapDrawable(getResources(), bmpOriginal);
                linearLayout.setBackgroundDrawable(bmpBackground);
            }
        });

        mTitle.setText(questionsList.get(currentQuestion).getTitle());
        mDescription.setText(questionsList.get(currentQuestion).getDescription());

        String firstLetter = String.valueOf(questionsList.get(currentQuestion).getTitle().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(currentQuestion);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        circleBody.setImageDrawable(drawable);
        buttonNext = ((FloatingActionButton) view.findViewById(R.id.next_fabPoll));
        buttonNext.setOnClickListener(this);
        getActivity().setTitle(getResources().getString(R.string.question) + " " + (currentQuestion + 1) + "/" + questionsList.size());
        return view;
    }

    @Override
    public void onClick(View v) {
        boolean cancel = false;
        View focusView = null;
        int selectedID = bodyRadioGroup.getCheckedRadioButtonId();
        painDiseasesText.setError(null);
        String answerText = painDiseasesText.getText().toString();
        Answer answer;
        if (TextUtils.isEmpty(answerText) && checkBoxStatus == false && selectedID <= 0) {
            selectPart.setTextColor(getResources().getColor(R.color.red));
            selectPart.setText(getResources().getString(R.string.null_check_body));
            focusView = painDiseasesText;
            cancel = true;
        }
        if (!TextUtils.isEmpty(answerText) && checkBoxStatus == false && selectedID <= 0) {
            selectPart.setTextColor(getResources().getColor(R.color.red));
            selectPart.setText(getResources().getString(R.string.null_check_body));
        }
        if (TextUtils.isEmpty(answerText) && checkBoxStatus == false && selectedID > 0) {
            textError.setText(getString(R.string.error_field_required));
            focusView = painDiseasesText;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
        if ((checkBoxStatus == true && TextUtils.isEmpty(answerText)) || (!TextUtils.isEmpty(answerText) && checkBoxStatus == false && selectedID > 0)) {
            if (checkBoxStatus) {
                answer = new Answer(questionsList.get(currentQuestion).getDescription(), "nothing", "", questionsList.get(currentQuestion).getID(), questionsList.get(currentQuestion).getQuestionTypeID());
            } else {
                for(int i = 0; i < questionsList.get(currentQuestion).getBodyPartsList().size(); i++){
                    if(questionsList.get(currentQuestion).getBodyPartsList().get(i).getLabel().equals(selectValue))
                        selectBodyID = questionsList.get(currentQuestion).getBodyPartsList().get(i).getID();
                }
                answer = new Answer(questionsList.get(currentQuestion).getDescription(), selectValue + ":" +painDiseasesText.getText().toString(), questionsList.get(currentQuestion).getID(), questionsList.get(currentQuestion).getQuestionTypeID(), selectBodyID);
            }
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

    private void setRadioButtons(boolean isStatus) {
        if (isStatus) {
            headButton.setVisibility(View.INVISIBLE);
            armRightButton.setVisibility(View.INVISIBLE);
            armLeftButton.setVisibility(View.INVISIBLE);
            stomach.setVisibility(View.INVISIBLE);
            buttocks.setVisibility(View.VISIBLE);
            rightLeg.setVisibility(View.INVISIBLE);
            leftLeg.setVisibility(View.INVISIBLE);
            rightFoot.setVisibility(View.INVISIBLE);
            leftFoot.setVisibility(View.INVISIBLE);
        } else {
            headButton.setVisibility(View.VISIBLE);
            armRightButton.setVisibility(View.VISIBLE);
            armLeftButton.setVisibility(View.VISIBLE);
            stomach.setVisibility(View.VISIBLE);
            buttocks.setVisibility(View.INVISIBLE);
            rightLeg.setVisibility(View.VISIBLE);
            leftLeg.setVisibility(View.VISIBLE);
            rightFoot.setVisibility(View.VISIBLE);
            leftFoot.setVisibility(View.VISIBLE);
        }
        bodyRadioGroup.clearCheck();
        selectPart.setText("");
    }

}
