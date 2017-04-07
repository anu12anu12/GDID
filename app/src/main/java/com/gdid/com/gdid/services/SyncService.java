package com.gdid.com.gdid.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by anupamsi on 4/7/2017.
 */
public class SyncService extends IntentService {
    private static final String TAG = "SyncService";
    public SyncService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent aIntent) {

    }
}
