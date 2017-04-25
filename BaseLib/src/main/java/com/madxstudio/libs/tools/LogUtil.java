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

package com.madxstudio.libs.tools;

import android.util.Log;

import com.madxstudio.libs.BuildConfig;

/**
 * 用于本应用的信息输出，所以级别的{@link Log}信息都只有使用这个工具类输出。目的在统一控制{@link Log}能够输出的信息级别
 *
 * @see Log
 *
 * Created 17/2/14.
 *
 * @author Jackie
 * @version 1.0
 */
public class LogUtil {

    /**
     * @see Log#v(String, String)
     */
    public static void v(String tag, String msg){
        if (BuildConfig.DEBUG_LEVEL <= Log.VERBOSE) {
            Log.v(tag, msg);
        }
    }

    /**
     * @see Log#v(String, String, Throwable)
     */
    public static void v(String tag, String msg, Throwable tr){
        if (BuildConfig.DEBUG_LEVEL <= Log.VERBOSE) {
            Log.v(tag, msg, tr);
        }
    }

    /**
     * @see Log#d(String, String)
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG_LEVEL <= Log.DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * @see Log#d(String, String, Throwable)
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG_LEVEL <= Log.DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * @see Log#i(String, String)
     */
    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG_LEVEL <= Log.INFO) {
            Log.i(tag, msg);
        }
    }

    /**
     * @see Log#i(String, String, Throwable)
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG_LEVEL <= Log.INFO) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * @see Log#w(String, String)
     */
    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG_LEVEL <= Log.WARN) {
            Log.w(tag, msg);
        }
    }

    /**
     * @see Log#w(String, String)
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG_LEVEL <= Log.WARN) {
            Log.w(tag, msg, tr);
        }
    }

    /**
     * @see Log#e(String, String)
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG_LEVEL <= Log.ERROR) {
            Log.e(tag, msg);
        }
    }

    /**
     * @see Log#e(String, String, Throwable)
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG_LEVEL <= Log.ERROR) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * @see Log#wtf(String, String)
     */
    public static void wtf(String tag, String msg) {
        if (BuildConfig.DEBUG_LEVEL <= Log.VERBOSE) {
            Log.wtf(tag, msg);
        }
    }

    /**
     * @see Log#wtf(String, String, Throwable)
     */
    public static void wtf(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG_LEVEL <= Log.VERBOSE) {
            Log.wtf(tag, msg, tr);
        }
    }

}
