package sunningrain.github.likeshare.presenter.user;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.model.user.FollowAndFansModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.userinfo.FollowAndFansView;

/**
 * Created by 27837 on  2019/5/8.
 */
public class FollowAndFansPresenter extends BasePresenter<FollowAndFansView> {
    private FollowAndFansModel mFollowAndFansModel = new FollowAndFansModel();
    public void getFollowList(){
        mView.showLoading();
        Disposable disposable = mFollowAndFansModel.getUserFollows()
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(baseUserInfos -> {
                    mView.hideLoading();
                    mView.showUserFollows(baseUserInfos);
                },throwable -> {
                    mView.hideLoading();
                    ApiException apiException = (ApiException)throwable;
                    mView.showToast(apiException.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }
    public void getFansList(){
        mView.showLoading();
        Disposable disposable = mFollowAndFansModel.getUserFans()
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(baseUserInfos -> {
                    mView.hideLoading();
                    mView.showUserFans(baseUserInfos);
                },throwable -> {
                    mView.hideLoading();
                    ApiException apiException = (ApiException)throwable;
                    mView.showToast(apiException.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }
}
