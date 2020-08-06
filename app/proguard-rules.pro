-optimizationpasses 5          # 指定代码的压缩级别
    -dontusemixedcaseclassnames   # 是否使用大小写混合
    -dontpreverify                 # 混淆时是否做预校验
    -verbose                        # 混淆时是否记录日志

    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

    -keep public class * extends android.app.Activity      # 所有activity的子类不要去混淆  
    -keep public class * extends android.app.Application   # 保持哪些类不被混淆
    -keep public class * extends android.app.Service       # 保持哪些类不被混淆
    -keep public class * extends android.app.widget.RelativeLayout       # 保持哪些类不被混淆
    -keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
    -keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
    -keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
    -keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
    -keep public class * extends android.widget.BaseAdapter

    -keepattributes Signature,*Annotation*
    -keep public class org.xutils.** {
        public protected *;
    }
    -keep public interface org.xutils.** {
        public protected *;
    }
    -keepclassmembers class * extends org.xutils.** {
        public protected *;
    }
    -keepclassmembers @org.xutils.db.annotation.* class * {*;}
    -keepclassmembers @org.xutils.http.annotation.* class * {*;}
    -keepclassmembers class * {
        @org.xutils.view.annotation.Event <methods>;
    }


#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

-keepattributes Exceptions,InnerClasses

-keepattributes Signature

# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
 -keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
        native <methods>;
    }
    -keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
        public <init>(android.content.Context, android.util.AttributeSet);
    }
    -keepclasseswithmembers class * {# 保持自定义控件类不被混淆
        public <init>(android.content.Context, android.util.AttributeSet, int);
    }
    -keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
        public void *(android.view.View);
    }
    -keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
        public static **[] values();
        public static ** valueOf(java.lang.String);
    }

    -keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
        public static final android.os.Parcelable$Creator *;
    }

    -keepclassmembers public class * extends android.view.View {
        void set*(***);
        *** get*();
    }

    -keepclassmembers class * extends android.app.Activity {
        public void *(android.view.View);
    }
##-------------------------------------------------------------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

##---------------End: proguard configuration for Gson  ----------

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}



# Permission
-dontwarn com.zhy.m.**
-keep class com.zhy.m.** {*;}
-keep interface com.zhy.m.** { *; }
-keep class **$$PermissionProxy { *; }

# nineoldandroids
-keep interface com.nineoldandroids.view.** { *; }
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

# Retrofit
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

# okhttp
-dontwarn okio.**

# Gson
-keep class com.wag.gamebox.entity.**{*;} # 自定义数据模型的bean目录

#Alipay
 -dontwarn com.alipay.**
    -keep class com.alipay.android.app.IAlixPay{*;}
    -keep class com.alipay.android.app.IAlixPay$Stub{*;}
    -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
    -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
    -keep class com.alipay.sdk.app.PayTask{ public *;}
    -keep class com.alipay.sdk.app.AuthTask{ public *;}
    -keep class http.**{ public *;}

#微信支付
   -keep class com.tencent.mm.opensdk.** {*;}
   -keep class com.tencent.wxop.** {*;}
   -keep class com.tencent.mm.sdk.** {*;}

#微博支付
   -dontwarn com.weibo.sdk.Android.WeiboDialog
   -dontwarn android.NET.http.SslError
   -dontwarn android.webkit.WebViewClient
   -keep public class android.Net.http.SslError{
   *;
   }
   -keep public class android.webkit.WebViewClient{
   *;
   }
   -keep public class android.webkit.WebChromeClient{
   *;
   }
   -keep public interface android.webkit.WebChromeClient$CustomViewCallback {
   *;
   }
   -keep public interface android.webkit.ValueCallback {
   *;
   }
   -keep class * implements android.webkit.WebChromeClient {
   *;
   }

   -keep class com.sina.weibo.sdk.api.** {
   *;
   }

   -keep class com.sina.weibo.sdk.** {
   *;
   }

-keep class * extends android.app.Dialog