package com.wag.gamebox.callback;

import com.wag.gamebox.tools.DownLoadManger;

import com.wag.gamebox.entity.GameInfo;

public interface DownloadObserver {
    /**
     * 当状态改变时的回调
     *
     * @param manager
     * @param info
     */
    void onDownloadStateChanged(DownLoadManger manager, GameInfo info);
}
