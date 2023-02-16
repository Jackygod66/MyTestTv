package com.example.mytesttv.firstmvp;

import com.example.mytesttv.appinfo.AppInfo;

import java.util.List;

public interface MoveAndDeleteView {
    /**
     * 展示所有app应用信息
     * @param appInfoList app应用列表
     */
    void showAllApp(List<AppInfo> appInfoList);

    /**
     * 在界面上移除指定app图标
     * @param position 要移除图标所在列表的位置
     */
    void deleteAppInView(int position);

    /**
     * 在界面上移动指定app图标
     * @param fromPosition 要移动的app当前所处位置
     * @param toPosition app需要移动到的目标位置
     */
    void moveAppInView(int fromPosition, int toPosition);
}
