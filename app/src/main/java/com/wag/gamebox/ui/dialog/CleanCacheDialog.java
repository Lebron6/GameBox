package com.wag.gamebox.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.wag.gamebox.R;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.tools.FileUtil;

import org.xutils.x;

/**
 * Created by Administrator on 2016/7/5.
 */
public class CleanCacheDialog extends Dialog {
    private TextView wenzi;
    private View btn;

    public CleanCacheDialog(Context context) {
        super(context);
    }

    public CleanCacheDialog(Context context, int theme) {
        super(context, theme);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_clean_cache);
        initView();
    }

    private void initView() {
        btn = findViewById(R.id.lv_btn);
        wenzi = (TextView)findViewById(R.id.zi);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CleanCacheDialog.this.dismiss();
            }
        });
        new Thread(new Runnable(){

            public void run(){

                try {
                    Thread.sleep(2000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                    wenzi.setText("缓存清除完毕！");
                    btn.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void show() {
//        FileUtil.deleteFolderFile(FileUtil.getDir_de().getPath(), true, wenzi, btn);
        x.image().clearMemCache();
        DownLoadManger.getInstance().deleteAllApk();
       // x.image().clearCacheFiles();
        super.show();
    }
}
