package com.example.mytesttv.firstmvp;

public interface MoveAndDeletePresenter {
    /**
     * 初始化
     */
    void init();

    /**
     * 获取所有应用信息
     */
    void getAllApp();

    /**
     * 删除App
     * @param position 要删除的App所在列表的当前位置
     */
    void deleteApp(int position);

    /**
     * 移动App的位置
     * @param fromPosition 需要移动的App在列表的当前位置
     * @param toPosition 要移动到列表的目标位置
     */
    void moveAppToTargetPosition(int fromPosition, int toPosition);
}
