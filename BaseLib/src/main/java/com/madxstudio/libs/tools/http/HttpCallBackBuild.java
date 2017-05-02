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

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.madxstudio.libs.entities.FailureResponse;
import com.madxstudio.libs.tools.LogUtil;
import com.madxstudio.libs.tools.PreferencesUtil;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created 17/2/15.
 *
 * @author Jackie
 * @version 1.0
 */

class HttpCallBackBuild {
    private static final String TAG = "HttpCallBackBuild";

    static AbsCallback buildStringCallback(final Callback<String> callback) {
        AbsCallback result;
        if (callback == null) {
            return null;
        }
        result = new StringCallback() {
            String body;

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                callback.onConnect();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                JsonObject json = new JsonParser().parse(s).getAsJsonObject();
                if (json.has("data")) {
                    s = json.get("data").toString();
                }
                PreferencesUtil.saveValue(response.request().url().toString(), s);
                LogUtil.d(TAG, "onSuccess: url " + response.request().url().toString());
                callback.onResponse(s, response.message());
            }

            @Override
            public void onError(final Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Gson gson = new Gson();
                try {

                    FailureResponse failure = gson.fromJson(body, new TypeToken<FailureResponse>()
                    {}.getType());

                    callback.onFailure(failure.getMessage(), e);
                } catch (Exception e1){
                    String message = response == null? e.getMessage() : response.message();
                    callback.onFailure(message, e);
                }
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long
                    networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                callback.onProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long
                    networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                callback.onProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                callback.onFinish(s, e);
            }

            @Override
            public String convertSuccess(Response response) throws Exception {
                body = super.convertSuccess(response);
                return body;
            }
        };
        return result;
    }

    public static AbsCallback buildBitmapCallback(final Callback<Bitmap> callback) {
        if (callback == null) {
            return null;
        }

        AbsCallback result;
        result = new BitmapCallback() {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                callback.onConnect();
            }

            @Override
            public void onSuccess(Bitmap bitmap, Call call, Response response) {
                callback.onResponse(bitmap, response.message());
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callback.onFailure(response.message(), e);
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long
                    networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                callback.onProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long
                    networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                callback.onProgress(currentSize, totalSize, progress, networkSpeed);
            }
        };
        return result;
    }

    static AbsCallback buildFileCallback(final DownloadCallback callback) {
        if (callback == null) {
            return null;
        }
        AbsCallback result;
        result = new FileCallback(callback.getDestFileDir(), callback.getDestFileName()) {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                callback.onConnect();
            }

            @Override
            public void onSuccess(File file, Call call, Response response) {
                callback.onResponse(file, response.message());
                callback.onFinish(file, null);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                callback.onFailure(response.message(), e);
                callback.onFinish(null, e);
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long
                    networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                callback.onProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long
                    networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                callback.onProgress(currentSize, totalSize, progress, networkSpeed);
            }
        };

        return result;
    }
}
