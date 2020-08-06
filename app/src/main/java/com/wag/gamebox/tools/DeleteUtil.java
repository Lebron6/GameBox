package com.wag.gamebox.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xpf on 2017/12/25 :)
 * Function:
 */
public class DeleteUtil {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    /**
     * 删除指定目录指定前后缀的文件
     *
     * @param dirPath
     * @param isPrefix
     * @param regEx
     */
    public static void delete(String dirPath, boolean isPrefix, String regEx) {
        executor.execute(new DeleteRunnable(dirPath, isPrefix, regEx));
    }
}