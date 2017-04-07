package com.gdid.com.gdid.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anupamsi on 3/26/2017.
 */
public class BarCodeData implements Parcelable {
    public static final Parcelable.Creator<BarCodeData> CREATOR = new Parcelable.Creator<BarCodeData>() {

        @Override
        public BarCodeData[] newArray(int size) {
            return new BarCodeData[size];
        }

        @Override
        public BarCodeData createFromParcel(Parcel aSource) {
            return new BarCodeData(aSource);
        }
    };

    public String mData;
    public String mFormat;
    public String mMetaData;
    public String mTimeStamp;
    public String mBarCodeScanCount;

    public BarCodeData(String aData, String aFormat, String aMetaData, String aTimeStamp,
                       String aBarCodeScanCount) {
        mData = aData;
        mFormat = aFormat;
        mMetaData = aMetaData;
        mTimeStamp = aTimeStamp;
        mBarCodeScanCount = aBarCodeScanCount;
    }

    public BarCodeData() {
    }

    public BarCodeData(Parcel aInput) {
        mData = aInput.readString();
        mFormat = aInput.readString();
        mMetaData = aInput.readString();
        mTimeStamp = aInput.readString();
        mBarCodeScanCount = aInput.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel aDestination, int aFlag) {
        aDestination.writeString(mData);
        aDestination.writeString(mFormat);
        aDestination.writeString(mMetaData);
        aDestination.writeString(mTimeStamp);
        aDestination.writeString(mBarCodeScanCount);
    }
}