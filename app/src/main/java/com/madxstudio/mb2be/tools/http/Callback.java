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

package com.madxstudio.mb2be.tools.http;

import android.support.annotation.Nullable;

/**
 * 用于UI层使用的网络连接回调类，不直接使用第三方库的网络连接回调目的：可以对网络框架的封装，以便以后更换网络框架而不会
 * 影响上层代码，使其与应用代码分离
 *
 * Created 17/2/15.
 *
 * @author Jackie
 * @version 1.0
 */
public abstract class Callback<T> {

    /**
     * 开始连接
     */
    void onConnect() {

    }

    /**
     * 连接失败
     *
     * @param msg 失败返回的信息
     * @param e   异常信息
     */
    void onFailure(@Nullable String msg, @Nullable Exception e) {

    }

    /**
     * 服务器成功并的响应
     *
     * @param response 响应信息
     */
    void onResponse(T t, @Nullable String response) {

    }

    void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }
}
