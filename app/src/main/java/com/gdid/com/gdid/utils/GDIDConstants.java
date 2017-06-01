package com.gdid.com.gdid.utils;

/**
 * Created by anupamsi on 1/4/2017.
 */
public interface GDIDConstants {
    String DASHBOARD_DATA_URL = "https://gist.githubusercontent.com/anu12anu12/cc529c822f7fab5a1f3380d3f43d347b/raw/d0b41aef762f7404fd9c68145f86e8a9192ce43f/DashBoardDataURL";
    String DEFECT_ORDER_LIST_URL = "https://gist.githubusercontent.com/anu12anu12/f9ed7a971355f198a494892ad76f653d/raw/687fa7c26108aa1ee12cc08f21b3581fea3d9dd7/DefectOrderList";

    int PARSE_DASHBOARD_DATA = 1;
    int PARSE_DEFECTLIST_DATA = 2;
    int PARSE_ORDERLIST_DATA = 3;

    int DASHBOARD_POSITION = 0;
    int BUGLIST_POSITION = 1;
    int ORDERLIST_POSITION = 2;
    int SCANBARCODE_POSITION = 3;
    int ORDERDETAILS_POSITION = 4;
    String SCANBARCODE_LIST_KEY = "ScanBarCodeList";

    int STATUS_SCAN_SAFE = 1;
    int STATUS_SCAN_DANGER = 2;
    boolean isPhone = true;
    String KEY_SCAN_BARCODE = "KEY_SCAN_BARCODE";
    int REQUEST_CODE_PHONE_BARCODE = 1;
    String KEY_ORDERDETAILS = "OrderDetails";
    String KEY_DEFECTDETAILS = "DefectDetails";
    String KEY_DASHBOARD_DATA = "DashBoardData";
    String KEY_BUGLIST = "BugListData";



    //DashBoard Json Key
    String KEY_DASHBOARD_SUMMARY = "result";
    String KEY_DASHBOARD_DESCRIPTION = "descriptiontext";
    String KEY_DASHBOARD_BACKGROUNDCOLOR = "hexcolorcode";
    String ACTION_DASHBOARD_RECEIVER = "android.intent.action.GDID_DASHBOARD_DATA";

    //Defect List Keys
    String KEY_DEFECTD = "defectid";
    String KEY_DEFECTTITLE = "defectitle";
    String KEY_DEFECTDESCRIPTION = "defectdescription";
    String KEY_URGENCY = "urgency";
    String KEY_LOCATION = "location";
    String KEY_REPORTEDDATE = "reportedddate";
    String KEY_WORKORDER = "workorder";
    String KEY_ORDERLIST = "orderlist";


    //OrderList Keys
    String KEY_ORDERNUMBER = "defectnumber";
    String KEY_ORDERTITLE = "ordernumber";
    String KEY_ORDERSTATUS = "orderstatus";
    String KEY_ORDERDESCRIPTION = "orderdescription";
    String KEY_ORDERCREATEDDATE = "ordercreateddate";
    String KEY_ODREMODIFIEDDATE = "ordermodifieddate";
    String KEY_ORDERSUPPLIERNAME = "suppliername";




}
