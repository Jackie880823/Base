/*
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
 *  Copyright (C) 2017 The Jackie's Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.madxstudio.mb2be.tools.http;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.madxstudio.mb2be.tools.LogUtil;

/**
 * 用于UI层使用的网络连接回调类，不直接使用第三方库的网络连接回调目的：可以对网络框架的封装，以便以后更换网络框架而不会
 * 影响上层代码，使其与应用代码分离
 * <p>
 * Created 17/2/15.
 *
 * @author Jackie
 * @version 1.0
 */
public abstract class Callback<T> {
    private String TAG = "Callback";
    private Context mContext;

    public Callback() {
    }

    /**
     * 开始连接
     */
    protected void onConnect() {
        LogUtil.d(TAG, "onConnect: ");
    }

    /**
     * 连接失败
     *
     * @param msg 失败返回的信息
     * @param e   异常信息
     */
    protected void onFailure(@Nullable String msg, @Nullable Exception e) {
        LogUtil.e(TAG, "onFailure: msg = [" + msg + "]", e);
        if (mContext != null) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 服务器成功并的响应
     *
     * @param response 响应信息
     */
    protected void onResponse(T t, @Nullable String response) {
        Log.d(TAG, "onResponse() called with: t = [" + t + "], response = [" + response + "]");
    }

    protected void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        LogUtil.d(TAG, "onProgress() called with: " + "currentSize = [" + currentSize + "], " +
                "totalSize = [" + totalSize + "], progress = [" + progress + "], networkSpeed = "
                + "[" + networkSpeed + "]");
    }

    protected void onFinish(T t, Exception e) {
        LogUtil.d(TAG, "onFinish() called with: " + "t = [" + t + "], e = [" + e + "]");
    }

    public void setTAG(String TAG) {
        if (!TextUtils.isEmpty(TAG)) {
            this.TAG = TAG;
        }
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getTAG() {
        return TAG;
    }
}
