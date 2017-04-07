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
import com.gdid.com.gdid.datamodel.MaterialData;
import com.gdid.material_management.R;

import java.util.ArrayList;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class MaterialListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String mUserName;
    private ListView mListView;
    private MaterialListAdapter mAdapter;
    private ArrayList<MaterialData> mMaterialList;

    public MaterialListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.materiallist, container, false);
        getViewID(rootView);
        getMaterialList();
        updateTopView(rootView);
        mAdapter = new MaterialListAdapter(getActivity());

        mListView.addHeaderView(getListHeaderView());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        return rootView;
    }

    private View getListHeaderView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View listHeaderView = layoutInflater.inflate(R.layout.detailsheadercolumnthree, null);
        TableLayout headerMainLayout = (TableLayout)listHeaderView.findViewById(R.id.listitemMainLayoutID);

        headerMainLayout.setBackgroundColor(getResources().getColor(R.color.dashboardbgblue));

        TextView materialDescTextView = (TextView)listHeaderView.findViewById(R.id.key1ID);
        materialDescTextView.setText(getResources().getString(R.string.str_materialdesc));
        materialDescTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView barCodeTextView = (TextView)listHeaderView.findViewById(R.id.key2ID);
        barCodeTextView.setText(getResources().getString(R.string.str_barcode));
        barCodeTextView.setTextColor(getResources().getColor(android.R.color.white));

        TextView quantityTextView = (TextView)listHeaderView.findViewById(R.id.key3ID);
        quantityTextView.setText(getResources().getString(R.string.str_quantity));
        quantityTextView.setTextColor(getResources().getColor(android.R.color.white));

        return listHeaderView;
    }

    private void getMaterialList() {
        mUserName = "Neo";
        mMaterialList = new ArrayList<MaterialData>();

        MaterialData materialData1 = new MaterialData();
        materialData1.mMaterialDesc = "Electrical Bulb Type1";
        materialData1.mBarcode = "23244352355";
        materialData1.mQuantity = 3;
        mMaterialList.add(materialData1);

        MaterialData materialData2 = new MaterialData();
        materialData2.mMaterialDesc = "Electrical Bulb Type2";
        materialData2.mBarcode = "235436677";
        materialData2.mQuantity = 4;
        mMaterialList.add(materialData2);

        MaterialData materialData3 = new MaterialData();
        materialData3.mMaterialDesc = "Electrical Bulb Type3";
        materialData3.mBarcode = "544366767";
        materialData3.mQuantity = 5;
        mMaterialList.add(materialData3);

        MaterialData materialData4 = new MaterialData();
        materialData4.mMaterialDesc = "Electrical Bulb Type4";
        materialData4.mBarcode = "235436666";
        materialData4.mQuantity = 3;
        mMaterialList.add(materialData4);
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
        mListView = (ListView) aView.findViewById(R.id.materialListID);

    }
    @Override
    public void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int aPosition, long l) {
        Intent loginIntent = new Intent();
        loginIntent.setClass(getActivity(), OrderDetailsActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(loginIntent);
    }


    private class MaterialListAdapter extends BaseAdapter{
        private LayoutInflater mInflater;
        private Context mContext;
        MaterialListAdapter (Context aContext) {
            mInflater = LayoutInflater.from(aContext);
            mContext = aContext;
        }
        @Override
        public int getCount() {
            return mMaterialList.size();
        }

        @Override
        public Object getItem(int i) {
            return mMaterialList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int aPosition, View aConvertView, ViewGroup viewGroup) {
            ViewHolder lViewHolder = null;
            if (aConvertView == null) {
                aConvertView = mInflater.inflate(R.layout.tablecolumnthree_item, null);
                lViewHolder = new ViewHolder();
                lViewHolder.mMaterialDesc = (TextView) aConvertView.findViewById(R.id.column1ID);
                lViewHolder.mBarcodeNumber = (TextView) aConvertView.findViewById(R.id.column2ID);
                lViewHolder.mQuantity = (TextView) aConvertView.findViewById(R.id.column3ID);

                aConvertView.setTag(lViewHolder);
            } else {
                lViewHolder = (ViewHolder) aConvertView.getTag();
            }

            lViewHolder.mMaterialDesc.setText(mMaterialList.get(aPosition).mMaterialDesc);
            lViewHolder.mBarcodeNumber.setText(mMaterialList.get(aPosition).mBarcode);
            lViewHolder.mQuantity.setText(mMaterialList.get(aPosition).mQuantity+"");

            return aConvertView;
        }

        class ViewHolder {
            TextView mMaterialDesc;
            TextView mBarcodeNumber;
            TextView mQuantity;

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
            case R.id.reconsileID:

                break;
        }
    }

}
