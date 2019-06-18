package sunningrain.github.likeshare.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import sunningrain.github.likeshare.net.threadschedule.SchedulerProvider;

/**
 * Create by 27837 on 2019/4/6.
 */
public class BasePresenter<V> {
    protected Context mContext;
    protected V mView;
    protected SchedulerProvider schedulerProvider;
    protected CompositeDisposable compositeDisposable;

    protected void onCleared(){

    }

    /**
     * 绑定view
     * @param context
     * @param view
     */
    public void attachView(Context context,V view){
        this.mContext = context;
        this.mView = view;
        schedulerProvider = SchedulerProvider.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 解绑view
     */
    public void detachView(){
        this.mView = null;
    }

    /**
     * 是否已经绑定view
     * @return
     */
    public boolean isAttachView(){
        return this.mView != null;
    }

    /**
     * 创建Presenter
     * @param savedState 保存的数据
     */
    public void onCreatePresenter(@Nullable Bundle savedState){

    }

    /**
     * 销毁Presenter
     */
    public void onDestoryPresenter(){
        this.mContext = null;
        compositeDisposable.dispose();
        detachView();
    }

    /**
     * 保存数据
     * @param outState 数据
     */
    public void onSaveInstanceState(Bundle outState){

    }
}
