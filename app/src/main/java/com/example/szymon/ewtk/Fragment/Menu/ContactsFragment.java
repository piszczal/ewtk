package com.example.szymon.ewtk.Fragment.Menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.szymon.ewtk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private ImageView location;
    private ImageView email;
    private ImageView call;

    public ContactsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        getActivity().setTitle(getResources().getString(R.string.contact));
        location = (ImageView) view.findViewById(R.id.contact_location);
        email = (ImageView) view.findViewById(R.id.contact_email);
        call = (ImageView) view.findViewById(R.id.contact_call);

        location.setImageResource(R.drawable.ic_location);
        email.setImageResource(R.drawable.ic_emai);
        call.setImageResource(R.drawable.ic_call);
        return view;
    }

}
