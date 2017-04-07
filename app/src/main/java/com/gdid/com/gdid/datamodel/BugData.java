package com.gdid.com.gdid.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anupamsi on 1/30/2017.
 */
public class BugData implements Parcelable {
    public static final Parcelable.Creator<BugData> CREATOR = new Parcelable.Creator<BugData>() {

        @Override
        public BugData[] newArray(int size) {
            return new BugData[size];
        }

        @Override
        public BugData createFromParcel(Parcel aSource) {
            return new BugData(aSource);
        }
    };

    public String mDefectNumber;
    public String mDefectTitle;
    public String mDefectStatus;
    public String mCreatedDate;
    public String mDefectDescription;
    public String mLocation;
    public String mWorkOrders;


    public BugData() {
    }

    public BugData(Parcel aInput) {
        mDefectNumber = aInput.readString();
        mDefectTitle = aInput.readString();
        mDefectStatus = aInput.readString();
        mCreatedDate = aInput.readString();
        mDefectDescription = aInput.readString();
        mLocation = aInput.readString();
        mWorkOrders = aInput.readString();


    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel aDestination, int aFlag) {
        aDestination.writeString(mDefectNumber);
        aDestination.writeString(mDefectTitle);
        aDestination.writeString(mDefectStatus);
        aDestination.writeString(mCreatedDate);
        aDestination.writeString(mDefectDescription);
        aDestination.writeString(mLocation);
        aDestination.writeString(mWorkOrders);


    }

}
