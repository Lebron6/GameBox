package com.wag.gamebox.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.wag.gamebox.R;
import com.wag.gamebox.tools.Utils;

public class GetGiftSuccessDialog extends Dialog {


    TextView tvGiftCode;
    TextView tvCopy;
    TextView tvUseType;
    TextView tvClose;
    private String giftCode;
    private String giftUserType;

    public GetGiftSuccessDialog(Context context) {
        super(context);
    }

    public GetGiftSuccessDialog(Context context, int theme, String giftCode, String giftUserType) {
        super(context, theme);
        this.giftCode=giftCode;
        this.giftUserType=giftUserType;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_get_gift_suc);
        initView();
    }

    private void initView() {
        tvGiftCode = findViewById(R.id.tv_gift_code);
        tvCopy = findViewById(R.id.tv_copy);
        tvUseType = findViewById(R.id.tv_use_type);
        tvClose = findViewById(R.id.tv_close);
        tvGiftCode.setText("礼包码："+giftCode);
        tvUseType.setText(giftUserType);
        tvClose.setOnClickListener(onClickListener);
        tvCopy.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_copy:
                    Utils.copy(giftCode);
                    break;
                case R.id.tv_close:
                    dismiss();
                    break;
            }
        }
    };



}
