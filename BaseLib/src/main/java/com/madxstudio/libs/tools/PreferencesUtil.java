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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.madxstudio.libs.BaseApp;

import java.util.Set;

/**
 * SharedPreferences的封装工具
 */
public class PreferencesUtil {

    public static final String USER = "user";

    private static Editor edit;
    private static PreferencesUtil preferencesUtil;
    private static SharedPreferences sharedPreferences;

    private PreferencesUtil(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.PREFERENCE_NAME, Context
                .MODE_PRIVATE);
        this.edit = sharedPreferences.edit();
    }

    private static void init() {
        if (preferencesUtil == null) {
            preferencesUtil = new PreferencesUtil(BaseApp.getInstance());
        }
    }

    /**
     * 清除所有的配置
     */
    public static void clear() {
        edit.clear().apply();
    }

    /**
     * 保存SharedPreferences中的整型值
     *
     * @param key   - 保存的数据{@code key}
     * @param value - 保存的数据{@code value}
     */
    public static void saveValue(String key, int value) {
        init();
        edit.putInt(key, value).apply();
    }

    /**
     * 保存SharedPreferences中的{@link Long}
     *
     * @param key   - 保存数据的{@code key}
     * @param value - 保存的数据{@code value}
     */
    public static void saveValue(String key, long value) {
        init();
        edit.putLong(key, value).apply();
    }

    /**
     * 保存SharedPreferences中的float
     *
     * @param key   - 保存数据的{@code key}
     * @param value - 保存的数据{@code value}
     * @return
     */
    public static void saveValue(String key, float value) {
        init();
        edit.putFloat(key, value).apply();
    }

    /**
     * 保存SharedPreferences中的String
     *
     * @param key   - 保存数据的{@code key}
     * @param value - 保存的数据{@code value}
     * @return
     */
    public static void saveValue(String key, String value) {
        init();
        edit.putString(key, value).apply();
    }

    /**
     * 保存SharedPreferences中的{@link Set<String>}
     *
     * @param key   - 保存数据的{@code key}
     * @param value - 保存的数据{@code value}
     * @return
     */
    public static void saveValue(String key, Set<String> value) {
        init();
        edit.putStringSet(key, value).apply();
    }

    /**
     * 保存SharedPreferences中的boolean
     *
     * @param key   - 保存数据的{@code key}
     * @param value - 保存的数据{@code value}
     * @return
     */
    public static void saveValue(String key, boolean value) {
        init();
        edit.putBoolean(key, value).apply();
    }

    /** TODO 如下定义各种默认SharedPreferences获取数据函数 */

    /**
     * 获取SharedPreferences中的整型值
     *
     * @param key     - 需要获取的数据的{@code key}
     * @param defaule - 缺省值
     * @return
     */
    public static int getValue(String key, int defaule) {
        init();
        return sharedPreferences.getInt(key, defaule);
    }

    /**
     * 获取SharedPreferences中的long
     *
     * @param key     - 需要获取的数据的{@code key}
     * @param defaule - 缺省值
     * @return
     */
    public static long getValue(String key, long defaule) {
        init();
        return sharedPreferences.getLong(key, defaule);
    }

    /**
     * 获取SharedPreferences中的float
     *
     * @param key     - 需要获取的数据的{@code key}
     * @param defaule - 缺省值
     * @return
     */
    public static float getValue(String key, float defaule) {
        init();
        return sharedPreferences.getFloat(key, defaule);
    }

    /**
     * 获取SharedPreferences中的String
     *
     * @param key     - 需要获取的数据的{@code key}
     * @param defaule - 缺省值
     * @return
     */
    public static String getValue(String key, String defaule) {
        init();
        return sharedPreferences.getString(key, defaule);
    }

    /**
     * 获取SharedPreferences中的String
     *
     * @param key     - 需要获取的数据的{@code key}
     * @param defaule - 缺省值
     * @return
     */
    public static Set<String> getValue(String key, Set<String> defaule) {
        init();
        return sharedPreferences.getStringSet(key, defaule);
    }

    /**
     * 获取SharedPreferences中的boolean
     *
     * @param key     - 需要获取的数据的{@code key}
     * @param defaule - 缺省值
     * @return
     */
    public static boolean getValue(String key, boolean defaule) {
        init();
        return sharedPreferences.getBoolean(key, defaule);
    }
}
