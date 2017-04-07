package com.gdid.com.gdid.datamodel;

/**
 * Created by anupamsi on 1/29/2017.
 */
public class DashBoardData {
    public static int TYPE_ORDER_CREATED = 1;
    public static int TYPE_ORDER_ALLOCAED_PENDINGACK = 2;
    public static int TYPE_PENDINGMATERIAL_PREPERATION = 3;
    public static int TYPE_PENDINGCOLLECTION_BYSTOREMAN = 4;

    public String mUserName;
    public String mResult;
    public String mDescriptionText;
    public int mDisplayType;
    public String mHexColorCode;
}
