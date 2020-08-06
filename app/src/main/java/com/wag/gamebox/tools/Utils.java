package com.wag.gamebox.tools;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.wag.gamebox.BuildConfig;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wag.gamebox.entity.GameInfo;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by LeBron on 2017/2/6.
 */

public class Utils {

    private static double b = 0;
    private static double c;
    private static double d;
    private static Bitmap bitmap;

    /**
     * 使状态栏透明  根据本版好判断当前Activity是否可设置为状态栏透明，如果ContentView 是一个图片，可直接调用此方法；
     * 如果其它布局直接使用这个方法，顶部titlebar会顶到状态栏，需要xml跟布局是线性布局，顶部动态设置View高度与当前机型状态栏高度一致
     */
    public static boolean setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            return true;
        } else {
            return false;
        }
    }
    public static boolean isQQInstall(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                //通过遍历应用所有包名进行判断
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean cardCodeVerify(String cardcode) {
             int i = 0;
             String r = "error";
             String lastnumber = "";

             i += Integer.parseInt(cardcode.substring(0, 1)) * 7;
             i += Integer.parseInt(cardcode.substring(1, 2)) * 9;
             i += Integer.parseInt(cardcode.substring(2, 3)) * 10;
             i += Integer.parseInt(cardcode.substring(3, 4)) * 5;
             i += Integer.parseInt(cardcode.substring(4, 5)) * 8;
             i += Integer.parseInt(cardcode.substring(5, 6)) * 4;
             i += Integer.parseInt(cardcode.substring(6, 7)) * 2;
             i += Integer.parseInt(cardcode.substring(7, 8)) * 1;
             i += Integer.parseInt(cardcode.substring(8, 9)) * 6;
             i += Integer.parseInt(cardcode.substring(9, 10)) * 3;
             i += Integer.parseInt(cardcode.substring(10,11)) * 7;
             i += Integer.parseInt(cardcode.substring(11,12)) * 9;
             i += Integer.parseInt(cardcode.substring(12,13)) * 10;
             i += Integer.parseInt(cardcode.substring(13,14)) * 5;
             i += Integer.parseInt(cardcode.substring(14,15)) * 8;
             i += Integer.parseInt(cardcode.substring(15,16)) * 4;
             i += Integer.parseInt(cardcode.substring(16,17)) * 2;
             i = i % 11;
             lastnumber =cardcode.substring(17,18);
             if (i == 0) {
                     r = "1";
                 }
             if (i == 1) {
                     r = "0";
                 }
             if (i == 2) {
                     r = "x";
                 }
             if (i == 3) {
                     r = "9";
                 }
             if (i == 4) {
                     r = "8";
                 }
             if (i == 5) {
                     r = "7";
                 }
             if (i == 6) {
                     r = "6";
                 }
             if (i == 7) {
                     r = "5";
                 }
             if (i == 8) {
                     r = "4";
                 }
             if (i == 9) {
                     r = "3";
                 }
             if (i == 10) {
                     r = "2";
                 }
             if (r.equals(lastnumber.toLowerCase())) {
                     return true;
                 }
             return false;
         }

    /**
     * 下载速度
     *
     * @param a
     * @return
     */
    public static String getSpent(double a) {
        DecimalFormat df = new DecimalFormat("######0.00");
        c = a - b;
        d = c / 1024;

        b = a;
        if (d > 1024) {
            double v = d / 1024;
            String format = df.format(v);
            return format + "MB/s";
        }
        String format = df.format(d);
        return format + "KB/s";
    }


    /**
     * 获取本地版本号
     * @param ctx
     * @return
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
    /**
     * 获取本地版本名
     * @param ctx
     * @return
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 安装应用程序
     *
     * @param context
     * @param apkFile
     */
    public static void installApp(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
//判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);

    }

    /**
     * 根据路径获得应用包名
     * @param context
     * @param path
     * @return
     */
    public static String getPackageName(Context context, String path) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo pm = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (pm != null) {
            ApplicationInfo Info = pm.applicationInfo;
            return Info.packageName;
        } else {
            return "";
        }
    }


    /**
     * 判断包是否安装
     * @param context
     * @param
     * @return
     */

    public static boolean isInstall(Context context, String pageName) {
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo(pageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean copy(String text) {
        if (!TextUtils.isEmpty(text)) {
            ClipboardManager cmb = (ClipboardManager) x.app().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(text);
            ToastUtil.showToast("已成功复制到粘贴板");
            return true;
        } else {
            ToastUtil.showToast("复制失败");
            return false;
        }
    }

    /**
     * 打开应用程序
     *
     * @param context
     * @param
     */
    public static void openApp(Context context, GameInfo path) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(path.getPackage_name());
        context.startActivity(intent);
    }

    /**
     * dip 转 px
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {

        DisplayMetrics metrics = x.app().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }

    public static int dipToPx(Context paramContext, float paramFloat) {
        return (int) (0.5f + (paramContext.getResources().getDisplayMetrics().density * paramFloat));
    }

    public static int px2dip(int px) {
        // dp = px / denisity
        DisplayMetrics metrics = x.app().getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 判断是否是手机号码：此方法已过时
     */
    public static boolean isMobileNO(String mobiles) {
        if(mobiles.length()!=11){
            return false;
        }
        return true;
//        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
    }

}
