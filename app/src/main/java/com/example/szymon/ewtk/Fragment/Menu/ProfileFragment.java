package com.example.szymon.ewtk.Fragment.Menu;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.szymon.ewtk.Adapter.ViewPagerAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Model.User;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.SessionManagement;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private CoordinatorLayout coordinatorLayout;
    private SessionManagement sessionManagement;
    private User userData;
    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;

    public static ProfileFragment newInstance(User user) {

        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.USER_INSTANCE, user);
        fragment.setArguments(args);
        return fragment;
    }

    public User getUserData() {
        return getArguments().getParcelable(Constants.USER_INSTANCE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.profilePager);
        sessionManagement = new SessionManagement(view.getContext());
        if (savedInstanceState == null) {
            userData = getUserData();
        } else {
            userData = savedInstanceState.getParcelable(Constants.USER_STATE);
        }
        if(userData==null){
            userData = new User();
        }
        initTabs(container);
        toolbarViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        getActivity().setTitle(R.string.my_profile);
        return view;
    }

    private void initTabs(ViewGroup container) {
        View view = (View) container.getParent();
        appBar = (AppBarLayout) view.findViewById(R.id.appbar);
        tabLayout = new TabLayout(getActivity());
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.white));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        appBar.addView(tabLayout);
    }

    private void toolbarViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new UserDataFragment().newInstance(userData), getResources().getString(R.string.data));
        adapter.addFragment(new DiseasesFragment().newInstance(userData.getProfile().getDiseases(), userData.getProfile().getAddictions()), getResources().getString(R.string.profile_ill));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (appBar != null)
            appBar.removeView(tabLayout);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.USER_STATE, userData);
        super.onSaveInstanceState(outState);
    }

}
