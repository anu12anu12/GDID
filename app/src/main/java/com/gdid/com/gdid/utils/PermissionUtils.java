package com.gdid.com.gdid.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by anupamsi on 3/26/2017.
 */
public class PermissionUtils {
    /**
     * Method which returns Permission is present or not.
     * @return Returns true if Permission is present.
     */
    public static boolean isPermissionPresent(Context aContext, String[] aPermissionList) {
        boolean isPermissionPreent = false;
        if (hasSelfPermission(aContext, aPermissionList)) {
            isPermissionPreent = true;
        }
        return isPermissionPreent;
    }

    public static boolean hasSelfPermission(Context activity, String[] permissions) {

        if(permissions!= null){
            // Verify that all required permissions have been granted
            for (String permission : permissions) {
                if(permission!= null){
                    if (checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private static int checkSelfPermission(Context context, String permission) {
        return context.checkPermission(permission, android.os.Process.myPid(),
                android.os.Process.myUid());
    }
}
