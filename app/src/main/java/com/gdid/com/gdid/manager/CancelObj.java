package com.gdid.com.gdid.manager;

/**
 * Created by anupamsi on 4/7/2017.
 */
public class CancelObj {
    private boolean cancel = false;

    public CancelObj() {
        cancel = false;
    }

    public void cancel() {
        cancel = true;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void resume() {
        cancel = false;
    }

}
