package sunningrain.github.likeshare.presenter.follow;

import android.util.Log;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.model.follow.FollowModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.follow.FollowView;

/**
 * Created by 27837 on  2019/5/5.
 */
public class FollowPresenter extends BasePresenter<FollowView> {
    private FollowModel mFollowModel = new FollowModel();
    public void getFollowList(int pageNo,int pageSize){
        Disposable disposable = mFollowModel.getFollowList(pageNo,pageSize)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(followResponse -> {
                    mView.refresh(followResponse);
                    Log.i("zy",followResponse.getData().toString());
                },throwable -> {
                    mView.hideLoading();
                    ApiException exception = (ApiException)throwable;
                    mView.showToast(exception.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }
}
