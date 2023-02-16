package com.example.mytesttv.firstmvp;

import android.content.Context;

import com.example.mytesttv.appinfo.AppInfo;
import com.example.mytesttv.appinfo.AppInfoService;

import java.util.List;

public class MoveAndDeletePresenterImpl implements MoveAndDeletePresenter{

    private List<AppInfo> mAppInfoList;
    private MoveAndDeleteView mView;
    private Context mContext;

    private AppInfoService mAppInfoService;

    public MoveAndDeletePresenterImpl(MoveAndDeleteView view, Context context) {
        mView = view;
        mContext = context;
        init();
    }

    @Override
    public void init() {
        mAppInfoService = new AppInfoService(mContext);
    }

    @Override
    public void getAllApp() {
        new Thread(){
            @Override
            public void run() {
                mAppInfoList = mAppInfoService.getAppInfo();
                if (mView != null) mView.showAllApp(mAppInfoList);
            }
        }.start();
    }

    @Override
    public void deleteApp(int position) {
        if (mAppInfoList != null) mAppInfoList.remove(position);
        if (mView != null) {
            mView.deleteAppInView(position);
        }
    }

    /**
     * 暂时只实现往前移动
     * @param fromPosition 需要移动的App在列表的当前位置
     * @param toPosition 要移动到列表的目标位置
     */
    @Override
    public void moveAppToTargetPosition(int fromPosition, int toPosition) {
        if (mAppInfoList != null) {
            AppInfo appInfo = mAppInfoList.remove(fromPosition);
            mAppInfoList.add(toPosition, appInfo);
        }
        if (mView != null) {
            mView.moveAppInView(fromPosition, toPosition);
        }
    }
}
