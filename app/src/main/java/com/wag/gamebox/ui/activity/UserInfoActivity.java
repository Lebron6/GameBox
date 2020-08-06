package com.wag.gamebox.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wag.gamebox.BuildConfig;
import com.wag.gamebox.R;
import com.wag.gamebox.api.BaseUrl;
import com.wag.gamebox.api.Constant;
import com.wag.gamebox.api.HttpRequestUtils;
import com.wag.gamebox.entity.MyPage;
import com.wag.gamebox.entity.UpLoadIcon;
import com.wag.gamebox.tools.AppManager;
import com.wag.gamebox.tools.FileUtil;
import com.wag.gamebox.tools.PreferenceUtils;
import com.wag.gamebox.tools.ToastUtil;
import com.wag.gamebox.ui.dialog.UpLoadPop;
import com.wag.gamebox.ui.view.TitleBarManger;
import com.tencent.tauth.Tencent;

import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2018/10/10.
 */
public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.layout_updata_password)
    RelativeLayout layoutUpdataPassword;
    @BindView(R.id.tv_phone_bind_status)
    TextView tvPhoneBindStatus;
    @BindView(R.id.layout_bind_phone)
    RelativeLayout layoutBindPhone;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.tv_bing_phone_num)
    TextView tvBingPhoneNum;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.layout_back)
    LinearLayout layoutBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_sreach)
    ImageView ivSreach;
    @BindView(R.id.tv_name_auth_status)
    TextView tvNameAuthStatus;
    @BindView(R.id.layout_name_auth)
    RelativeLayout layoutNameAuth;
    @BindView(R.id.tv_login_out)
    TextView tvLoginOut;
    private UpLoadPop upLoadPop;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleBarManger manger = TitleBarManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("个人信息");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initViews() {

        if (PreferenceUtils.getInstance().getLoginType() == 1) {//手机号
            layoutBindPhone.setVisibility(View.GONE);
            layoutUpdataPassword.setVisibility(View.VISIBLE);
        } else {
            layoutBindPhone.setVisibility(View.VISIBLE);
            layoutUpdataPassword.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initDatas() {
        if (!PreferenceUtils.getInstance().getLoginStatus()) {
            return;
        } else {
            RequestParams params = new RequestParams(BaseUrl.getInstence().getAccountInfoUrl());
            params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
            params.addParameter("type", PreferenceUtils.getInstance().getLoginType());
            HttpRequestUtils requestUtils = new HttpRequestUtils(mHandler);
            requestUtils.doGet(params);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("获取个人信息数据", msg.obj.toString());
                    try {
                        MyPage myPage = new Gson().fromJson(msg.obj.toString(), MyPage.class);
                        if (myPage.getData() != null) {
                            ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true).setIgnoreGif(false).build();
                            x.image().bind(ivUserIcon, myPage.getData().getHead_img(), imageOptions);
                                if (myPage.getData().getAuth() == 1) {
                                    tvNameAuthStatus.setText("已认证");
                                } else {
                                    tvNameAuthStatus.setText("未认证");
                                }
                            if (PreferenceUtils.getInstance().getLoginType() == 1) {//手机号
                                layoutBindPhone.setVisibility(View.GONE);
                                tvUserNickname.setText(myPage.getData().getUser_login());
                            } else {
                                tvUserNickname.setText(myPage.getData().getUser_name());
                                layoutBindPhone.setVisibility(View.VISIBLE);
                                if (myPage.getData().getBinding() == 0) {
                                    tvPhoneBindStatus.setText("未绑定");
                                } else {
                                    tvBingPhoneNum.setText(myPage.getData().getUser_login());
                                    tvPhoneBindStatus.setText("已绑定");
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("解析个人信息数据失败", msg.obj.toString());
                        finish();
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("获取个人信息失败");
                    Log.e("获取个人信息失败", msg.obj.toString());
                    finish();
                    break;
            }
        }
    };

    @OnClick({R.id.layout_updata_nickName,R.id.tv_login_out, R.id.iv_user_icon, R.id.layout_updata_password, R.id.layout_bind_phone,R.id.layout_name_auth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                upLoadPop = new UpLoadPop(UserInfoActivity.this, onClickListener);
                upLoadPop.showAtLocation(viewBottom, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.layout_updata_password:
                UpdataPasswordActivity.actionStart(this);
                break;
            case R.id.layout_updata_nickName:
                UpdataNickNameActivity.actionStart(this);
                break;
            case R.id.layout_name_auth:
                NameAuthActivity.actionStart(this);
                break;
            case R.id.layout_bind_phone:
                BindPhoneActivity.actionStart(this);
                break;
            case R.id.tv_login_out:
                Tencent mTencent = Tencent.createInstance(Constant.QQ_APP_ID, this);
                mTencent.logout(UserInfoActivity.this);
                loginOut();
                break;

        }
    }

    private void loginOut() {
        PreferenceUtils.getInstance().loginOut();
        AppManager.getAppManager().AppExit(UserInfoActivity.this);
        startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
    }

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_chose_pic:
                    // 3、调用从图库选取图片方法
                    //权限判断
                    if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请READ_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                READ_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {
                        //跳转到相册
                        gotoPhoto();
                    }
                    upLoadPop.dismiss();
                    break;
                case R.id.btn_take_photo:
//权限判断
                    if (ContextCompat.checkSelfPermission(UserInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(UserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {
                        //跳转到调用系统相机
                        gotoCamera();
                    }
                    upLoadPop.dismiss();
                    break;
            }
        }
    };

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Log.e("参数", BuildConfig.APPLICATION_ID + ".fileProvider");
            Uri contentUri = FileProvider.getUriForFile(UserInfoActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    private File tempFile;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    final String cropImagePath = FileUtil.getRealFilePathFromUri(UserInfoActivity.this, uri);
                    upLoadHeadimg(cropImagePath);
                }
                break;
        }
    }

    private void upLoadHeadimg(String cropImagePath) {
        RequestParams params = new RequestParams(BaseUrl.getInstence().getUploadAvatarUrl());
        params.addHeader("token", PreferenceUtils.getInstance().getUserToken());
        params.addBodyParameter("file", new File(cropImagePath));
        params.setMultipart(true);      //设置表单上传非常重要
        HttpRequestUtils requestUtils = new HttpRequestUtils(sHandler);
        requestUtils.doPost(params);
    }

    private Handler sHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.REQUEST_SUCCESS:
                    Log.e("上传成功", msg.obj.toString());
                    UpLoadIcon upLoadIcon = new Gson().fromJson(msg.obj.toString(), UpLoadIcon.class);
                    if (upLoadIcon.getCode().equals("200")) {
                        ToastUtil.showToast("上传成功");
                        ImageOptions imageOptions = (new ImageOptions.Builder()).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setCircular(true).setCrop(true).setIgnoreGif(false).build();
                        x.image().bind(ivUserIcon, upLoadIcon.getData(), imageOptions);
                    } else {
                        ToastUtil.showToast("上传失败");
                    }
                    break;
                case Constant.REQUEST_FAIL:
                    ToastUtil.showToast("上传头像失败");
                    Log.e("上传头像失败", msg.obj.toString());
                    break;
            }
        }
    };

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(UserInfoActivity.this, ClipImageActivity.class);
        intent.putExtra("type", 1);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
