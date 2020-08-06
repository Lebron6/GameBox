package com.wag.gamebox.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wag.gamebox.R;
import com.wag.gamebox.entity.GameInfo;
import com.wag.gamebox.server.KeepLifeService;
import com.wag.gamebox.tools.DownLoadManger;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.tools.ZipStream;
import com.wag.gamebox.ui.adapter.PagerAdapter;
import com.wag.gamebox.ui.dialog.InstallAppDialog;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_gifts)
    RadioButton rbGifts;
    @BindView(R.id.rb_activitys)
    RadioButton rbActivitys;
    @BindView(R.id.rb_mcenter)
    RadioButton rbMcenter;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.rb_my)
    RadioButton rbMy;
    private PagerAdapter viewPagerAdapter;
    private InstallAppDialog installAppDialog;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private String mComment;

    @Override
    protected void initTitle() {
        //find apk position
        String path = getApplicationContext().getPackageResourcePath();
        System.out.println("path===" + path);

        //red apk Comment
        try {
            File file = new File(path);
            RandomAccessFile apkFile = new RandomAccessFile(file, "r");
            ZipStream zipStream = new ZipStream(apkFile);
            mComment = zipStream.getComment();

            System.out.println("zip Comment = " + mComment);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if (!mComment.equals("")) {
            ToastUtil.showToast(mComment);
            Log.e("ZIP注释内容", mComment);
//            TextView textView = (TextView)findViewById(R.id.textView);
//            textView.setText("ZIP注释内容：" + mComment);
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        viewPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        vpContent.setAdapter(viewPagerAdapter);
        vpContent.setOffscreenPageLimit(2);
        vpContent.setOnPageChangeListener(onPagerChangerListener);
        rgGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        rbHome.setChecked(true);

    }


    private static final int REQUECT_CODE_SDCARD = 1;
    private static final int INSTALL_PERMISS_CODE = 2;

    @Override
    protected void initDatas() {
        startService(new Intent(this, KeepLifeService.class));
        MPermissions.requestPermissions(this, REQUECT_CODE_SDCARD, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE});

    }

    /**
     * 8.0以上系统设置安装未知来源权限
     */
    public void setInstallPermission() {
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先判断是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {

                installAppDialog = new InstallAppDialog(MainActivity.this, R.style.MyDialog, onClickListener);
                installAppDialog.show();

                return;
            }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_close:
                    toInstallPermissionSettingIntent();
                    break;
            }
        }
    };

    /**
     * 开启安装未知来源权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void toInstallPermissionSettingIntent() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == INSTALL_PERMISS_CODE) {
            ToastUtil.showToast("已授予APP安装未知应用权限");
            if (installAppDialog != null) {
                installAppDialog.dismiss();
            }
        } else {
            ToastUtil.showToast("请授予APP安装未知应用权限");
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess() {
        setInstallPermission();
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed() {
        ToastUtil.showToast("请设置App运行所需权限!");
        finish();
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            setTabSelection(checkedId);
        }
    };

    ViewPager.OnPageChangeListener onPagerChangerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    rbHome.setChecked(true);
                    break;
                case 1:
                    rbMy.setChecked(true);
                    break;
                case 2:
                    rbMcenter.setChecked(true);
                    break;
//                case 3:
//                    rbMcenter.setChecked(true);
//                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setTabSelection(int checkedId) {

        switch (checkedId) {
            case R.id.rb_home:
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.rb_my:
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.rb_mcenter:
                vpContent.setCurrentItem(2, false);
                break;
        }
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToast("再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);       不走MainActivityDestory方法，所以注销
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDestroy() {
        Log.e("MainActivityDestory执行","~");
        List<GameInfo> downLoadingGames = DownLoadManger.getInstance().getOnlyDownLoadingGames();
        if (downLoadingGames!=null&&downLoadingGames.size()>0){
            for (int i = 0; i < downLoadingGames.size(); i++) {
                DownLoadManger.getInstance().pause(downLoadingGames.get(i));
            }
        }
        super.onDestroy();
    }

}
