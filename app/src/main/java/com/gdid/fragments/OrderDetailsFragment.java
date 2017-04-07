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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdid.activities.LoginActivity;
import com.gdid.activities.MaterialListActivity;
import com.gdid.com.gdid.datamodel.OrderData;
import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.material_management.R;

import java.util.ArrayList;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class OrderDetailsFragment extends Fragment implements View.OnClickListener {
    private String mUserName;
    private ListView mListView;
    private OrderDetailsAdapter mAdapter;
    private ArrayList<DisplayData> mDisplayList;
    private OrderData mOrderData;


    private class DisplayData {
        String mKey;
        String mValue;
    }

    public OrderDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.orderdetails, container, false);
        getViewID(rootView);
        getOrderList();
//        updateTopView(rootView);
        mAdapter = new OrderDetailsAdapter(getActivity());

        mListView.addHeaderView(getListHeaderView());
        mListView.addFooterView(getFooterView());
        mListView.setAdapter(mAdapter);
        return rootView;
    }

    private View getListHeaderView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View listHeaderView = layoutInflater.inflate(R.layout.detailsheader, null);
        TableLayout headerMainLayout = (TableLayout)listHeaderView.findViewById(R.id.listitemMainLayoutID);

        headerMainLayout.setBackgroundColor(getResources().getColor(R.color.dashboardbgblue));

        TextView keyTextView = (TextView)listHeaderView.findViewById(R.id.keyID);
        keyTextView.setText(getResources().getString(R.string.str_orderdetailsattrskey));
        keyTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView valTextView = (TextView)listHeaderView.findViewById(R.id.valueId);
        valTextView.setText(getResources().getString(R.string.str_orderdetailsattrsval));
        valTextView.setTextColor(getResources().getColor(android.R.color.white));

        return listHeaderView;
    }

    private View getFooterView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View listHeaderView = layoutInflater.inflate(R.layout.detailsfooterview, null);
        listHeaderView.findViewById(R.id.viewMaterialsListOptionID).setOnClickListener(this);
        return listHeaderView;
    }

    private void getOrderList() {
        mUserName = "Neo";
        mOrderData = getActivity().getIntent().getParcelableExtra(GDIDConstants.KEY_ORDERDETAILS);
        mDisplayList = new ArrayList<DisplayData>();

        DisplayData lDisplayData1 = new DisplayData();
        lDisplayData1.mKey = "Order Number";
        lDisplayData1.mValue = mOrderData.mOrderNumber;
        mDisplayList.add(lDisplayData1);

        DisplayData lDisplayData2 = new DisplayData();
        lDisplayData2.mKey = "Defect Number";
        lDisplayData2.mValue = mOrderData.mDefectNumber;
        mDisplayList.add(lDisplayData2);

        DisplayData lDisplayData3 = new DisplayData();
        lDisplayData3.mKey = "Order Status";
        lDisplayData3.mValue = mOrderData.mOrderStatus;
        mDisplayList.add(lDisplayData3);


        DisplayData lDisplayData4 = new DisplayData();
        lDisplayData4.mKey = "Order Description";
        lDisplayData4.mValue = mOrderData.mOrderDescription;
        mDisplayList.add(lDisplayData4);

        DisplayData lDisplayData5 = new DisplayData();
        lDisplayData5.mKey = "Order Created Date Time";
        lDisplayData5.mValue = mOrderData.mCreatedDate;
        mDisplayList.add(lDisplayData5);

        DisplayData lDisplayData6 = new DisplayData();
        lDisplayData6.mKey = "Order Updated Date Time";
        lDisplayData6.mValue = mOrderData.mModifiedData;
        mDisplayList.add(lDisplayData6);

        DisplayData lDisplayData7 = new DisplayData();
        lDisplayData7.mKey = "Supplier Name";
        lDisplayData7.mValue = mOrderData.mSupplierName;
        mDisplayList.add(lDisplayData7);
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
        mListView = (ListView) aView.findViewById(R.id.orderDetailsID);

    }
    @Override
    public void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }


    private class OrderDetailsAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        OrderDetailsAdapter (Context aContext) {
            mInflater = LayoutInflater.from(aContext);
            mContext = aContext;
        }
        @Override
        public int getCount() {
            return mDisplayList.size();
        }

        @Override
        public Object getItem(int i) {
            return mDisplayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int aPosition, View aConvertView, ViewGroup viewGroup) {
            ViewHolder lViewHolder = null;
            if (aConvertView == null) {
                aConvertView = mInflater.inflate(R.layout.orderdetails_item, null);
                lViewHolder = new ViewHolder();
                lViewHolder.mKeyView = (TextView) aConvertView.findViewById(R.id.keyID);
                lViewHolder.mValueView = (TextView) aConvertView.findViewById(R.id.valueId);
                aConvertView.setTag(lViewHolder);
            } else {
                lViewHolder = (ViewHolder)aConvertView.getTag();
            }

            lViewHolder.mKeyView.setText(mDisplayList.get(aPosition).mKey);
            lViewHolder.mValueView.setText(mDisplayList.get(aPosition).mValue);

            return aConvertView;
        }

        class ViewHolder {
            TextView mKeyView;
            TextView mValueView;
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
            case R.id.viewMaterialsListOptionID:
                Intent materialListIntent = new Intent();
                materialListIntent.setClass(getActivity(), MaterialListActivity.class);
                materialListIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(materialListIntent);
                break;

        }
    }

    public void viewMaterialList(View aView) {
        Toast.makeText(getActivity(), "Option to display Material List", Toast.LENGTH_SHORT).show();
    }

}
