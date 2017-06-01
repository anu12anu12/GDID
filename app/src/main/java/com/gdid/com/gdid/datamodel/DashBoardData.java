package com.gdid.com.gdid.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anupamsi on 1/29/2017.
 */
public class DashBoardData implements Parcelable {
    public static final Parcelable.Creator<DashBoardData> CREATOR = new Parcelable.Creator<DashBoardData>() {

        @Override
        public DashBoardData[] newArray(int size) {
            return new DashBoardData[size];
        }

        @Override
        public DashBoardData createFromParcel(Parcel aSource) {
            return new DashBoardData(aSource);
        }
    };
    public static int TYPE_ORDER_CREATED = 1;
    public static int TYPE_ORDER_ALLOCAED_PENDINGACK = 2;
    public static int TYPE_PENDINGMATERIAL_PREPERATION = 3;
    public static int TYPE_PENDINGCOLLECTION_BYSTOREMAN = 4;

    public String mUserName;
    public String mResult;
    public String mDescriptionText;
    public int mDisplayType;
    public String mHexColorCode;

    public DashBoardData() {}

    public DashBoardData(Parcel aInput) {
        mResult = aInput.readString();
        mDescriptionText = aInput.readString();
        mHexColorCode = aInput.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel aDestination, int aFlag) {
        aDestination.writeString(mResult);
        aDestination.writeString(mDescriptionText);
        aDestination.writeString(mHexColorCode);
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String aUserName) {
        this.mUserName = aUserName;
    }

    public String getmResult() {
        return mResult;
    }

    public void setmResult(String aResult) {
        this.mResult = aResult;
    }

    public String getmDescriptionText() {
        return mDescriptionText;
    }

    public void setmDescriptionText(String aDescriptionText) {
        this.mDescriptionText = aDescriptionText;
    }

    public int getmDisplayType() {
        return mDisplayType;
    }

    public void setmDisplayType(int aDisplayType) {
        this.mDisplayType = aDisplayType;
    }

    public String getmHexColorCode() {
        return mHexColorCode;
    }

    public void setmHexColorCode(String aHexColorCode) {
        this.mHexColorCode = aHexColorCode;
    }
}
