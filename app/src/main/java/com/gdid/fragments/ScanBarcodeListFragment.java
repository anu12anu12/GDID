package com.gdid.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdid.activities.BarcodeReader;
import com.gdid.activities.LoginActivity;
import com.gdid.activities.ReconsileListActivity;
import com.gdid.com.gdid.databases.DBAdapter;
import com.gdid.com.gdid.datamodel.BarCodeData;
import com.gdid.com.gdid.datamodel.MaterialData;
import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.com.gdid.utils.PermissionUtils;
import com.gdid.material_management.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anupamsi on 1/4/2017.
 */
public class ScanBarcodeListFragment extends Fragment implements View.OnClickListener {
    private String mUserName;
    private ListView mListView;
    private MaterialListAdapter mAdapter;
    private ArrayList<MaterialData> mMaterialList;
    private HashMap<String, Integer> mBarCodeQuantityMap = new HashMap<String, Integer>();
    private HashMap<String, MaterialData> mBarCodeMaterialMap = new HashMap<String, MaterialData>();
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    private View mRootView;
    private Button mReconsileButton;
    private boolean mDummy;
    private DBAdapter mDbHelper;
    private IntentFilter mFilter;
    public ScanBarcodeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.scanbarcode_list, container, false);
        mUserName = "Neo";
        mDbHelper = DBAdapter.getInstance(getActivity());
        getViewID(mRootView);

        configScanDevice();
        mFilter= new IntentFilter("nlscan.action.SCANNER_RESULT");
        getActivity().registerReceiver(mReceiver, mFilter);

        return mRootView;
    }

    private void configScanDevice()
    {
        Intent intent = new Intent ("ACTION_BAR_SCANCFG");
        intent.putExtra("EXTRA_SCAN_MODE",3);
        intent.putExtra("EXTRA_SCAN_AUTOENT", 1);
        getActivity().sendBroadcast(intent);
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
        Button scanBarCodeBtn = (Button) aView.findViewById(R.id.scanBtnID);
        scanBarCodeBtn.setOnClickListener(this);
        mReconsileButton = (Button)aView.findViewById(R.id.reconsileID);
        mReconsileButton.setOnClickListener(this);
//        mReconsileButton.setVisibility(View.GONE);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int aPosition, long l) {
//        Intent loginIntent = new Intent();
//        loginIntent.setClass(getActivity(), OrderDetailsActivity.class);
//        loginIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(loginIntent);
//    }


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
            case R.id.scanBtnID:
                if (GDIDConstants.isPhone) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !PermissionUtils.isPermissionPresent(getActivity(), new String[]{Manifest.permission.CAMERA})) {
                        requestCameraPermission();
                    } else {
                        launchBarCodeScannerActivity();
                    }
                } else {
                    startScanningFromDevice();
                }
