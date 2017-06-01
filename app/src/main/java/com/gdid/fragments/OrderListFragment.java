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

import com.gdid.activities.LoginActivity;
import com.gdid.activities.OrderDetailsActivity;
import com.gdid.com.gdid.datamodel.OrderData;
import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.material_management.R;

import java.util.ArrayList;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class OrderListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String mUserName;
    private ListView mListView;
    private OrderListAdapter mAdapter;
    private ArrayList<OrderData> mOrderList;

    public OrderListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.orderlist, container, false);
        getViewID(rootView);
        getOrderList();
        updateTopView(rootView);
        mAdapter = new OrderListAdapter(getActivity());

        mListView.addHeaderView(getListHeaderView());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        return rootView;
    }

    private View getListHeaderView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View listHeaderView = layoutInflater.inflate(R.layout.detailsheader, null);
        TableLayout headerMainLayout = (TableLayout)listHeaderView.findViewById(R.id.listitemMainLayoutID);

        headerMainLayout.setBackgroundColor(getResources().getColor(R.color.dashboardbgblue));

        TextView orderNumberTextView = (TextView)listHeaderView.findViewById(R.id.keyID);
        orderNumberTextView.setText(getResources().getString(R.string.str_ordernumber));
        orderNumberTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView statusTextView = (TextView)listHeaderView.findViewById(R.id.valueId);
        statusTextView.setText(getResources().getString(R.string.str_orderstatus));
        statusTextView.setTextColor(getResources().getColor(android.R.color.white));

