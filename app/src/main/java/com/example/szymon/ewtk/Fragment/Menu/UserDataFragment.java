package com.example.szymon.ewtk.Fragment.Menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.User;
import com.example.szymon.ewtk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDataFragment extends Fragment {

    private ImageView circleProfile;
    private TextView email;
    private TextView nameUser;
    private TextView pointsUser;
    private TextView surnameUser;
    private TextView genderUser;
    private TextView birthDateUser;
    private TextView ageUser;
    private TextView peselUser;
    private TextView growthUser;
    private TextView phoneUser;
    private TextView weightUser;
    private  TextView streetUser;
    private TextView bloodUser;
    private TextView zipcodeUser;
    private TextView townUser;
    private TextView aboutmeUser;

    private User user;

    private View view;

    public static UserDataFragment newInstance(User user) {
        UserDataFragment fragment = new UserDataFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.USER_INSTANCE, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            user = savedInstanceState.getParcelable(Constants.USER_STATE);
        } else
            user = getUser();
    }


    public User getUser() {
        return getArguments().getParcelable(Constants.USER_INSTANCE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_data, container, false);
        bindView();

        String firstLetter = String.valueOf(user.getEmail().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(0);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        circleProfile.setImageDrawable(drawable);
        setText();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.USER_STATE, user);
        super.onSaveInstanceState(outState);
    }

    private void setText() {
        email.setText(user.getEmail());
        nameUser.setText(user.getName());
        pointsUser.setText(String.valueOf(user.getScorePoints()));
        surnameUser.setText(user.getSurname());
        genderUser.setText(user.getProfile().getGender());
        birthDateUser.setText(user.getProfile().getBirthDate());
        ageUser.setText(String.valueOf(user.getProfile().getAge()));
        peselUser.setText(user.getProfile().getPesel());
        growthUser.setText(String.valueOf(user.getProfile().getWidth()));
        phoneUser.setText(user.getProfile().getPhone());
        weightUser.setText(String.valueOf(user.getProfile().getWeight()));
        streetUser.setText(user.getProfile().getStreet());
        bloodUser.setText(user.getProfile().getBloodType());
        zipcodeUser.setText(user.getProfile().getZipcode());
        townUser.setText(user.getProfile().getTown());
        aboutmeUser.setText(user.getProfile().getAboutMe());
    }

    private void bindView() {
        circleProfile = (ImageView) view.findViewById(R.id.circleProfile);
        email = (TextView) view.findViewById(R.id.email);
        nameUser = (TextView) view.findViewById(R.id.nameUser);
        pointsUser = (TextView) view.findViewById(R.id.pointsUser);
        surnameUser = (TextView) view.findViewById(R.id.surnameUser);
        genderUser = (TextView) view.findViewById(R.id.genderUser);
        birthDateUser = (TextView) view.findViewById(R.id.birthDateUser);
        ageUser = (TextView) view.findViewById(R.id.ageUser);
        peselUser = (TextView) view.findViewById(R.id.peselUser);
        growthUser = (TextView) view.findViewById(R.id.growthUser);
        phoneUser = (TextView) view.findViewById(R.id.phoneUser);
        weightUser = (TextView) view.findViewById(R.id.weightUser);
        streetUser = (TextView) view.findViewById(R.id.streetUser);
        bloodUser = (TextView) view.findViewById(R.id.bloodUser);
        zipcodeUser = (TextView) view.findViewById(R.id.zipcodeUser);
        townUser = (TextView) view.findViewById(R.id.townUser);
        aboutmeUser = (TextView) view.findViewById(R.id.aboutmeUser);
    }


}
