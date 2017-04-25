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

public abstract class BindRecyclerAdapter<V> extends BaseRecyclerAdapter<V> {

    protected List<V> mData;

    protected Context mContext;

    public BindRecyclerAdapter(Context context) {
        this(context, new ArrayList<V>());
    }

    public BindRecyclerAdapter(Context mContext, List<V> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    public void setData(Collection<? extends V> data) {
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

    public void addData(Collection<? extends V> data) {
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

    public void addItem(V item) {
        addItem(getItemCount(), item);
    }

    public void addItem(int position, V item) {
        if (mData == null) {
            mData = new ArrayList<>();
        }

        mData.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder<V> holder, int position) {
        holder.bindEntity(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
