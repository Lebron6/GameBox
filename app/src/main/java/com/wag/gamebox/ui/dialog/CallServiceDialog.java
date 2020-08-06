package com.wag.gamebox.ui.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.tools.Utils;

public class CallServiceDialog extends Dialog {


    TextView tvCall;
    TextView tvClose;
    TextView tvPhoneNum;
    private String phoneNum;
    private Context context;

    public CallServiceDialog(Context context) {
        super(context);
    }

    public CallServiceDialog(Context context, int theme, String phoneNum) {
        super(context, theme);
        this.phoneNum = phoneNum;
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_call_phone);
        initView();
    }

    private void initView() {
        tvPhoneNum = findViewById(R.id.tv_phone_num);
        tvCall = findViewById(R.id.tv_call);
        tvClose = findViewById(R.id.tv_close);
        tvPhoneNum.setText("号码：" + phoneNum);
        tvClose.setOnClickListener(onClickListener);
        tvCall.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_call:
                    callPhone(phoneNum);
                    dismiss();
                    break;
                case R.id.tv_close:
                    dismiss();
                    break;
            }
        }
    };

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

}
