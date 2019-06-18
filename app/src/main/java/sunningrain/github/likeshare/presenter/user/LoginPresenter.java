package sunningrain.github.likeshare.presenter.user;

import android.app.Activity;
import android.util.Log;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.model.user.LoginModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.login.LoginView;
import sunningrain.github.likeshare.util.SharedUtil;
import sunningrain.github.likeshare.util.StringUtils;

/**
 * Created by 27837 on  2019/4/14.
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginModel loginModel = new LoginModel();

    public void login(String phoneNumber, String password) {
        checkParameter(phoneNumber, password);

    }

    private void checkParameter(String phoneNumber, String password) {
        if (StringUtils.checkPhoneNumber(phoneNumber)) {
            if (StringUtils.checkPassWord(password)) {
                mView.showLoading();
                Disposable disposable = loginModel.login(phoneNumber, password)
                        .compose(ResponseTransformer.handleResult())
                        .compose(schedulerProvider.applySchedulers())
                        .subscribe(userBean -> {
                                    //保存用户信息
                                    SharedUtil.save(Const.Auth.USER_ID, userBean.getId());
                                    SharedUtil.save(Const.User.USER_PHONE, userBean.getUserPhone().trim());
                                    SharedUtil.save(Const.User.USER_NAME, userBean.getUserName().trim());
                                    SharedUtil.save(Const.User.USER_PIC, userBean.getUserPic().trim());
                                    SharedUtil.save(Const.User.USER_SIGNATURE, userBean.getUserSignat().trim());
                                    SharedUtil.save(Const.User.USER_PASSWORD, userBean.getUserPwd().trim());
                                    LikeShare.refreshLoginState();
                                    Log.i("zy",userBean.toString());
                                    mView.hideLoading();
                                    mView.showToast("登录成功 ");
                                    mView.jumpToHome();
                                    if (mContext instanceof Activity) {
                                        Activity context = (Activity)mContext;
                                        context.finish();
                                    }
                                }
                                , throwable -> {
                                    mView.hideLoading();
                                    ApiException e = (ApiException) throwable;
                                    mView.showToast(e.getDisplayMessage());
                                });
                compositeDisposable.add(disposable);
            } else {
                mView.showToast("请输入密码");
            }
        } else {
            mView.showToast("请输入正确的手机号");
        }
    }

}
