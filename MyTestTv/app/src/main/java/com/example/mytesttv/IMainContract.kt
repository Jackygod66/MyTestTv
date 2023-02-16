package com.example.mytesttv

import com.example.mytesttv.BaseMvp.ResultCallback
import com.example.mytesttv.appinfo.AppInfo

interface IMainContract {
    interface IMainView {
        /**
         * 展示所有app应用信息
         * @param appInfoList app应用列表
         */
        fun showAllApp(appInfoList: MutableList<AppInfo>?)

        /**
         * 在界面上移除指定app图标
         * @param position 要移除图标所在列表的位置
         */
        fun deleteAppInView(position: Int)

        /**
         * 在界面上移动指定app图标
         * @param fromPosition 要移动的app当前所处位置
         * @param toPosition app需要移动到的目标位置
         */
        fun moveAppInView(fromPosition: Int, toPosition: Int)
    }

    interface IMainPresenter {
        /**
         * 获取所有应用信息
         */
        fun getAllApp()

        /**
         * 删除App
         * @param position 要删除的App所在列表的当前位置
         */
        fun deleteApp(position: Int)

        /**
         * 移动App的位置
         * @param fromPosition 需要移动的App在列表的当前位置
         * @param toPosition 要移动到列表的目标位置
         */
        fun moveAppToTargetPosition(fromPosition: Int, toPosition: Int)
    }

    interface IMainModel {
        /**
         * 获取所有应用信息
         */
        fun getAllApp(callback : ResultCallback<MutableList<AppInfo>?>)
        /**
         * 删除App
         * @param position 要删除的App所在列表的当前位置
         */
        fun deleteAppInModel(position: Int)

        /**
         * 移动App的位置
         * @param fromPosition 需要移动的App在列表的当前位置
         * @param toPosition 要移动到列表的目标位置
         */
        fun moveAppToTargetPositionInModel(fromPosition: Int, toPosition: Int)
    }
}