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

package com.madxstudio.mb2be;

import android.support.multidex.MultiDexApplication;

import com.madxstudio.mb2be.base.BaseActivity;
import com.madxstudio.mb2be.tools.http.HttpUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created 17/2/13.
 *
 * @author Jackie
 * @version 1.0
 */
public class BaseApp extends MultiDexApplication {
    private static BaseApp instance;

    private static Set<WeakReference<BaseActivity>> activities = new HashSet<>();

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化网络框架
        HttpUtil.init(new HashMap<String, String>());
    }

    public static void addActivity(BaseActivity activity) {
        activities.add(new WeakReference<>(activity));
    }

    public static void removeActivity(BaseActivity activity) {
        activities.remove(new WeakReference<>(activity));
    }

    public static void clearActivities() {
        for (WeakReference<BaseActivity> reference : activities) {
            BaseActivity activity = reference.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
