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
import com.example.szymon.ewtk.Model.Disease;
import com.example.szymon.ewtk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiseasesDetailFragment extends Fragment {

    private Disease disease;
    private ImageView circleDisease;
    private TextView name;
    private TextView description;
    private int color;

    public static DiseasesDetailFragment newInstance(Disease disease) {
        DiseasesDetailFragment fragment = new DiseasesDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.DISEASE_INSTANCE, disease);
        fragment.setArguments(args);
        return fragment;
    }

    public Disease getDisease() {
        return (Disease) getArguments().getParcelable(Constants.DISEASE_INSTANCE);
    }

    @Override
    public final void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            disease = savedInstanceState.getParcelable(Constants.DISEASE_STATE);
        } else
            disease = getDisease();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diseases_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleDisease = (ImageView) view.findViewById(R.id.circleDisease);
        name = (TextView) view.findViewById(R.id.diseaseName);
        description = (TextView) view.findViewById(R.id.descriptionDisease);
        String firstLetter = String.valueOf(disease.getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        color = generator.getColor(disease.getID());
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        circleDisease.setImageDrawable(drawable);
        name.setText(disease.getName());
        description.setText(disease.getDescription());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.DISEASE_STATE, disease);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
