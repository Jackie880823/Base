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

package com.madxstudio.mb2be.tools.image;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.madxstudio.mb2be.tools.Constant;
import com.madxstudio.mb2be.tools.PreferencesUtil;

/**
 * Created 17/2/14.
 *
 * @author Ja\ckie
 * @version 1.0
 */

public class ImageLoader {

    // 加载签名
    public static final String KEY_SIGNATURE_VERSION = "KEY_SIGNATURE_VERSION";
    public static int SIGNATURE_VERSION = -1;


    /**
     * 初始化对应配置的{@link DrawableTypeRequest}, 统一封装加载图片的请求
     *
     * @param context
     * @param configuration
     * @return
     */
    @Nullable
    private static DrawableTypeRequest initTypeRequest(Context context, Configuration
            configuration) {
        RequestManager manager = Glide.with(context);
        DrawableTypeRequest typeRequest = null;
        if (configuration.uri != null && !configuration.uri.equals(Uri.EMPTY)) {
            if (Constant.SCHEME_HTTP.equalsIgnoreCase(configuration.uri.getScheme()) || Constant
                    .SCHEME_HTTPS.equalsIgnoreCase(configuration.uri.getScheme())) {
                configuration.cacheStrategy = Configuration.CACHE_STRATEGY_ALL;
            } else {
                configuration.cacheStrategy = Configuration.CACHE_STRATEGY_RESULT;
            }
            typeRequest = manager.load(configuration.uri);
        } else if (!TextUtils.isEmpty(configuration.url)) {
            if (configuration.url.contains(Constant.SCHEME_HTTP)) {
                configuration.cacheStrategy = Configuration.CACHE_STRATEGY_ALL;
            } else {
                configuration.cacheStrategy = Configuration.CACHE_STRATEGY_RESULT;
            }
            typeRequest = manager.load(configuration.url);
        } else if (configuration.file != null) {
            configuration.cacheStrategy = Configuration.CACHE_STRATEGY_RESULT;
            typeRequest = manager.load(configuration.file);
        } else if (configuration.model != null) {
            typeRequest = manager.load(configuration.model);
        } else if (configuration.drawableId != null) {
            configuration.cacheStrategy = Configuration.CACHE_STRATEGY_RESULT;
            typeRequest = manager.load(configuration.drawableId);
        }

        if (typeRequest == null) {
            return null;
        }

        return typeRequest;
    }


    /**
     * @param context
     * @param configuration
     * @return
     */
    private static GenericRequestBuilder initRequestBuilder(@NonNull Context context, @NonNull
            Configuration configuration) {
        // 这里牵涉到Glide的build的流程，不要轻易改变其顺序，否则有些build设置无法起做用
        DrawableTypeRequest typeRequest = initTypeRequest(context, configuration);
        if (typeRequest == null) {
            return null;
        }

        GenericRequestBuilder genericRequestBuilder;
        if (configuration.isGif) {
            // gif图片不能裁剪
            genericRequestBuilder = typeRequest.asGif();
        } else {
            DrawableRequestBuilder builder;

            // 加载图片的裁剪方式
            if (configuration.scaleType == Configuration.CENTER_CROP) {
                builder = typeRequest.centerCrop();
            } else if (configuration.scaleType == Configuration.FIT_CENTER) {
                builder = typeRequest.fitCenter();
            } else {
                builder = typeRequest;
            }


            genericRequestBuilder = builder;
        }

        // 是否需要动画
        if (configuration.doNotAnimate) {
            genericRequestBuilder = genericRequestBuilder.dontAnimate();
        } else {
            if (configuration.thumbnailAvailable) {
                // 模糊渐进
                genericRequestBuilder = genericRequestBuilder.thumbnail(0.5f);
            }
        }

        switch (configuration.cacheStrategy) {
            case Configuration.CACHE_STRATEGY_RESULT: // 缓存加载后的资源
                genericRequestBuilder = genericRequestBuilder.diskCacheStrategy(DiskCacheStrategy
                        .RESULT);
                break;
            case Configuration.CACHE_STRATEGY_SOURCE: // 缓存源
                genericRequestBuilder = genericRequestBuilder.diskCacheStrategy(DiskCacheStrategy
                        .SOURCE);
                break;
            default: // 缓存所有的资源
                genericRequestBuilder = genericRequestBuilder.diskCacheStrategy(DiskCacheStrategy
                        .ALL);
                break;
        }


        genericRequestBuilder = genericRequestBuilder.placeholder(configuration.placeholderId)
                .error(configuration.errorDrawableId);
        // 是否先显示小图
        if (!Uri.EMPTY.equals(configuration.thumbnailUri)) {
            configuration.uri = configuration.thumbnailUri;
            configuration.url = null;
            configuration.file = null;
            configuration.model = null;
            configuration.thumbnailUri = null;
            GenericRequestBuilder thumbnailRequest = initRequestBuilder(context, configuration);
            genericRequestBuilder = genericRequestBuilder.thumbnail(thumbnailRequest);
        }
        return genericRequestBuilder;
    }

    /**
     * 根据{@link android.content.res.Configuration}配置加载图片到指定的{@link ImageView imageView}
     *
     * @param context
     * @param imageView     - 显示在此{@link ImageView}中
     * @param configuration - 需要显示的配置封装实例
     */
    public static void display(Context context, ImageView imageView, Configuration configuration,
                               int signature) {

        if (imageView == null) {
            return;
        }

        GenericRequestBuilder genericRequestBuilder = initRequestBuilder(context, configuration);

        if (genericRequestBuilder != null) {
            genericRequestBuilder.signature(new IntegerVersionSignature(signature)).into(imageView);
        }
    }


    /**
     * 根据{@link android.content.res.Configuration}配置加载图片到指定的{@link ImageView imageView}
     *
     * @param context
     * @param imageView     - 显示在此{@link ImageView}中
     * @param configuration - 需要显示的配置封装实例
     */
    public static void display(@NonNull Context context, ImageView imageView, @NonNull
            Configuration configuration) {
        if (SIGNATURE_VERSION == -1) {
            SIGNATURE_VERSION = PreferencesUtil.getValue(KEY_SIGNATURE_VERSION, SIGNATURE_VERSION);
        }
        display(context, imageView, configuration, SIGNATURE_VERSION);
    }

    /**
     * 加载指定{@link Uri 路径}的图片到{@link ImageView imageView}控件中 <br/>
     * 这个函数以后应该会丢弃，若想使用相应加载功能请使用{@link #display(Context, ImageView, Configuration)}
     * <br/>
     * 设置{@link Configuration#url}的值与{@link Uri uri}对应即可
     *
     * @param context
     * @param uri - 图片的{@link Uri}路径
     * @param imageView
     */
    @Deprecated
    public static void display(@NonNull Context context, Uri uri, @NonNull ImageView imageView) {
        Configuration configuration = new Configuration();
        configuration.uri = uri;
        display(context, imageView, configuration);
    }

    /**
     * 加载指定{@link DrawableRes}的图片到{@link ImageView imageView}控件中 <br/>
     * 这个函数以后应该会丢弃，若想使用相应加载功能请使用{@link #display(Context, ImageView, Configuration)}
     * <br/>
     * 设置{@link Configuration#url}的值与{@link DrawableRes drawableID}对应即可
     *
     * @param context
     * @param drawableID
     * @param imageView
     */
    @Deprecated
    public static void display(Context context, @DrawableRes int drawableID, @NonNull ImageView
            imageView) {
        Configuration configuration = new Configuration();
        configuration.drawableId = drawableID;

        display(context, imageView, configuration);
    }

}
