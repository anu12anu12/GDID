package com.gdid.phone.barcodemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.gdid.com.gdid.datamodel.BarCodeData;
import com.gdid.com.gdid.utils.GDIDConstants;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private static final String TAG = "CameraPreview";
    private int mWidth, mHeight;
    private Context mContext;
    private MultiFormatReader mMultiFormatReader;
    private AlertDialog mDialog;
    private int mLeft, mTop, mAreaWidth, mAreaHeight;
    private boolean mBarCodeScannedSucessfully = false;
    
    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Parameters params = mCamera.getParameters();
        if (params.getSupportedFocusModes().contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
        	params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        
        mWidth = 640;
        mHeight = 480;
        
        params.setPreviewSize(mWidth, mHeight); 
        mCamera.setParameters(params);
        
        mMultiFormatReader = new MultiFormatReader();
        
        mDialog =  new AlertDialog.Builder(mContext).create();
    }

    @Override
	public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
	public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mHolder.getSurface() == null){
          return;
        }

        try {
            mCamera.stopPreview();
            
        } catch (Exception e){

        }

        try {
            mCamera.setPreviewCallback(mPreviewCallback);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
    
    public void setCamera(Camera camera) {
    	mCamera = camera;
    }
    
    public void onPause() {
    	if (mCamera != null) {
    		mCamera.setPreviewCallback(null);
    		mCamera.stopPreview();
    	}
    }
    
    private PreviewCallback mPreviewCallback = new PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
        	
        	if (mDialog.isShowing())
        		return;
        	
        	LuminanceSource source = new PlanarYUVLuminanceSource(data, mWidth, mHeight, mLeft, mTop, mAreaWidth, mAreaHeight, false);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
              source));
            Result result;
          
            try {
				result = mMultiFormatReader.decode(bitmap, null);
				if (result != null) {
//					mDialog.setTitle("Result");
//					mDialog.setMessage(result.getText());
//					mDialog.show();
					Toast.makeText(mContext, "Bar Code Scanned Successfuly", Toast.LENGTH_LONG).show();
					if (mBarCodeScannedSucessfully) {
						mBarCodeScannedSucessfully = true;
						mCamera.setPreviewCallback(null);
						return;
					}
					BarCodeData lBarCodeData = new BarCodeData();
					lBarCodeData.mData = result.getText();
//					lBarCodeData.mMetaData = result.getResultMetadata().toString();
//					lBarCodeData.mFormat = result.getBarcodeFormat().toString();
					lBarCodeData.mTimeStamp = String.valueOf(result.getTimestamp());
					lBarCodeData.mBarCodeScanCount = "0";
					
					Log.d(TAG, "Data = " + lBarCodeData.mData);
					Log.d(TAG, "Metadata = " + lBarCodeData.mMetaData);
					Log.d(TAG, "Format = " + lBarCodeData.mFormat);
					Log.d(TAG, "Timestamp = " + lBarCodeData.mTimeStamp);
					Log.d(TAG, "mBarCodeScanCount = " + lBarCodeData.mBarCodeScanCount);

//					BarCodeData barCodeData = DataBaseManager.getInstance(mContext).getBarCodeData(mContext, lBarCodeData.mData);
					if (lBarCodeData != null) {
						Log.d(TAG, "barCodeData.mBarCodeScanCount : " + lBarCodeData.mBarCodeScanCount);

						int scanCount = Integer.valueOf(lBarCodeData.mBarCodeScanCount) + 1;
						lBarCodeData.mBarCodeScanCount = String.valueOf(scanCount);
						Log.d(TAG, "updated scan count : " + lBarCodeData.mBarCodeScanCount);
						Log.d(TAG, "updateBarCodeData");
//						DataBaseManager.getInstance(mContext).updateBarCodeData(mContext, lBarCodeData);
					} else {
						Log.d(TAG, "saveBarCodeData");
						lBarCodeData.mBarCodeScanCount = "1";
//						DataBaseManager.getInstance(mContext).saveBarCodeData(mContext, lBarCodeData);
					}

                    Intent intent = new Intent();
                    intent.putExtra(GDIDConstants.KEY_SCAN_BARCODE, lBarCodeData);
                    ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
					((Activity) mContext).finish();
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
        }
    };
    
    public void setArea(int left, int top, int areaWidth, int width) {
    	double ratio = width / mWidth;
    	mLeft = (int) (left / (ratio + 1));
    	mTop = (int) (top / (ratio + 1));
    	mAreaHeight = mAreaWidth = mWidth - mLeft * 2;
    }
    
}
