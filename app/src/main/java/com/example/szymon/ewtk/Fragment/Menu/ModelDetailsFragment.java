package com.example.szymon.ewtk.Fragment.Menu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Activity.MainActivity;
import com.example.szymon.ewtk.Activity.QuestionActivity;
import com.example.szymon.ewtk.Adapter.ListExecutionAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.Model;
import com.example.szymon.ewtk.Model.ModelExecution;
import com.example.szymon.ewtk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModelDetailsFragment extends Fragment {

    private Model model;
    private ArrayAdapter<ModelExecution> modelAdapter;
    private CardView mCardViewTop;
    private TextView modelName;
    private TextView modelCreated;
    private ImageView imageView;
    private TextView modelUpdated;
    private TextView descriptionModel;
    private TextView executionDetails;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public static ModelDetailsFragment newInstance(Model model) {
        ModelDetailsFragment fragment = new ModelDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.MODEL_INSTANCE, model);
        fragment.setArguments(args);
        return fragment;
    }

    public Model getModel() {
        return getArguments().getParcelable(Constants.MODEL_INSTANCE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model_details, container, false);
        if (savedInstanceState != null) {
            model = savedInstanceState.getParcelable(Constants.MODEL_STATE);
        } else
            model = getModel();
        imageView = (ImageView) view.findViewById(R.id.model_detail_image);
        modelName = (TextView) view.findViewById(R.id.name_model);
        descriptionModel = (TextView) view.findViewById(R.id.description_model);
        executionDetails = (TextView) view.findViewById(R.id.execution_model);
        modelName.setText(model.getName());
        descriptionModel.setText(model.getDescription());
        executionDetails.setText(getResources().getString(R.string.executions_module) + ": " + model.getModelExecution().size());

        getActivity().setTitle(getResources().getString(R.string.module_details));
        String firstLetter = String.valueOf(model.getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(model.getID());
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        imageView.setImageDrawable(drawable);

        mDrawableBuilder = TextDrawable.builder()
                .roundRect(10);
        final ListView listView = (ListView) view.findViewById(R.id.module_execution_list);
        modelAdapter = new ListExecutionAdapter(getActivity().getApplicationContext(), R.layout.execution_item, model.getModelExecution());
        listView.setAdapter(modelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent questionActivity = new Intent(getActivity().getApplicationContext(), QuestionActivity.class);
                questionActivity.putExtra(Constants.MODEL_ID, model.getID());
                questionActivity.putExtra(Constants.GENDER, ((MainActivity) getActivity()).getGender());
                questionActivity.putExtra(Constants.MODEL_EXECUTION_ID, model.getModelExecution().get(arg2).getID());
                startActivity(questionActivity);
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.MODEL_STATE, model);
        super.onSaveInstanceState(outState);
    }

}