//                if (mMaterialList == null) {
//                    mMaterialList = new ArrayList<MaterialData>();
//                }
//                MaterialData materialData = getMaterialAfterScan();
//                ArrayList<MaterialData> lMaterialList = mMaterialList;
//                lMaterialList.add(materialData);
//                if (mAdapter == null) {
//                    updateTopView(mRootView);
//                    mAdapter = new MaterialListAdapter(getActivity());
//
//                    mListView.addHeaderView(getListHeaderView());
//                    mMaterialList = lMaterialList;
//                    mListView.setAdapter(mAdapter);
////                    mListView.setOnItemClickListener(this);
//                } else {
//                    mMaterialList = lMaterialList;
//                    mAdapter.notifyDataSetChanged();
//                }

                break;
            case R.id.reconsileID:
                Intent reconsileListIntent = new Intent();
                reconsileListIntent.setClass(getActivity(), ReconsileListActivity.class);
                reconsileListIntent.putParcelableArrayListExtra(GDIDConstants.SCANBARCODE_LIST_KEY, mMaterialList);
                startActivity(reconsileListIntent);
                break;
        }
    }

    private void launchBarCodeScannerActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), BarcodeReader.class);
        startActivityForResult(intent, GDIDConstants.REQUEST_CODE_PHONE_BARCODE);
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // camera-related task you need to do.
                    launchBarCodeScannerActivity();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Please accept Camera Permission to use Barcode scanner", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMaterialList instanceof Parcelable) {
            outState.putParcelable(GDIDConstants.SCANBARCODE_LIST_KEY, (Parcelable) mMaterialList);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mMaterialList = (ArrayList<MaterialData>) savedInstanceState.getParcelable(GDIDConstants.SCANBARCODE_LIST_KEY);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GDIDConstants.REQUEST_CODE_PHONE_BARCODE:
                    BarCodeData barCodeData = data.getExtras().getParcelable(GDIDConstants.KEY_SCAN_BARCODE);
                    if (barCodeData != null) {
                        processData(barCodeData.mData);
                    }
                    break;
            }
        }
    }

    private void startScanningFromDevice() {
        Intent intent = new Intent("nlscan.action.SCANNER_TRIG");
        intent.putExtra("SCAN_TIMEOUT", 4);// SCAN_TIMEOUT value: int, 1-9; unit: second
        intent.putExtra("SCAN_TYPE ", 1);// SCAN_TYPE: read two barcodes during a scan attempt
        getActivity().sendBroadcast(intent);
//        sendScanResult();
    }

    private void sendScanResult() {
        Intent intent = new Intent();
        intent.putExtra("SCAN_BARCODE1", "243335235");
        intent.putExtra("EXTRA_SCAN_STATE", "1");
        mReceiver.onReceive(getActivity(), intent);

    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver()
    {
        @Override public void onReceive(Context context, Intent intent)
        {

            String scanResult_1 = intent.getStringExtra("SCAN_BARCODE1");
            //  final Strinintent.getStringExtra("SCAN_BARCODE1")g scanResult_2 = intent.getStringExtra("SCAN_BARCODE2");
            String scanStatus = intent.getStringExtra("EXTRA_SCAN_STATE");

            if(intent != null && context != null)
            {
                if(scanResult_1 != null)
                { //Success
                    processData(scanResult_1);
//                    Toast mytoast= Toast.makeText(getActivity().getApplicationContext(), scanResult_1 + " stored", Toast.LENGTH_LONG);
//                    mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
//                    mytoast.show();

                }else
                { //Failure, e.g. operation timed out
                    Toast mytoast= Toast.makeText(getActivity().getApplicationContext(), "Scanner time out", Toast.LENGTH_LONG);
                    mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    mytoast.show();
                }
            }
            else
            {
                Toast mytoast= Toast.makeText(getActivity().getApplicationContext(), "Scan status is null", Toast.LENGTH_LONG);
                mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                mytoast.show();
            }

        }
    };

    private void updateView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mReconsileButton.setVisibility(View.VISIBLE);
                if (mAdapter == null) {
                    updateTopView(mRootView);
                    mAdapter = new MaterialListAdapter(getActivity());

                    mListView.addHeaderView(getListHeaderView());
                    mListView.setAdapter(mAdapter);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    void processData(String data)
    {
        if(data.length() > 0)
        {
            MaterialData materialData = getMaterialAfterScan(data);
            if (mMaterialList == null) {
                mMaterialList = new ArrayList<MaterialData>();
            }
            ArrayList<MaterialData> lMaterialList = mMaterialList;
            lMaterialList.add(materialData);
            mMaterialList = lMaterialList;
            updateView();
            if( mDbHelper.insertData(purify(data)))
            {

            }
            else
            {
                Toast mytoast= Toast.makeText(getActivity().getApplicationContext(), "Code not stored.", Toast.LENGTH_LONG);
                mytoast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                mytoast.show();
            }

        }
    }

    public  String purify(String txt)
    {
        String value;
        if(txt.toCharArray()[0] == '*' && txt.toCharArray()[txt.length() - 1 ] == '*' )
        {
            value = txt.substring(1,txt.lastIndexOf('*'));
        }
        else
        {
            value = txt;
        }
        return value;
    }

    private MaterialData getMaterialAfterScan(String aBarCode) {
        MaterialData materialData = null;
        int barCodeQuantity = 1;
//        Object value = mBarCodeQuantityMap.get(aBarCode);
//        if (value != null) {
//            barCodeQuantity = ((Integer) value) + 1;
//        }
        if (mDummy) {
            mDummy = false;
            materialData = new MaterialData("Scanned Item", aBarCode, barCodeQuantity, 10, 10, "W0323459", GDIDConstants.STATUS_SCAN_SAFE);
        } else {
            materialData = new MaterialData("Scanned Item", aBarCode, barCodeQuantity, 10, 10, "W0323460", GDIDConstants.STATUS_SCAN_DANGER);
            mDummy = true;
        }

//        mBarCodeMaterialMap.put(aBarCode, materialData);


        mBarCodeQuantityMap.put(aBarCode, barCodeQuantity);
        return materialData;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }
    }
}
