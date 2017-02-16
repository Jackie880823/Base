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

package com.madxstudio.mb2be.base;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created 16/11/25.
 *
 * @author Jackie
 * @version 1.0
 */

public abstract class BindRecyclerAdapter<VH extends ViewHolder, E> extends
        BaseRecyclerAdapter<VH> {

    protected List<E> mData;

    protected Context mContext;

    public void setData(Collection<E> data) {
        if (data == null || data.isEmpty()) {
            // 清空数据
            mData = null;
        } else if (mData != null) {
            mData.clear();
            mData = new ArrayList<>(data);
        } else {
            mData = new ArrayList<>(data);
        }
        notifyDataSetChanged();

    }

    public void addData(Collection<E> data) {
        if (data == null || data.isEmpty()) {
            // 添加空数据直接返回
            return;
        }

        if (mData == null) {
            mData = new ArrayList<>(data);
            notifyDataSetChanged();
        } else {
            int positionStart = getItemCount();
            mData.addAll(data);
            notifyItemRangeInserted(positionStart, data.size());
        }
    }

    public void addItem(E item) {
        addItem(getItemCount(), item);
    }

    public void addItem(int position, E item) {
        if (mData == null) {
            mData = new ArrayList<>();
        }

        mData.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindEntity(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
