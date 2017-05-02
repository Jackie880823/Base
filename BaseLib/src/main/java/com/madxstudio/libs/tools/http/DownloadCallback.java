/*
 *
 *             $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 *             $                                                   $
 *             $                       _oo0oo_                     $
 *             $                      o8888888o                    $
 *             $                      88" . "88                    $
 *             $                      (| -_- |)                    $
 *             $                      0\  =  /0                    $
 *             $                    ___/`-_-'\___                  $
 *             $                  .' \\|     |$ '.                 $
 *             $                 / \\|||  :  |||$ \                $
 *             $                / _||||| -:- |||||- \              $
 *             $               |   | \\\  -  $/ |   |              $
 *             $               | \_|  ''\- -/''  |_/ |             $
 *             $               \  .-\__  '-'  ___/-. /             $
 *             $             ___'. .'  /-_._-\  `. .'___           $
 *             $          ."" '<  `.___\_<|>_/___.' >' "".         $
 *             $         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       $
 *             $         \  \ `_.   \_ __\ /__ _/   .-` /  /       $
 *             $     =====`-.____`.___ \_____/___.-`___.-'=====    $
 *             $                       `=-_-='                     $
 *             $     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   $
 *             $                                                   $
 *             $          Buddha Bless         Never Bug           $
 *             $                                                   $
 *             $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
 *
 *  Copyright (C) 2017 The Mad x Studio's Android Project by Jackie
 */

package com.madxstudio.libs.tools.http;

import android.text.TextUtils;

import com.madxstudio.libs.SDCardException;
import com.madxstudio.libs.tools.DeviceUtils;

import java.io.File;

/**
 * 文件下载的回调，可以指定下载的目录和文件名，回调下载进度
 * Created 17/2/15.
 *
 * @author Jackie
 * @version 1.0
 */

public abstract class DownloadCallback extends Callback<File> {
    /**
     * 下载存放的文件目录
     */
    private String destFileDir;
    /**
     * 下载存放的文件名
     */
    private String destFileName;

    public DownloadCallback() throws SDCardException {
        this(null);
    }

    public DownloadCallback(String destFileName) throws SDCardException {
        this(DeviceUtils.getSavePath(), destFileName);
    }

    public DownloadCallback(String destFileDir, String destFileName) throws SDCardException {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;

        if (TextUtils.isEmpty(destFileDir)) {
            throw new SDCardException("save path is null");
        }

        File file = new File(destFileDir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public String getDestFileName() {
        return destFileName;
    }
}
