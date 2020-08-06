package com.wag.gamebox.entity;

import android.text.TextUtils;

public class PayResult {
    private String o;
    private String p;
    private String q;

    public PayResult(String var1) {
        if (!TextUtils.isEmpty(var1)) {
            String[] var10000 = var1.split(";");
            var1 = null;
            String[] var4 = var10000;
            int var3 = var10000.length;

            for(int var2 = 0; var2 < var3; ++var2) {
                if ((var1 = var4[var2]).startsWith("resultStatus")) {
                    this.o = a(var1, "resultStatus");
                }

                if (var1.startsWith("result")) {
                    this.p = a(var1, "result");
                }

                if (var1.startsWith("memo")) {
                    this.q = a(var1, "memo");
                }
            }

        }
    }

    public String toString() {
        return "resultStatus={" + this.o + "};memo={" + this.q + "};result={" + this.p + "}";
    }

    private static String a(String var0, String var1) {
        var1 = var1 + "={";
        return var0.substring(var0.indexOf(var1) + var1.length(), var0.lastIndexOf("}"));
    }

    public String getResultStatus() {
        return this.o;
    }

    public String getMemo() {
        return this.q;
    }

    public String getResult() {
        return this.p;
    }
}
