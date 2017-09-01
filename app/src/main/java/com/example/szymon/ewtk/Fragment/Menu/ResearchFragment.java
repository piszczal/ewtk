package com.example.szymon.ewtk.Fragment.Menu;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.szymon.ewtk.Adapter.ViewPagerAdapter;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.Model.Inquiry;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.SocketTimeoutException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResearchFragment extends Fragment {

    private RestClient restClient;
    private ResearchFragment.HttpRequestTask mAsyncTask;
    private SessionManagement sessionManagement;
    private AppBarLayout appBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Inquiry inquiry;

    public ResearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_research, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        if (savedInstanceState == null) {
            sessionManagement = new SessionManagement(view.getContext());
            restClient = new Methods(getActivity().getApplicationContext());
            mAsyncTask = new ResearchFragment.HttpRequestTask(sessionManagement.getUserDetails());
            mAsyncTask.execute((Void) null);
            initTabs(container);
        } else {
            inquiry = savedInstanceState.getParcelable(Constants.INQUIRY_STATE);
            initTabs(container);
            toolbarViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
        getActivity().setTitle(R.string.my_research);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        adapter.addFragment(new InquiryFragment().newInstance(inquiry), getResources().getString(R.string.details));
        adapter.addFragment(new DiseasesFragment().newInstance(inquiry.getDiseases(), inquiry.getAddictions()), getResources().getString(R.string.pref_ill));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        if (appBar != null)
            appBar.removeView(tabLayout);
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.INQUIRY_STATE, inquiry);
        super.onSaveInstanceState(outState);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Boolean> {
        String access_token;
        int inquiryID;

        HttpRequestTask(HashMap<String, String> session) {
            this.access_token = session.get(SessionManagement.KEY_ACCESS_TOKEN);
            this.inquiryID = Integer.parseInt(session.get(SessionManagement.INQUIRY_ID));
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                restClient.setBearerAuth(access_token);
                inquiry = restClient.getInquiryDetails(inquiryID);
                return true;
            } catch (final HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getResources().getString(R.string.expired_session), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getContext(), getResources().getString(R.string.time_out), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                }
            } catch (ResourceAccessException e) {
                if (e.getCause() instanceof SocketTimeoutException) {
                    Log.e("Request time out", e.getMessage(), e);
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean status) {
            if (status != false) {
                toolbarViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
