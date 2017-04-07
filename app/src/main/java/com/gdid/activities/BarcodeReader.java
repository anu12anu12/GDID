package com.gdid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.gdid.material_management.R;
import com.gdid.phone.barcodemanager.CameraManager;
import com.gdid.phone.barcodemanager.CameraPreview;
import com.gdid.phone.barcodemanager.HoverView;

public class BarcodeReader extends Activity {
    private CameraPreview mPreview;
    private CameraManager mCameraManager;
    private HoverView mHoverView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_barcode);
		
		mHoverView = (HoverView)findViewById(R.id.hover_view);
		mHoverView.update(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
		
		mCameraManager = new CameraManager(this);
        mPreview = new CameraPreview(this, mCameraManager.getCamera());
        mPreview.setArea(mHoverView.getHoverLeft(), mHoverView.getHoverTop(), mHoverView.getHoverAreaWidth(), getResources().getDisplayMetrics().widthPixels);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        
//        getActionBar().hide();
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
        mCameraManager.onPause(); 
    }

	@Override
	protected void onResume() {
		super.onResume();
		mCameraManager.onResume();
		mPreview.setCamera(mCameraManager.getCamera());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		setResult(Activity.RESULT_OK, data);
		finish();
	}
}
