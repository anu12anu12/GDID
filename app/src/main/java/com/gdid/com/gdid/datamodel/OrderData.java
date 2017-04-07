package com.gdid.com.gdid.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anupamsi on 1/30/2017.
 */
public class OrderData implements Parcelable {
    public static final Parcelable.Creator<OrderData> CREATOR = new Parcelable.Creator<OrderData>() {

        @Override
        public OrderData[] newArray(int size) {
            return new OrderData[size];
        }

        @Override
        public OrderData createFromParcel(Parcel aSource) {
            return new OrderData(aSource);
        }
    };



    public String mOrderNumber;
    public String mDefectNumber;
    public String mModifiedData;
    public String mOrderStatus;
    public String mCreatedDate;
    public String mOrderDescription;
    public String mSupplierName;


    public OrderData() {
    }

    public OrderData(Parcel aInput) {
        mOrderNumber = aInput.readString();
        mDefectNumber = aInput.readString();
        mModifiedData = aInput.readString();
        mOrderStatus = aInput.readString();
        mCreatedDate = aInput.readString();
        mOrderDescription = aInput.readString();
        mSupplierName = aInput.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel aDestination, int aFlag) {
        aDestination.writeString(mOrderNumber);
        aDestination.writeString(mDefectNumber);
        aDestination.writeString(mModifiedData);
        aDestination.writeString(mOrderStatus);
        aDestination.writeString(mCreatedDate);
        aDestination.writeString(mOrderDescription);
        aDestination.writeString(mSupplierName);
    }
}
