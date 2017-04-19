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

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.madxstudio.mb2be.BaseApp;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * Created 17/2/15.
 *
 * @author Jackie
 * @version 1.0
 */

public class HttpUtil {
    private static final String TAG = "HttpUtil";

    public static void init(@NonNull Map<String, String> headerMap) {
        // 定义header,不支持中文
        // TODO: 17/2/15 配置全局所需的header信息
        HttpHeaders headers = new HttpHeaders();
        for (String s : headerMap.keySet()) {
            headers.put(s, headerMap.get(s));
        }

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

    /**
     * 从服务器中获取数据
     *
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void get(Object tag, @NonNull String url, Map<String, String> params,
                           final Callback<String> callback) {
        OkGo.get(url)
                .tag(tag)
                .params(params, true)
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    /**
     * 获取网络图片
     *
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void getBitmap(Object tag, @NonNull String url, @Nullable Map<String, String>
            params, final Callback<Bitmap> callback) {

        OkGo.get(url)
                .tag(tag)
                .params(params, true)
                .execute(HttpCallBackBuild.buildBitmapCallback(callback));
    }

    /**
     * 下载文件
     *
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数，可以为空
     * @param callback - 回调
     */
    public static void download(Object tag, @NonNull String url, Map<String, String> params,
                                DownloadCallback
            callback) {
        OkGo.get(url)
                .tag(tag)
                .params(params, true)
                .execute(HttpCallBackBuild.buildFileCallback(callback));
    }

    /**
     * 上传参数
     *
     * @param tag      - 请求的 tag, 主要用于取消对应的请求
     * @param url      - 请求的网络路径
     * @param params   - 需要的{@code key-value}参数， 不可以为空
     * @param callback - 回调
     */
    public static void post(Object tag, @NonNull String url, @NonNull Map<String, String> params,
                            final Callback<String> callback) {
        OkGo.post(url)
                .tag(tag)
                .upJson(new JSONObject(params))
                .execute(HttpCallBackBuild.buildStringCallback(callback));
    }

    /**
     * 上传文件
     *
     * @param tag        - 请求的 tag, 主要用于取消对应的请求
     * @param url        - 请求的网络路径
     * @param params     - 需要的{@code key-value}参数，可以为空
     * @param fileParams - 需要上传的文件集合，不可以为空
     * @param callback   - 回调
     */
    public static void postFile(Object tag, @NonNull String url, @Nullable Map<String, String>
            params, @NonNull Map<String, File> fileParams, Callback<String> callback) {
        HttpParams httpParams = new HttpParams();
        for (String key : fileParams.keySet()) {
            httpParams.put(key, fileParams.get(key));
        }
        httpParams.put(params);

        OkGo.post(url)
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
