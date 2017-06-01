package com.gdid.com.gdid.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.JsonReader;
import android.util.Log;

import com.gdid.com.gdid.datamodel.BugData;
import com.gdid.com.gdid.datamodel.DashBoardData;
import com.gdid.com.gdid.utils.GDIDConstants;
import com.gdid.com.gdid.utils.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by anupamsi on 4/7/2017.
 */
public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static final int NET_REQUEST_TIMEOUT_SECS = 60;

    private Context mContext;
    private CancelObj mCancelObj;
    private int mnTimeOut;


    public NetworkManager(Context context, CancelObj cancelObj) {
        mContext = context.getApplicationContext();
        mCancelObj = cancelObj;
        if (mCancelObj == null)
            mCancelObj = new CancelObj();
        mnTimeOut = NET_REQUEST_TIMEOUT_SECS;
    }

    public static boolean isConnected(Context context) {
        if (context == null) {
            if (Log.isLoggable(TAG, Log.DEBUG))
            {
                Log.d(TAG, "Context is null");
            }
        }
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connManager)
            return false;

        NetworkInfo netInfo = connManager.getActiveNetworkInfo();
        NetworkInfo.State state = netInfo == null ? NetworkInfo.State.DISCONNECTED
                : netInfo.getState();
        if (Log.isLoggable(TAG, Log.DEBUG))
        {
            if (netInfo != null)
                Log.d(TAG, "Network state = " + netInfo.getState());
            Log.d(TAG, "isConnected, return value = "
                    + (state != null && state == NetworkInfo.State.CONNECTED));
        }
        return state != null && state == NetworkInfo.State.CONNECTED;
    }

    public JsonReader sendHttpGetJsonData(String address) throws IOException {
        JsonReader jsonReader = null;
        URL url = new URL(address);
        URLConnection connection = url.openConnection();

        connection.setConnectTimeout(mnTimeOut * 1000);
        connection.setReadTimeout(mnTimeOut * 1000);

        InputStream is = null;
        StringBuffer sb = new StringBuffer();

        try {
            is = connection.getInputStream();
            jsonReader = new JsonReader(new InputStreamReader(is,
                    "UTF-8"));

        } finally {
            if (is != null) {
                is.close();
            }
        }
        if (Log.isLoggable(TAG, Log.DEBUG))
        {
            Log.d(TAG, "Result = " + sb.toString());
        }
        return jsonReader;
    }

    public ArrayList<DashBoardData> sendHttpGetDashBoardData(String address) throws IOException {
        ArrayList<DashBoardData> dashBoardData = null;
        URL url = new URL(address);
        Log.d(TAG, "sendHttpGetDashBoardData address :" + address);

        URLConnection connection = url.openConnection();

        connection.setConnectTimeout(mnTimeOut * 1000);
        connection.setReadTimeout(mnTimeOut * 1000);

        InputStream is = null;
        StringBuffer sb = new StringBuffer();
        Log.d(TAG, "getInputStream");

        try {
            is = connection.getInputStream();
            Log.d(TAG, "is : " + is);
            dashBoardData = (ArrayList<DashBoardData>)JsonParser.parseJsonData(is, GDIDConstants.PARSE_DASHBOARD_DATA);
        } finally {
            if (is != null) {
                is.close();
            }
        }

        Log.d(TAG, "Result = " + sb.toString());

        return dashBoardData;
    }

    public ArrayList<BugData> sendHttpGetBugList(String address) throws IOException {
        ArrayList<BugData> bugList = null;
        URL url = new URL(address);
        Log.d(TAG, "sendHttpGetBugList address :" + address);

        URLConnection connection = url.openConnection();

        connection.setConnectTimeout(mnTimeOut * 1000);
        connection.setReadTimeout(mnTimeOut * 1000);

        InputStream is = null;
        StringBuffer sb = new StringBuffer();
        Log.d(TAG, "sendHttpGetBugList getInputStream");

        try {
            is = connection.getInputStream();
            Log.d(TAG, "is sendHttpGetBugList : " + is);
            bugList = (ArrayList<BugData>)JsonParser.parseJsonData(is, GDIDConstants.PARSE_DEFECTLIST_DATA);
        } finally {
            if (is != null) {
                is.close();
            }
        }

        Log.d(TAG, "Result = " + sb.toString());

        return bugList;
    }
}
