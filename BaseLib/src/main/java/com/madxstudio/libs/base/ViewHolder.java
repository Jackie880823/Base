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

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.madxstudio.libs.interfaces.OnViewHolderClickListener;
import com.madxstudio.libs.interfaces.OnViewHolderLongClickListener;

/**
 * Created 16/11/25.
 *
 * @author Jackie
 * @version 1.0
 */


public abstract class ViewHolder<V> extends RecyclerView.ViewHolder implements View
        .OnClickListener, View.OnLongClickListener {
    private OnViewHolderClickListener clickListener;
    private OnViewHolderLongClickListener longClickListener;

    protected V entity;

    public ViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @CallSuper
    public void bindEntity(V entity) {
        this.entity = entity;
    }

    @Override
    public void onClick(View v) {
        if (clickListener == null) {
            return;
        }

        clickListener.onClick(v, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener == null) {
            return false;
        }
        longClickListener.onLongClick(v, getAdapterPosition());
        return false;
    }

    public void setClickListener(OnViewHolderClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setLongClickListener(OnViewHolderLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
}
