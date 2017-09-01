package com.example.szymon.ewtk.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.szymon.ewtk.Activity.MainActivity;
import com.example.szymon.ewtk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionFragment extends Fragment implements View.OnClickListener {

    private Button mRetry;

    public ConnectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connection, container, false);
        mRetry = (Button)view.findViewById(R.id.retry);
        mRetry.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(((MainActivity)getActivity()).isOnline()){
            getActivity().finish();
            startActivity(getActivity().getIntent());
        }else{

        }
    }
}
