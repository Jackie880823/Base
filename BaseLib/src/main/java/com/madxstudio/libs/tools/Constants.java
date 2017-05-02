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

/**
 * Created 17/2/14.
 *
 * @author Jackie
 * @version 1.0
 */
public class Constants {

    /**
     * csv的扩展名文件
     */
    public static final String EXTEND_CSV = ".csv";

    /**
     * http协议头，在{@link android.net.Uri}中可以用这个来判断网络协议类型
     */
    public static final String SCHEME_HTTP = "http";

    /**
     * http协议头，在{@link android.net.Uri}中可以用这个来判断网络协议类型
     */
    public static final String SCHEME_HTTPS = "http";

    /**
     * 本应用主要保存{@code key-value}的{@link android.content.SharedPreferences}的文件名称
     */
    public static final String PREFERENCE_NAME = "mb2be_preference";

    /*bundle 的extra使用常量需要在常量前添加EXTRA_字段，与其它常量区分*/
    public static final String EXTRA_IS_EMAIL = "isEmail";
    public static final String EXTRA_IS_SIGN_UP = "isSignUp";
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_ENTITY = "entity";

    /**
     * 状态值<p>
     * 1 - 为真；<p>
     * 0 - 为假
     */
    public static final String STATUS_ONE = "1";
    public static final String STATUS_ZERO = "0";

    public static final String DISPLAY_NULL_DATA = "-";

    /**
     * 分页访问个数；
     */
    public static final int OFF_SET = 20;

    public static final String STATUS_ACTIVATED = "activated";
    public static final String KEY_SUGGESTIONS = "Suggestions";
}
