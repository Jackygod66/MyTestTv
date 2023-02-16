package com.example.mytesttv.BaseMvp

import android.content.Context

abstract class BaseMvpModel {

    private var mContext: Context? = null
    fun getContext(): Context? {
        return mContext
    }

    /*************分界线以上为外部使用的方法，以下为私有或生命周期方法不应主动调用*****************/
    fun onCreate(context: Context?) {
        mContext = context
    }
    fun onDestroy() {
        mContext = null
    }
}