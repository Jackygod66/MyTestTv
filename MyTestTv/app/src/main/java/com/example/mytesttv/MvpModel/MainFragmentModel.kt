package com.example.mytesttv.MvpModel

import com.example.mytesttv.appinfo.AppInfo
import com.example.mytesttv.appinfo.AppInfoService
import com.example.mytesttv.BaseMvp.BaseMvpModel
import com.example.mytesttv.IMainContract
import com.example.mytesttv.BaseMvp.ResultCallback

class MainFragmentModel : BaseMvpModel() , IMainContract.IMainModel {
    private var mAppInfoList : MutableList<AppInfo>? = null

    override fun getAllApp(callback: ResultCallback<MutableList<AppInfo>?>) {
        val pm = getContext()?.packageManager
        Thread {
            mAppInfoList = AppInfoService.getAppInfo(pm)
            callback.onSuccess(mAppInfoList)
        }.start()
    }

    override fun deleteAppInModel(position: Int) {
        mAppInfoList?.removeAt(position)
    }

    override fun moveAppToTargetPositionInModel(fromPosition: Int, toPosition: Int) {
        val appInfo = mAppInfoList?.removeAt(fromPosition)
        appInfo?.apply {
            mAppInfoList?.add(toPosition, this)
        }
    }
}