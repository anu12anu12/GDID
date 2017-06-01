package com.gdid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.gdid.activities.DefectDetailsActivity;
import com.gdid.activities.LoginActivity;
import com.gdid.com.gdid.datamodel.BugData;
import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.material_management.R;

import java.util.ArrayList;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class BugListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String mUserName;
    private ListView mListView;
    private BugListAdapter mAdapter;
    private ArrayList<BugData> mBugList;

    public BugListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buglist, container, false);
        getViewID(rootView);
        getUserName();
        updateTopView(rootView);
        mBugList = getActivity().getIntent().getParcelableArrayListExtra(GDIDConstants.KEY_BUGLIST);
        if (mBugList == null) {
            mBugList = new ArrayList<BugData>();
        }

        mAdapter = new BugListAdapter(getActivity());
        mListView.addHeaderView(getListHeaderView());
        mListView.setAdapter(mAdapter);
        return rootView;
    }

    private View getListHeaderView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View listHeaderView = layoutInflater.inflate(R.layout.detailsheader, null);
        TableLayout headerMainLayout = (TableLayout)listHeaderView.findViewById(R.id.listitemMainLayoutID);

        headerMainLayout.setBackgroundColor(getResources().getColor(R.color.dashboardbgblue));

        TextView titleTextView = (TextView)listHeaderView.findViewById(R.id.keyID);
        titleTextView.setText(getResources().getString(R.string.str_defecttitle));
        titleTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView statusTextView = (TextView)listHeaderView.findViewById(R.id.valueId);
        statusTextView.setText(getResources().getString(R.string.str_defectstatus));
        statusTextView.setTextColor(getResources().getColor(android.R.color.white));

//        TextView defectTextView = (TextView)listHeaderView.findViewById(R.id.defectNumberId);
//        defectTextView.setText(getResources().getString(R.string.str_defectnumber));
//        defectTextView.setTextColor(getResources().getColor(android.R.color.white));



        return listHeaderView;
    }

    private void getUserName() {
        mUserName = "Neo";
    }

    private void getBugList() {
        mBugList = new ArrayList<BugData>();

        BugData bugData1 = new BugData();
        bugData1.mDefectTitle = "Broken Glass and Frames";
        bugData1.mDefectNumber = "1002";
        bugData1.mDefectStatus = "Very Urgent";
        bugData1.mCreatedDate = "6/12/2013  12:27:09 PM";
        bugData1.mDefectDescription = "Glass Broken and Window frames broken";
        bugData1.mLocation = "G&H Building";
        bugData1.mWorkOrders = "543";
        mBugList.add(bugData1);

        BugData bugData2 = new BugData();
        bugData2.mDefectNumber = "1004";
        bugData2.mLocation = "Recreation hall";
        bugData2.mWorkOrders = "876";

        bugData2.mDefectTitle = "Leaking Roof Waterproofing and Painting";
        bugData2.mDefectStatus = "Urgent";
        bugData2.mCreatedDate = "6/12/2013  1:08:34 PM";
        bugData2.mDefectDescription = "Roof leaking and needs sealing,painting and replacement of sheets where damaged";

        mBugList.add(bugData2);

        BugData bugData3 = new BugData();
        bugData3.mLocation = "Recreation hall";
        bugData3.mWorkOrders = "334";

        bugData3.mDefectNumber = "1006";
        bugData3.mDefectTitle = "Fallen Ceiling";
        bugData3.mDefectStatus = "Not Urgent";
        bugData3.mCreatedDate = "2015-06-23 22:11:33";
        bugData3.mDefectDescription = "Ceiling falling due damage by water leak";

        mBugList.add(bugData3);

        BugData bugData4 = new BugData();
        bugData4.mLocation = "G&H Building, H1 WARD";
        bugData4.mWorkOrders = "654";

        bugData4.mDefectNumber = "1007";
        bugData4.mDefectTitle = "NO WATER AT H1 WARD";
        bugData4.mDefectStatus = "Very Urgent";
        bugData4.mCreatedDate = "6/12/2013  1:11:36 PM";
        bugData4.mDefectDescription = "There is no water in the ward.Water had been closed for maintenance .ATTENDED TO BY STANDBY PLUMBER.ALL IS FINE AND THERE IS WATER NOW";

        mBugList.add(bugData4);

        BugData bugData5 = new BugData();
        bugData5.mLocation = "WARDS 17-24";
        bugData5.mWorkOrders = "234";

        bugData5.mDefectTitle = "Broken Glazing";
        bugData5.mDefectNumber = "1009";
        bugData5.mDefectStatus = "Not Urgent";
        bugData5.mCreatedDate = "6/12/2013  1:53:30 PM";
        bugData5.mDefectDescription = "Broken glazing and framing";

        mBugList.add(bugData5);

    }

    private void updateTopView(View aView) {
        TextView userNameView = (TextView) aView.findViewById(R.id.userID);
        TextView logoutView = (TextView) aView.findViewById(R.id.logoutID);
        logoutView.setOnClickListener(this);

        userNameView.setText(getString(R.string.str_hello) + " " + mUserName);
        SpannableString spanString = new SpannableString(getString(R.string.str_logout));
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);

        logoutView.setText(spanString);

    }
    private void getViewID(View aView) {
        mListView = (ListView) aView.findViewById(R.id.bugListID);
        mListView.setOnItemClickListener(this);
    }
    @Override
    public void onStart() {
        super.onStart();
//        getBugList();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }


    private class BugListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        BugListAdapter (Context aContext) {
            mInflater = LayoutInflater.from(aContext);
            mContext = aContext;
        }
        @Override
        public int getCount() {
            return mBugList.size();
        }

        @Override
        public Object getItem(int i) {
            return mBugList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int aPosition, View aConvertView, ViewGroup viewGroup) {
            ViewHolder lViewHolder = null;
            if (aConvertView == null) {
                aConvertView = mInflater.inflate(R.layout.buglist_item, null);
                lViewHolder = new ViewHolder();
//                lViewHolder.mDefectNumberTextView = (TextView) aConvertView.findViewById(R.id.defectNumberId);
                lViewHolder.mDefectTitleTextView = (TextView) aConvertView.findViewById(R.id.defectTitleId);
                lViewHolder.mDefectStatusTextView = (TextView) aConvertView.findViewById(R.id.defectStatusId);
//                lViewHolder.mDefectCreationDateTextView = (TextView) aConvertView.findViewById(R.id.defectCreationDateId);
                aConvertView.setTag(lViewHolder);
            } else {
                lViewHolder = (ViewHolder)aConvertView.getTag();
            }

//            lViewHolder.mDefectNumberTextView.setText(mBugList.get(aPosition).mDefectNumber);
            lViewHolder.mDefectTitleTextView.setText(mBugList.get(aPosition).mDefectTitle);
            lViewHolder.mDefectStatusTextView.setText(mBugList.get(aPosition).mDefectStatus);
//            lViewHolder.mDefectCreationDateTextView.setText(mBugList.get(aPosition).mCreatedDate);

            return aConvertView;
        }

        class ViewHolder {
            TextView mDefectNumberTextView;
            TextView mDefectTitleTextView;
            TextView mDefectStatusTextView;
            TextView mDefectCreationDateTextView;
        }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int aPosition, long l) {
        if (aPosition == 0)
            return;
        Intent loginIntent = new Intent();
        loginIntent.setClass(getActivity(), DefectDetailsActivity.class);
        loginIntent.putExtra(GDIDConstants.KEY_DEFECTDETAILS, mBugList.get((aPosition - 1)));
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(loginIntent);
    }

}
