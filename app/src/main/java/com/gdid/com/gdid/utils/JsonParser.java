package com.gdid.com.gdid.utils;

import android.util.JsonReader;
import android.util.Log;

import com.gdid.com.gdid.datamodel.BugData;
import com.gdid.com.gdid.datamodel.DashBoardData;
import com.gdid.com.gdid.datamodel.OrderData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by anupamsi on 4/7/2017.
 */
public class JsonParser {
    private static final String TAG = "JsonParser";
    private static final String UTF_8 = "UTF-8";

    public static Object parseJsonData(InputStream is, int aParseType)
            throws IOException {
        Log.d(TAG, "parseJsonData");

        JsonReader jsonReader = new JsonReader(new InputStreamReader(is,
                UTF_8));
        if (aParseType == GDIDConstants.PARSE_DASHBOARD_DATA) {
            return readDashBoardDataList(jsonReader);
        } else if (aParseType == GDIDConstants.PARSE_DEFECTLIST_DATA) {
            return readDefectList(jsonReader);
        } else {

        }
        return null;
    }
    private static ArrayList<BugData> readDefectList(
            JsonReader reader) throws IOException {
        Log.d(TAG, "readDefectList");

        ArrayList<BugData> defectListData = new ArrayList<BugData>();

        reader.beginArray();
        while (reader.hasNext()) {
            defectListData.add(readDefects(reader));
        }
        reader.endArray();
        return defectListData;
    }

    private static ArrayList<DashBoardData> readDashBoardDataList(
            JsonReader reader) throws IOException {
        Log.d(TAG, "readDashBoardDataList");

        ArrayList<DashBoardData> dashBoardData = new ArrayList<DashBoardData>();

        reader.beginArray();
        while (reader.hasNext()) {
            dashBoardData.add(readDashBoardData(reader));
        }
        reader.endArray();
        return dashBoardData;
    }

    private static DashBoardData readDashBoardData(JsonReader reader)
            throws IOException {
        String summaryText = null;
        String descriptionText = null;
        String hexColorCode = null;
        long id = -1;
        Log.d(TAG, "readDashBoardData");


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(GDIDConstants.KEY_DASHBOARD_SUMMARY)) {
                summaryText = reader.nextString();
            } else if (name.equals(GDIDConstants.KEY_DASHBOARD_DESCRIPTION)) {
                descriptionText = reader.nextString();
            } else if (name.equals(GDIDConstants.KEY_DASHBOARD_BACKGROUNDCOLOR)) {
                hexColorCode = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        DashBoardData dashBoardData = new DashBoardData();
        dashBoardData.setmDescriptionText(descriptionText);
        dashBoardData.setmHexColorCode(hexColorCode);
        dashBoardData.setmResult(summaryText);
        Log.d(TAG, "dashBoardData");

        return dashBoardData;
    }

    private static OrderData readOrder(JsonReader reader) throws IOException {
        OrderData orderData = new OrderData();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(GDIDConstants.KEY_ORDERNUMBER)) {
                orderData.mOrderNumber = reader.nextString();
            } else if (name.equals(GDIDConstants.KEY_ORDERTITLE)) {
                orderData.mOrderDescription = reader.nextString();
            } else if (name.equals(GDIDConstants.KEY_ORDERSTATUS)) {
                orderData.mOrderStatus = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_ORDERDESCRIPTION)) {
                orderData.mOrderDescription = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_ORDERCREATEDDATE)) {
                orderData.mCreatedDate = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_ODREMODIFIEDDATE)) {
                orderData.mModifiedData = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_ORDERSUPPLIERNAME)) {
                orderData.mSupplierName = reader.nextString();
            }
            else {
                reader.skipValue();
            }
        }

        reader.endObject();

        return orderData;
    }


    private static BugData readDefects(JsonReader reader)
            throws IOException {
        String defectID = null;
        String defectTitle = null;
        String defectDescription = null;
        String defectUrgency = null;
        String defectLocation = null;
        String defectReportedDate = null;
        String defectWorkOrder = null;
        ArrayList<OrderData> defectOrderList = null;

        long id = -1;
        Log.d(TAG, "readDefects");


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(GDIDConstants.KEY_DEFECTD)) {
                defectID = reader.nextString();
            } else if (name.equals(GDIDConstants.KEY_DEFECTTITLE)) {
                defectTitle = reader.nextString();
            } else if (name.equals(GDIDConstants.KEY_DEFECTDESCRIPTION)) {
                defectDescription = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_URGENCY)) {
                defectUrgency = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_LOCATION)) {
                defectLocation = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_REPORTEDDATE)) {
                defectReportedDate = reader.nextString();
            }else if (name.equals(GDIDConstants.KEY_WORKORDER)) {
                defectWorkOrder = reader.nextString();
            }
            else if (name.equals(GDIDConstants.KEY_ORDERLIST)) {
                defectOrderList = getOrderList(reader);
            }
            else {
                reader.skipValue();
            }
        }
        reader.endObject();

        BugData defectData = new BugData();
        defectData.mDefectTitle = defectTitle;
        defectData.mDefectNumber = defectID;
        defectData.mDefectDescription = defectDescription;
        defectData.mDefectStatus = defectUrgency;
        defectData.mLocation = defectLocation;
        defectData.mCreatedDate = defectReportedDate;
        defectData.mWorkOrders = defectWorkOrder;
        defectData.mOrderList = defectOrderList;
        Log.d(TAG, "defectData");

        return defectData;
    }

    private static ArrayList<OrderData> getOrderList(JsonReader reader) {
        ArrayList<OrderData> orderList = new ArrayList<OrderData>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                orderList.add(readOrder(reader));
            }
            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
