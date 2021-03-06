package com.wag.gamebox.callback;

import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class BaseUiListener implements IUiListener {

    protected void doComplete(Object values) {

    }

    @Override
    public void onComplete(Object o) {
        //        mBaseMessageText.setText("onComplete:");
//
//        mMessageText.setText(response.toString());

        doComplete(o);
    }

    @Override

    public void onError(UiError e) {

        Log.e("onError:", "code:" + e.errorCode + ", msg:"

                + e.errorMessage + ", detail:" + e.errorDetail);

    }

    @Override

    public void onCancel() {

        Log.e("onCancel", "");

    }

}