package com.gdid.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdid.activities.LoginActivity;
import com.gdid.com.gdid.datamodel.DashBoardData;
import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.material_management.R;

import java.util.ArrayList;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {
    private ListView mListView;
    private DashboardAdapter mAdapter;
    private ArrayList<DashBoardData> mDashBoardDataList;
    private String mUserName;
    private RelativeLayout mProgressBar;
    private class DashBoardDataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DashBoardData> dashBoardDataArrayList = intent.getParcelableArrayListExtra(GDIDConstants.KEY_DASHBOARD_DATA);
            if (dashBoardDataArrayList != null) {
                mDashBoardDataList = dashBoardDataArrayList;
//                mProgressBar.setVisibility(View.GONE);

                mAdapter.notifyDataSetChanged();

            }
        }
    }
    private DashBoardDataReceiver mDashBoardReceiver = new DashBoardDataReceiver();
    public DashboardFragment() {
    }

    private void getUserName() {
        mUserName = "Neo";

    }
    private void getDashBoardData() {
        mDashBoardDataList = new ArrayList<DashBoardData>();

        DashBoardData dashBoardData1 = new DashBoardData();
        dashBoardData1.mResult = "552";
        dashBoardData1.mDescriptionText = "New Order Created";
        dashBoardData1.mHexColorCode = "#4e87b8";
        mDashBoardDataList.add(dashBoardData1);

        DashBoardData dashBoardData2 = new DashBoardData();
        dashBoardData2.mResult = "582 / 258";
        dashBoardData2.mHexColorCode = "#d05a59";
        dashBoardData2.mDescriptionText = "Order Allocated / Pending Acknowledgement";
        mDashBoardDataList.add(dashBoardData2);

        DashBoardData dashBoardData3 = new DashBoardData();
        dashBoardData3.mHexColorCode = "#43a9a5";
        dashBoardData3.mResult = "544";
        dashBoardData3.mDescriptionText = "Pending Material Preperation";
        mDashBoardDataList.add(dashBoardData3);

        DashBoardData dashBoardData4 = new DashBoardData();
        dashBoardData4.mResult = "124 / 147";
        dashBoardData4.mHexColorCode = "#8e7cab";
        dashBoardData4.mDescriptionText = "Pending Collection By Storeman / Artisan";

        mDashBoardDataList.add(dashBoardData4);
    }

    private void registerReceiver() {
        getActivity().registerReceiver(mDashBoardReceiver, new IntentFilter(GDIDConstants.ACTION_DASHBOARD_RECEIVER));
    }

    private void unRegisterReceiver() {
        if (mDashBoardReceiver != null) {
            getActivity().unregisterReceiver(mDashBoardReceiver);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        unRegisterReceiver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);
        getViewID(rootView);
        getUserName();
//        getDashBoardData();
        updateTopView(rootView);
        mDashBoardDataList = getActivity().getIntent().getParcelableArrayListExtra(GDIDConstants.KEY_DASHBOARD_DATA);
        if (mDashBoardDataList == null) {
            mDashBoardDataList = new ArrayList<DashBoardData>();
        }
        mAdapter = new DashboardAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        return rootView;
    }

    private void updateTopView(View aView) {
        TextView userNameView = (TextView) aView.findViewById(R.id.userID);
        TextView logoutView = (TextView) aView.findViewById(R.id.logoutID);

        userNameView.setText(getString(R.string.str_hello) + " " + mUserName);
        SpannableString spanString = new SpannableString(getString(R.string.str_logout));
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
        logoutView.setOnClickListener(this);
        logoutView.setText(spanString);

    }

    private void getViewID(View aView) {
        mListView = (ListView) aView.findViewById(R.id.dashboardListID);
//        mProgressBar = (RelativeLayout) aView.findViewById(R.id.progressViewID);
//        mProgressBar.setVisibility(View.VISIBLE);

    }
    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        registerReceiver();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logoutID:
                Intent loginIntent = new Intent();
                loginIntent.setClass(getActivity(), LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(loginIntent);
                getActivity().finish();
                break;
        }
    }

    private class DashboardAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        DashboardAdapter (Context aContext) {
            mInflater = LayoutInflater.from(aContext);
            mContext = aContext;
        }
        @Override
        public int getCount() {
            return mDashBoardDataList.size();
        }

        @Override
        public Object getItem(int i) {
            return mDashBoardDataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int aPosition, View aConvertView, ViewGroup viewGroup) {
            ViewHolder lViewHolder = null;
            if (aConvertView == null) {
                aConvertView = mInflater.inflate(R.layout.dashboardlistitem, null);
                lViewHolder = new ViewHolder();
                lViewHolder.mTitleTextView = (TextView) aConvertView.findViewById(R.id.title);
                lViewHolder.mSummaryTextView = (TextView) aConvertView.findViewById(R.id.summary);
                lViewHolder.mDashBoardMainLayout = (RelativeLayout) aConvertView.findViewById(R.id.dashboardMainLayoutListItemID);
                aConvertView.setTag(lViewHolder);
            } else {
                lViewHolder = (ViewHolder)aConvertView.getTag();
            }

            lViewHolder.mTitleTextView.setText(mDashBoardDataList.get(aPosition).mResult);
            lViewHolder.mSummaryTextView.setText(mDashBoardDataList.get(aPosition).mDescriptionText);

            RelativeLayout listitemMainLayout = (RelativeLayout)aConvertView.findViewById(R.id.dashboardMainLayoutListItemID);
            if (mDashBoardDataList.get(aPosition).mHexColorCode != null) {
                lViewHolder.mDashBoardMainLayout.setBackgroundColor(Color.parseColor(mDashBoardDataList.get(aPosition).mHexColorCode));

            } else {
                lViewHolder.mDashBoardMainLayout.setBackgroundColor(getResources().getColor(R.color.dashboardbgblue));
            }
            return aConvertView;
        }

        class ViewHolder {
            TextView mTitleTextView;
            TextView mSummaryTextView;
            RelativeLayout mDashBoardMainLayout;
        }
    }

}
