package com.example.szymon.ewtk.Fragment.Menu;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Adapter.ListViewAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.Disease;
import com.example.szymon.ewtk.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiseasesFragment extends Fragment {

    private List<Disease> diseaseList;
    private List<Disease> addictionList;
    private ArrayAdapter<Disease> diseasedAdapter;
    private ArrayAdapter<Disease> addictionsAdapter;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private TextView disease;
    private TextView addiction;

    public static DiseasesFragment newInstance(List<Disease> diseaseList, List<Disease> addictionsList) {
        DiseasesFragment fragment = new DiseasesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.DISEASES_INSTANCE, (ArrayList<? extends Parcelable>) diseaseList);
        args.putParcelableArrayList(Constants.ADDICTIONS_INSTANCE, (ArrayList<? extends Parcelable>) addictionsList);
        fragment.setArguments(args);
        return fragment;
    }

    public List<Disease> getDiseases() {
        return getArguments().getParcelableArrayList(Constants.DISEASES_INSTANCE);
    }

    public List<Disease> getAddictions() {
        return (List<Disease>) getArguments().getSerializable(Constants.ADDICTIONS_INSTANCE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases, container, false);
        ListView listDiseasesView = (ListView) view.findViewById(R.id.listDiseases);
        ListView listAddictionsView = (ListView) view.findViewById(R.id.listAddictions);
        disease = (TextView) view.findViewById(R.id.disease);
        addiction = (TextView) view.findViewById(R.id.addictions);

        if (savedInstanceState != null) {
            diseaseList = savedInstanceState.getParcelableArrayList(Constants.DISEASES_STATE);
            addictionList = savedInstanceState.getParcelableArrayList(Constants.ADDICTIONS_STATE);
        } else {
            diseaseList = getDiseases();
            addictionList = getAddictions();
        }
        mDrawableBuilder = TextDrawable.builder()
                .roundRect(10);

        disease.setText(getResources().getString(R.string.diseases) + ": " + diseaseList.size());
        addiction.setText(getResources().getString(R.string.addictions) + ": " + addictionList.size());
        diseasedAdapter = new ListViewAdapter(getActivity().getApplicationContext(), R.layout.diseases_item, diseaseList);
        addictionsAdapter = new ListViewAdapter(getActivity().getApplicationContext(), R.layout.diseases_item, addictionList);
        listDiseasesView.setAdapter(diseasedAdapter);
        listDiseasesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.diseasesFragment, new DiseasesDetailFragment().newInstance(diseaseList.get(arg2)), "MY FRAGMENT")
                        .addToBackStack(null);
                transaction.commit();
            }
        });
        listAddictionsView.setAdapter(addictionsAdapter);
        listAddictionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.diseasesFragment, new DiseasesDetailFragment().newInstance(addictionList.get(arg2)), "MY FRAGMENT2")
                        .addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.DISEASES_STATE, (ArrayList<? extends Parcelable>) diseaseList);
        outState.putParcelableArrayList(Constants.ADDICTIONS_STATE, (ArrayList<? extends Parcelable>) addictionList);
        super.onSaveInstanceState(outState);
    }
}
