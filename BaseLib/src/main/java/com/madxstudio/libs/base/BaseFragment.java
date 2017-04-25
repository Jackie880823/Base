/*
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
 *  Copyright (C) 2017 The Jackie's Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.madxstudio.libs.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madxstudio.libs.R;
import com.madxstudio.libs.interfaces.LayoutAssist;
import com.madxstudio.libs.tools.http.HttpUtil;

/**
 * Created 17/2/13.
 *
 * @author Jackie
 * @version 1.0
 */

public abstract class BaseFragment extends Fragment implements LayoutAssist, View.OnClickListener{


    protected ProgressDialog progressDialog;

    public BaseFragment() {
    }

    @LayoutRes
    public abstract int getLayoutId();

    public <V extends View> V getViewById(@IdRes int id) {
        return (V) getView().findViewById(id);
    }

    @Override
    @CallSuper
    public void initView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 用于显示加载框
     * @param tag 网络请求的tag，可以控制在加载框取消时同时关闭对应的网络请求
     * @param strMessage 提示信息
     * @param blnCancelable
     * @param blnCanceledOnTouchOutside
     */
    public void showLoadingProgress(final Object tag, String strMessage, boolean blnCancelable, boolean
            blnCanceledOnTouchOutside)
    {
        if (progressDialog == null)
        {
            progressDialog = new ProgressDialog(getContext());
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
        if (!TextUtils.isEmpty(strMessage))
        {
            progressDialog.setMessage(strMessage);
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(blnCancelable);
        progressDialog.setCanceledOnTouchOutside(blnCanceledOnTouchOutside);
        if (!progressDialog.isShowing() && getUserVisibleHint() && !isDetached()) {
            progressDialog.show();
        }
    }

    public void dismissLoadingProgress()
    {
        if (progressDialog != null && progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }
}
