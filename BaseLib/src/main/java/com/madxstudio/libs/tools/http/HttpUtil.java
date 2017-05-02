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

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.PutRequest;
import com.madxstudio.libs.BaseApp;
import com.madxstudio.libs.tools.DeviceUtils;
import com.madxstudio.libs.tools.NetworkUtils;
import com.madxstudio.libs.tools.PreferencesUtil;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created 17/2/15.
 *
 * @author Jackie
 * @version 1.0
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";

    public static void init() {
        // 定义header,不支持中文
        // TODO: 17/2/15 配置全局所需的header信息
        HttpHeaders headers = new HttpHeaders();
        try {
            // X_MB2BE_DEVID:anonymous 手机的 UUID
            headers.put("X_MB2BE_DEVID", String.valueOf(DeviceUtils.getUUID()));
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        Locale locale = Locale.getDefault();
        // X_MB2BE_USERLOC:KualaLumpur/Malaysia 手机的地区
        headers.put("X_MB2BE_USERLOC", locale.getISO3Country());
        // X_MB2BE_APPLANG:en-MY 手机的言语
        headers.put("X_MB2BE_APPLANG", locale.getLanguage());
        // X_MB2BE_TIMEZONE:Asia/Kuala_Lumpur 手机的时区
        headers.put("X_MB2BE_TIMEZONE", TimeZone.getDefault().getID());

        // X_MB2BE_IDENTIFIER: client
        headers.put("X_MB2BE_IDENTIFIER", "client");
        headers.put("X_MB2BE_APPVER", "1.0.0");

        // X_MB2BE_PAIRID : com.mb2be
        headers.put("X_MB2BE_PAIRID", "com.mb2be");
        // X_MB2BE_APPID: com.madxstudio.libs 应用的 id，暂时写死这个名，以后会需要改，因为名字还不确定
        headers.put("X_MB2BE_APPID", "com.madxstudio.libs");
        // X_MB2BE_USERLOGINID:kengwai@gmail.com 用户的账号
        headers.put("X_MB2BE_USERLOGINID", "kengwai@gmail.com");

        // 定义param，支持中文，直接传不要自己编码
        // TODO: 17/2/15 配置全局所需的参数
        HttpParams params = new HttpParams();

        // 初始化OkGo
        OkGo.init(BaseApp.getInstance());

        OkGo.getInstance()
                // 打开该调试开关
                .debug(TAG)
                // 如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间;

                // 设置全局缓存模式: 按照HTTP协议的默认缓存规则，例如有304响应头时缓存
                .setCacheMode(CacheMode.DEFAULT)

                // 可以全局统一设置缓存时间,默认永不过期
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                // 可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                .setRetryCount(4)

                //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                // .setCookieStore(new MemoryCookieStore()) //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore()) //cookie持久化存储，如果cookie不过期，则一直有效

                // 可以设置https的证书,以下几种方案根据需要自己设置
                // .setCertificates() //方法一：信任所有证书,不安全有风险
                // .setCertificates(new SafeTrustManager()) //方法二：自定义信任规则，校验服务端证书
                // .setCertificates(getAssets().open("srca.cer")) //方法三：使用预埋证书，校验服务端证书（自签名证书）

                // 方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                // .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy
                // .cer"))
                .addCommonHeaders(headers).addCommonParams(params);
    }

    public static void addUserHeader(@NonNull String key, @NonNull String value) {
        OkGo.getInstance().addCommonHeaders(new HttpHeaders(key, value));
    }

    public static void addUserParam(@NonNull String key, @NonNull String value) {
        OkGo.getInstance().addCommonParams(new HttpParams(key, value));
    }

    /**
     * 从服务器中获取数据
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void get(Context context, Object tag, @NonNull String url, Map<String, String>
            params, @Nullable final Callback<String> callback) {

        if (callback != null) {
            if (tag instanceof String) {
                callback.setTAG((String) tag);
            }
            callback.setContext(context);
        }

        BaseRequest request = OkGo.get(url);

        request.tag(tag).params(params, true).execute(HttpCallBackBuild.buildStringCallback
                (callback));

        if (!NetworkUtils.isConnected(context)) {
            String s = new Gson().toJson(params);
            String value = PreferencesUtil.getValue(request.getRequest().url().toString(), "");
            if (!value.isEmpty()) {
                callback.onResponse(value, value);
                callback.onFinish(s, new NetworkErrorException("not connect"));
            }
        }
    }

    /**
     * 获取网络图片
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void getBitmap(Context context, Object tag, @NonNull String url, @Nullable
            Map<String, String> params, @Nullable final Callback<Bitmap> callback) {
        GetRequest request = OkGo.get(url);

        if (callback != null) {
            if (tag instanceof String) {
                callback.setTAG((String) tag);
            }
            callback.setContext(context);
        }

        request
                .tag(tag)
                .params(params)
                .execute(HttpCallBackBuild.buildBitmapCallback(callback));
    }

    /**
     * 下载文件
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void download(Context context, Object tag, @NonNull String url, Map<String,
            String> params, @Nullable DownloadCallback callback) {
        GetRequest request = OkGo.get(url);

        if (callback != null) {
            if (tag instanceof String) {
                callback.setTAG((String) tag);
            }
            callback.setContext(context);
        }

        request
                .tag(tag)
                .params(params, true)
                .execute(HttpCallBackBuild.buildFileCallback(callback));
    }

    /**
     * @see #post(Context, Object, String, String, Map, Callback)
     */
    @Deprecated
    public static void post(Object tag, @NonNull String url, @NonNull String json, @NonNull
            Map<String, String> params, @Nullable final Callback<String> callback) {
        post(null, tag, url, json, params, callback);
    }

    /**
     * 上传参数
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param json     - post的数据，不能为空
     * @param params   - 需要的{@code key-value}参数， 不可以为空
     * @param callback - 回调
     */
    public static void post(Context context, Object tag, @NonNull String url, @NonNull String
            json, @NonNull Map<String, String> params, @Nullable final Callback<String> callback) {
        PostRequest request = OkGo.post(url);

        if (callback != null) {
            if (tag instanceof String) {
                callback.setTAG((String) tag);
            }
            callback.setContext(context);
        }

        request
                .tag(tag)
                .upJson(json)
                .params(params)
                .execute(HttpCallBackBuild
                .buildStringCallback(callback));
    }

    /**
     * @see #put(Context, Object, String, String, Map, Callback)
     */
    @Deprecated
    public static void put(Object tag, @NonNull String url, @NonNull String json, @NonNull
            Map<String, String> params, @Nullable final Callback<String> callback) {
        put(null, tag, url, json, params, callback);
    }

    /**
     * put参数
     *
     * @param context  - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数， 不可以为空
     * @param callback - 回调
     */
    public static void put(Context context, Object tag, @NonNull String url, @NonNull String
            json, @Nullable Map<String, String> params, @Nullable Callback<String> callback) {
        PutRequest request = OkGo.put(url);

        if (callback != null) {
            if (tag instanceof String) {
                callback.setTAG((String) tag);
            }
            callback.setContext(context);
        }

        request
                .tag(tag)
                .upJson(json)
                .params(params)
                .execute(HttpCallBackBuild
                .buildStringCallback(callback));
    }

    /**
     * @see #postFile(Context, Object, String, Map, Map, Callback)
     */
    @Deprecated
    public static void postFile(Object tag, @NonNull String url, @Nullable Map<String, String>
            params, @NonNull Map<String, File> fileParams, Callback<String> callback) {
        postFile(null, tag, url, params, fileParams, callback);
    }

    /**
     * 上传文件
     *
     * @param context    - 上下文环境引用，建议使用{@link Activity}或{@link Fragment}的{@link Context}
     * @param tag        - 请求的 tag, 主要用于取消对应的请求
     * @param url        - 请求的网络路径
     * @param params     - 需要的{@code key-value}参数，可以为空
     * @param fileParams - 需要上传的文件集合，不可以为空
     * @param callback   - 回调
     */
    public static void postFile(Context context, Object tag, @NonNull String url, @Nullable
            Map<String, String> params, @NonNull Map<String, File> fileParams, @Nullable
            Callback<String> callback) {
        HttpParams httpParams = new HttpParams();
        for (String key : fileParams.keySet()) {
            httpParams.put(key, fileParams.get(key));
        }
        httpParams.put(params);

        PostRequest request = OkGo.post(url);

        if (callback != null) {
            if (tag instanceof String) {
                callback.setTAG((String) tag);
            }
            callback.setContext(context);
        }

        request
                .tag(tag)
                .params(httpParams)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    /**
     * 取消对应{@code tag}的网络连接
     *
     * @param tag - 连接的Tag
     */
    public static void cancel(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 取消所有的网络连接
     */
    public static void cancelAll() {
        OkGo.getInstance().cancelAll();
    }
}
