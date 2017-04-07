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
import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.material_management.R;

import java.util.ArrayList;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class ReconsileListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private String mUserName;
    private ListView mListView;
    private MaterialListAdapter mAdapter;
    private ArrayList<MaterialData> mMaterialList;

    public ReconsileListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reconsilelist, container, false);
        getViewID(rootView);
        getMaterialList();
        updateTopView(rootView);
        mAdapter = new MaterialListAdapter(getActivity());

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        return rootView;
    }


    private void getMaterialList() {
        mUserName = "Neo";
        mMaterialList = getActivity().getIntent().getParcelableArrayListExtra(GDIDConstants.SCANBARCODE_LIST_KEY);
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
                aConvertView = mInflater.inflate(R.layout.reconsile_item, null);
                lViewHolder = new ViewHolder();
                lViewHolder.mOrderNumberValueTextView = (TextView) aConvertView.findViewById(R.id.orderKeyID);
                lViewHolder.mMaterialCountTextView = (TextView) aConvertView.findViewById(R.id.materialListCountID);
                lViewHolder.mScannedMaterialListCountTextView = (TextView) aConvertView.findViewById(R.id.scanMaterialListCountID);
                lViewHolder.mMainLayout = (TableLayout) aConvertView.findViewById(R.id.listitemMainLayoutID);

                aConvertView.setTag(lViewHolder);
            } else {
                lViewHolder = (ViewHolder) aConvertView.getTag();
            }

            lViewHolder.mOrderNumberValueTextView.setText(getActivity().getString(R.string.str_ordernumberwithcolon) +mMaterialList.get(aPosition).mOrderNumber);
            lViewHolder.mMaterialCountTextView.setText(getActivity().getString(R.string.str_materiallistcount) + mMaterialList.get(aPosition).mMaterialListCount+"");
            lViewHolder.mScannedMaterialListCountTextView.setText(getActivity().getString(R.string.str_scannedmateriallistcount) + mMaterialList.get(aPosition).mScannedMaterialListCount+"");
            int color = R.color.safe;
            switch (mMaterialList.get(aPosition).mStatus) {
                case GDIDConstants.STATUS_SCAN_DANGER:
                    color = R.color.safe;
                    break;
                case GDIDConstants.STATUS_SCAN_SAFE:
                    color = R.color.danger;
                    break;
            }
            lViewHolder.mMainLayout.setBackgroundColor(getActivity().getResources().getColor(color));
            return aConvertView;
        }

        class ViewHolder {
            TextView mOrderNumberValueTextView;
            TextView mMaterialCountTextView;
            TextView mScannedMaterialListCountTextView;
            TableLayout mMainLayout;
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
