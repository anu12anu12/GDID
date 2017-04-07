package com.gdid.com.gdid.datamodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anupamsi on 3/8/2017.
 */
public class MaterialData implements Parcelable {
    public static final Parcelable.Creator<MaterialData> CREATOR = new Parcelable.Creator<MaterialData>() {

        @Override
        public MaterialData[] newArray(int size) {
            return new MaterialData[size];
        }

        @Override
        public MaterialData createFromParcel(Parcel aSource) {
            return new MaterialData(aSource);
        }
    };
    public String mMaterialDesc;
    public String mBarcode;
    public int mQuantity;
    public int mMaterialListCount;
    public int mScannedMaterialListCount;
    public String mOrderNumber;
    public int mStatus;


    public MaterialData(String aMaterialDescription, String aBarCode, int aQuantity, int aMaterialListCount,
                        int aScannedMaterialListCount, String aOrderNumber, int aStatus) {
        mMaterialDesc = aMaterialDescription;
        mBarcode = aBarCode;
        mQuantity = aQuantity;
        mMaterialListCount = aMaterialListCount;
        mScannedMaterialListCount = aScannedMaterialListCount;
        mOrderNumber = aOrderNumber;
        mStatus = aStatus;
    }

    public MaterialData() {
    }

    public MaterialData(Parcel aInput) {
        mMaterialDesc = aInput.readString();
        mBarcode = aInput.readString();
        mQuantity = aInput.readInt();
        mMaterialListCount = aInput.readInt();
        mScannedMaterialListCount = aInput.readInt();
        mOrderNumber = aInput.readString();
        mStatus = aInput.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel aDestination, int aFlag) {
        aDestination.writeString(mMaterialDesc);
        aDestination.writeString(mBarcode);
        aDestination.writeInt(mQuantity);
        aDestination.writeInt(mMaterialListCount);
        aDestination.writeInt(mScannedMaterialListCount);
        aDestination.writeString(mOrderNumber);
        aDestination.writeInt(mStatus);

    }

}
