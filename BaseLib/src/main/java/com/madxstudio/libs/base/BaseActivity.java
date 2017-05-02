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

package com.madxstudio.libs.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.madxstudio.libs.BaseApp;
import com.madxstudio.libs.R;
import com.madxstudio.libs.interfaces.LayoutAssist;
import com.madxstudio.libs.tools.LogUtil;
import com.madxstudio.libs.tools.NetworkUtils;
import com.madxstudio.libs.tools.http.HttpUtil;

/**
 * Created 17/2/13.
 *
 * @author Jackie
 * @version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity implements LayoutAssist, View
        .OnClickListener {

    private TextView tvMsg;

    protected ActionBar actionBar;
    protected View msgBar;
    protected ProgressDialog progressDialog;
    private NetworkReceiver receiver;

    @LayoutRes
    public abstract int getLayoutId();

    public <V extends View> V getViewById(@IdRes int id) {
        return (V) findViewById(id);
    }

    @Override
    @CallSuper
    public void initView() {
        initToolbar();
    }

    protected void initToolbar() {
        Toolbar toolbar = getViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            msgBar = getViewById(R.id.msg_bar);
            tvMsg = getViewById(R.id.msg);

            getViewById(R.id.iv_close_msg).setOnClickListener(this);
            goneMessageBar();
        }
    }

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        BaseApp.addActivity(this);
        receiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApp.removeActivity(this);
        unregisterReceiver(receiver);
    }

    @Override
    @CallSuper
    public void onClick(View v) {
        if (R.id.iv_close_msg == v.getId()) {
            goneMessageBar();
        }
    }

    protected void goneMessageBar() {
        if (msgBar == null) {
            return;
        }
        msgBar.setVisibility(View.GONE);
    }

    protected void setMsgBackgroundResource(@DrawableRes int backgroundResource) {
        if (msgBar == null) {
            return;
        }
        msgBar.setBackgroundResource(backgroundResource);
    }

    protected void setMessage(@StringRes int messageRes) {
        setMessage(getText(messageRes));
    }

    protected void setMessage(@StringRes int messageRes, int gravity) {
        CharSequence message = getText(messageRes);
        setMessage(message, gravity);
    }

    protected void setMessage(CharSequence message) {
        setMessage(message, Gravity.CENTER);
    }

    protected void setMessage(CharSequence message, int gravity) {
        if (tvMsg == null) {
            return;
        }

        msgBar.setVisibility(View.VISIBLE);
        tvMsg.setText(message);
        tvMsg.setGravity(gravity);
    }

    protected CharSequence getMessage() {
        if (tvMsg == null) {
            return null;
        } else {
            return tvMsg.getText();
        }
    }

    /**
     * @see #showLoadingProgress(Object, String, boolean, boolean)
     */
    public void showLoadingProgress(String strMessage, boolean blnCancelable, boolean
            blnCanceledOnTouchOutside) {
        showLoadingProgress(null, strMessage, blnCancelable, blnCanceledOnTouchOutside);
    }

    /**
     * 用于显示加载框
     *
     * @param tag                       网络请求的tag，可以控制在加载框取消时同时关闭对应的网络请求
     * @param strMessage                提示信息
     * @param blnCancelable
     * @param blnCanceledOnTouchOutside
     */
    public void showLoadingProgress(@Nullable final Object tag, String strMessage, boolean
            blnCancelable, boolean blnCanceledOnTouchOutside) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }

        if (tag != null) {
            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    HttpUtil.cancel(tag);
                }
            });
        }

        progressDialog.setMessage(getString(R.string.loading));
        if (!TextUtils.isEmpty(strMessage)) {
            progressDialog.setMessage(strMessage);
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(blnCancelable);
        progressDialog.setCanceledOnTouchOutside(blnCanceledOnTouchOutside);
        if (!progressDialog.isShowing() && !this.isDestroyed() && !this.isFinishing()) {
            progressDialog.show();
        }
    }

    public void dismissLoadingProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private class NetworkReceiver extends BroadcastReceiver {
        private static final String TAG = "NetworkReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                LogUtil.d(TAG, "onReceive: ");
                boolean isConnect = NetworkUtils.isConnected(context);
                if (isConnect && context.getString(R.string.msg_no_internet).equals(getMessage())) {
                    goneMessageBar();
                } else if (!isConnect) {
                    setMessage(R.string.msg_no_internet);
                }
            }
        }
    }
}

