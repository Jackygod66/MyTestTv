package com.example.mytesttv.MvpPresenter

import com.example.mytesttv.appinfo.AppInfo
import com.example.mytesttv.BaseMvp.BaseMvpPresenter
import com.example.mytesttv.IMainContract
import com.example.mytesttv.MvpModel.MainFragmentModel
import com.example.mytesttv.BaseMvp.ResultCallback

class MainFragmentPresenter : BaseMvpPresenter<IMainContract.IMainView>(), IMainContract.IMainPresenter {
    override fun getAllApp() {
        getModel(MainFragmentModel::class.java).getAllApp(object : ResultCallback<MutableList<AppInfo>?> {
            override fun onSuccess(result: MutableList<AppInfo>?) {
                result?.apply {
                    getView()?.showAllApp(this)
                }
            }

            override fun onFail(message: String) {

            }
        })
    }

    override fun deleteApp(position: Int) {
        getModel(MainFragmentModel::class.java).deleteAppInModel(position)
        getView()?.deleteAppInView(position)
    }

    override fun moveAppToTargetPosition(fromPosition: Int, toPosition: Int) {
        getModel(MainFragmentModel::class.java).moveAppToTargetPositionInModel(fromPosition, toPosition)
        getView()?.moveAppInView(fromPosition, toPosition)
    }
}