//        TextView defectTextView = (TextView)listHeaderView.findViewById(R.id.defectNumberId);
//        defectTextView.setText(getResources().getString(R.string.str_defectnumber));
//        defectTextView.setTextColor(getResources().getColor(android.R.color.white));



        return listHeaderView;
    }

    private void getOrderList() {
        mUserName = "Neo";
        mOrderList = new ArrayList<OrderData>();

        OrderData orderData1 = new OrderData();
        orderData1.mOrderNumber = "543";
        orderData1.mOrderStatus = "Pending for Storeman Collection";
        orderData1.mModifiedData = "6/19/2013  4:43:52 PM";
        orderData1.mCreatedDate = "6/19/2013  4:43:52 PM";
        orderData1.mDefectNumber = "1002";
        orderData1.mOrderDescription = "Loose connection to pot and switches broken";
        orderData1.mSupplierName = "Ntinga trading (pty)Ltd";
        mOrderList.add(orderData1);

        OrderData orderData2 = new OrderData();
        orderData2.mOrderNumber = "876";
        orderData2.mOrderStatus = "Pending Acknowledge from Supplier";
        orderData2.mModifiedData = "6/19/2013  4:45:56 PM";
        orderData2.mCreatedDate = "6/19/2013  4:45:56 PM";
        orderData2.mDefectNumber = "1004";
        orderData2.mSupplierName = "Jaydee Magazi Construction & projects CC";

        orderData2.mOrderDescription = "blown out isolock fuses";


        mOrderList.add(orderData2);

        OrderData orderData3 = new OrderData();
        orderData3.mOrderNumber = "334";
        orderData3.mOrderStatus = "Pending for Artisan collection";
        orderData3.mModifiedData = "6/19/2013  4:47:25 PM";
        orderData3.mCreatedDate = "6/19/2013  4:47:25 PM";
        orderData3.mDefectNumber = "1006";
        orderData3.mOrderDescription = "plugs not working";
        orderData3.mSupplierName = "JMTZ Consultants and projects (Pty) Ltd";


        mOrderList.add(orderData3);

        OrderData orderData4 = new OrderData();
        orderData4.mOrderNumber = "654";
        orderData4.mOrderStatus = "Pending for Artisan assignment";
        orderData4.mModifiedData = "6/19/2013  4:53:32 PM";
        orderData4.mCreatedDate = "6/19/2013  4:53:32 PM";
        orderData4.mDefectNumber = "1007";
        orderData4.mOrderDescription = "Plugs broken";
        orderData4.mSupplierName = "Norvrtns Trading Enterprise CC";

        mOrderList.add(orderData4);

        OrderData orderData5 = new OrderData();
        orderData5.mOrderNumber = "234";
        orderData5.mOrderStatus = "Pending for Storeman assignment";
        orderData5.mModifiedData = "6/19/2013  4:54:42 PM";
        orderData5.mCreatedDate = "6/19/2013  4:54:42 PM";
        orderData5.mDefectNumber = "1009";
        orderData5.mOrderDescription = "No power at nurses residence";
        orderData5.mSupplierName = "Moon and Earth trading and Projects 187";

        mOrderList.add(orderData5);
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
        mListView = (ListView) aView.findViewById(R.id.orderListID);

    }
    @Override
    public void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int aPosition, long l) {
        if (aPosition == 0)
            return;

        Intent loginIntent = new Intent();
        loginIntent.setClass(getActivity(), OrderDetailsActivity.class);
        OrderData orderData = mOrderList.get((aPosition - 1));
        loginIntent.putExtra(GDIDConstants.KEY_ORDERDETAILS, orderData);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(loginIntent);
    }


    private class OrderListAdapter extends BaseAdapter implements View.OnClickListener {
        private LayoutInflater mInflater;
        private Context mContext;
        OrderListAdapter (Context aContext) {
            mInflater = LayoutInflater.from(aContext);
            mContext = aContext;
        }
        @Override
        public int getCount() {
            return mOrderList.size();
        }

        @Override
        public Object getItem(int i) {
            return mOrderList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int aPosition, View aConvertView, ViewGroup viewGroup) {
            ViewHolder lViewHolder = null;
            if (aConvertView == null) {
                aConvertView = mInflater.inflate(R.layout.orderlist_item, null);
                lViewHolder = new ViewHolder();
//                lViewHolder.mDefectNumberTextView = (TextView) aConvertView.findViewById(R.id.defectNumberId);
                lViewHolder.mOrderNumberTextView = (TextView) aConvertView.findViewById(R.id.orderNumberId);
                lViewHolder.mOrderStatusTextView = (TextView) aConvertView.findViewById(R.id.orderStatusId);
                lViewHolder.mOrderData = mOrderList.get(aPosition);
//                lViewHolder.mDefectCreationDateTextView = (TextView) aConvertView.findViewById(R.id.defectCreationDateId);
                aConvertView.setTag(lViewHolder);
            } else {
                lViewHolder = (ViewHolder)aConvertView.getTag();
            }

//            lViewHolder.mDefectNumberTextView.setText(mBugList.get(aPosition).mDefectNumber);
            SpannableString spanString = new SpannableString(mOrderList.get(aPosition).mOrderNumber);
            spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
            lViewHolder.mOrderNumberTextView.setText(spanString);
            lViewHolder.mOrderNumberTextView.setOnClickListener(this);
            lViewHolder.mOrderStatusTextView.setText(mOrderList.get(aPosition).mOrderStatus);
//            lViewHolder.mDefectCreationDateTextView.setText(mBugList.get(aPosition).mCreatedDate);

            return aConvertView;
        }

        @Override
        public void onClick(View aView) {
            //To do
//            ViewHolder aViewHolder = (ViewHolder)aView.getTag();
//            Intent loginIntent = new Intent();
//            loginIntent.setClass(getActivity(), DefectDetailsActivity.class);
//            OrderData orderData = aViewHolder.mOrderData;
//            loginIntent.putExtra(GDIDConstants.KEY_ORDERDETAILS, orderData);
//
//            loginIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivity(loginIntent);
        }

        class ViewHolder {
            TextView mOrderNumberTextView;
            TextView mOrderStatusTextView;
            OrderData mOrderData;
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

}
