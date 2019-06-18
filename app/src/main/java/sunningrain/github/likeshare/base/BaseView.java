package sunningrain.github.likeshare.base;

import android.content.Context;

/**
 * Create by ZhangYu on 2019/4/5
 */
public interface BaseView {
    /**
     * 显示加载中
     */
    void showLoading();
    /**
     * 隐藏加载
     */
    void hideLoading();
    /**
     * 获取数据失败
     */
    void onError(Throwable throwable);

    /**
     * 弹出消息
     * @param msg
     */
    void showToast(String msg);

}
