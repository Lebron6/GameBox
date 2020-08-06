package com.wag.gamebox.ui.dialog;

import android.Manifest;
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

public class InstallAppDialog extends Dialog {


    private View.OnClickListener onClickListener;
    private Context context;
    private TextView tvCall;

    public InstallAppDialog(Context context) {
        super(context);
    }

    public InstallAppDialog(Context context, int theme, View.OnClickListener onClickListener) {
        super(context, theme);
        this.onClickListener = onClickListener;
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_install_app);
        initView();
    }

    private void initView() {
        tvCall=findViewById(R.id.tv_close);
        tvCall.setOnClickListener(onClickListener);
    }



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
