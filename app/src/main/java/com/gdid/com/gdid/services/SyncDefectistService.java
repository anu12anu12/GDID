package com.gdid.com.gdid.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.gdid.com.gdid.datamodel.BugData;
import com.gdid.com.gdid.datamodel.DashBoardData;
import com.gdid.com.gdid.manager.CancelObj;
import com.gdid.com.gdid.manager.NetworkManager;
import com.gdid.com.gdid.utils.GDIDConstants;

import java.util.ArrayList;

/**
 * Created by anupamsi on 4/7/2017.
 */
public class SyncDefectistService extends IntentService {
    private static final String TAG = "SyncService";
    private CancelObj mCancelDashBoardObj;
    public SyncDefectistService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent aIntent) {
        Log.d(TAG, "onHandleIntent");
        ArrayList<BugData> bugList = getBugList();
        Intent intent = new Intent(GDIDConstants.ACTION_DASHBOARD_RECEIVER);
        if (bugList != null) {
            intent.putParcelableArrayListExtra(GDIDConstants.KEY_BUGLIST, bugList);
        }
        sendBroadcast(intent);
        Log.d(TAG, "sendBroadcast");

    }

    private ArrayList<DashBoardData> getDashBoardDataList() {
        Log.d(TAG, "sendBroadcast");

        mCancelDashBoardObj = new CancelObj();
        NetworkManager networkManager = new NetworkManager(this, mCancelDashBoardObj);
        ArrayList<DashBoardData> arrayListDashBoardData = null;
        try {
            arrayListDashBoardData = networkManager.sendHttpGetDashBoardData(GDIDConstants.DASHBOARD_DATA_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayListDashBoardData;
    }

    private ArrayList<BugData> getBugList() {
        Log.d(TAG, "getBugList");

        mCancelDashBoardObj = new CancelObj();
        NetworkManager networkManager = new NetworkManager(this, mCancelDashBoardObj);
        ArrayList<BugData> bugList = null;
        try {
            bugList = networkManager.sendHttpGetBugList(GDIDConstants.DEFECT_ORDER_LIST_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bugList;
    }
}
