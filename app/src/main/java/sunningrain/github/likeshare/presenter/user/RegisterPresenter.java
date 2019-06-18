package sunningrain.github.likeshare.presenter.user;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.model.user.RegisterModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.register.RegisterView;
import sunningrain.github.likeshare.util.StringUtils;

/**
 * Created by 27837 on  2019/4/18.
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {
    RegisterModel registerModel = new RegisterModel();
    public void setUser(String userPhone,String password){
        if (!StringUtils.checkPhoneNumber(userPhone)){
            mView.showToast("请输入合法的手机号");
            return;
        }
        if (password.length()<6 || password.length()>20){
            mView.showToast("密码长度必须大于5小于21");
            return;
        }

        daRegister(userPhone, password);
    }

    private void daRegister(String userPhone, String password) {
        mView.showLoading();
        Disposable disposable = registerModel.register(userPhone,password)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(usr-> {
                    //保存信息
                    mView.hideLoading();
                    mView.jumpToLoginActivity();
                        }
                ,throwable -> {
                    mView.hideLoading();
                            ApiException exception = (ApiException)throwable;
                    mView.showToast(exception.getCode()+" "+exception.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }
}
