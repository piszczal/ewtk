package com.example.szymon.ewtk.Fragment.Menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.szymon.ewtk.Adapter.InquiryAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.Inquiry;
import com.example.szymon.ewtk.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InquiryFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    InquiryAdapter inquiryAdapter;
    Inquiry inquiry;

    public static InquiryFragment newInstance(Inquiry inquiry) {
        InquiryFragment fragment = new InquiryFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.INQUIRY_INSTANCE, inquiry);
        fragment.setArguments(args);
        return fragment;
    }

    public Inquiry getInquiry() {
        return getArguments().getParcelable(Constants.INQUIRY_INSTANCE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inquiry, container, false);
        if (savedInstanceState != null) {
            inquiry = savedInstanceState.getParcelable(Constants.INQUIRY_STATE);
        } else
            inquiry = getInquiry();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        inquiryAdapter = new InquiryAdapter(inquiry, getContext());
        recyclerView.setAdapter(inquiryAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.INQUIRY_STATE, inquiry);
        super.onSaveInstanceState(outState);
    }
}
