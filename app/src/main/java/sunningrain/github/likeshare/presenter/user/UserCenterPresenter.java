package sunningrain.github.likeshare.presenter.user;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.model.user.BaseUserInfoModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.userinfo.UserCenterView;

/**
 * Created by 27837 on  2019/5/6.
 */
public class UserCenterPresenter extends BasePresenter<UserCenterView> {
    private BaseUserInfoModel mBaseUserInfoModel = new BaseUserInfoModel();

    public void getBaseUserInfo(int userId) {
        mView.showLoading();
        Disposable disposable = mBaseUserInfoModel.getBaseUserInfo(userId)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(baseUserInfo -> {
                    mView.initData(baseUserInfo);
                }, throwable -> {
                    ApiException apiException = (ApiException) throwable;
                    mView.showToast(apiException.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void getPublishList(int pageNo, int pageSize,int userId) {
        Disposable disposable = mBaseUserInfoModel.getPublishList(pageNo, pageSize,userId)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(publishResponse -> {
                    mView.hideLoading();
                    mView.setData(publishResponse);
                }, throwable -> {
                    mView.hideLoading();
                    ApiException apiException = (ApiException) throwable;
                    mView.showToast(apiException.getDisplayMessage());

                });
        compositeDisposable.add(disposable);
    }

    public void getBaseFollowList(int userId) {
        Disposable disposable = mBaseUserInfoModel.getBaseFollowList(userId)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(baseFollows -> {
                    mView.setBaseFollowInfo(baseFollows);
                }, throwable -> {
                    ApiException apiException = (ApiException) throwable;
                    mView.showToast(apiException.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void getBaseFollowedList(int userId) {
        Disposable disposable = mBaseUserInfoModel.getBaseFollowedList(userId)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(baseFollows -> {
                    mView.setBaseFollowedInfo(baseFollows);
                }, throwable -> {
                    ApiException apiException = (ApiException) throwable;
                    mView.showToast(apiException.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }
}
