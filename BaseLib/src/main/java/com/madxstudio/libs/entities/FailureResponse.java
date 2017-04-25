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

package com.madxstudio.libs.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * 连失败后服务器返回的失败信息封装类
 * Created 17/3/31.
 *
 * @author Jackie
 * @version 1.0
 */

public class FailureResponse implements Parcelable {
    /**
     * name : Forbidden
     * message : Your account has not been activated. Please contact your supplier to activate
     * the account
     * code : 0
     * status : 403
     * type : yii\web\HttpException
     */
    private String name;
    private String message;
    private int code;
    private int status;
    private String type;

    public FailureResponse() {}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getMessage() { return message;}

    public void setMessage(String message) { this.message = message;}

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public int getStatus() { return status;}

    public void setStatus(int status) { this.status = status;}

    public String getType() { return type;}

    public void setType(String type) { this.type = type;}

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.message);
        dest.writeInt(this.code);
        dest.writeInt(this.status);
        dest.writeString(this.type);
    }

    protected FailureResponse(Parcel in) {
        this.name = in.readString();
        this.message = in.readString();
        this.code = in.readInt();
        this.status = in.readInt();
        this.type = in.readString();
    }

    public static final Creator<FailureResponse> CREATOR = new Creator<FailureResponse>() {
        @Override
        public FailureResponse createFromParcel(Parcel source) {return new FailureResponse(source);}

        @Override
        public FailureResponse[] newArray(int size) {return new FailureResponse[size];}
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FailureResponse)) return false;

        FailureResponse that = (FailureResponse) o;

        if (getCode() != that.getCode()) return false;
        if (getStatus() != that.getStatus()) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null)
            return false;
        if (getMessage() != null ? !getMessage().equals(that.getMessage()) : that.getMessage() !=
                null)
            return false;
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getMessage() != null ? getMessage().hashCode() : 0);
        result = 31 * result + getCode();
        result = 31 * result + getStatus();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FailureResponse" + new Gson().toJson(this);
    }
}
