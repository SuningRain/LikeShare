package sunningrain.github.likeshare.presenter.user;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.callback.BaseListener;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.model.user.UpdateUserModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.userinfo.EditUsrInfoView;
import sunningrain.github.likeshare.util.SharedUtil;

/**
 * Created by 27837 on  2019/4/30.
 */
public class UpdateUserInfoPresenter extends BasePresenter<EditUsrInfoView>{
    private UpdateUserModel mUpdateUserModel = new UpdateUserModel();
    private BaseListener mBaseListener;

    public void setBaseListener(BaseListener baseListener){
        this.mBaseListener = baseListener;
    }

    public void updateUserInfo(String userName,String userSignat) {
        String userPic = SharedUtil.read(Const.User.USER_PIC,"");
        if (prepareInfo(userName)) {
            if (TextUtils.isEmpty(userSignat)){
                userSignat = SharedUtil.read(Const.User.USER_SIGNATURE, "");
            }
            String finalUserSignat = userSignat;
            Disposable disposable = mUpdateUserModel.updateUserBaseInfo(userName, userSignat, userPic)
                    .compose(ResponseTransformer.handleResult())
                    .compose(schedulerProvider.applySchedulers())
                    .subscribe(integer -> {
                                if (integer == 1) {
                                    SharedUtil.save(Const.User.USER_NAME,userName);
                                    SharedUtil.save(Const.User.USER_SIGNATURE, finalUserSignat);
                                    mView.showToast("保存成功");
                                    mView.hideLoading();
                                    mView.updateUserInfo();
                                }
                            }
                            , throwable -> {
                                mView.hideLoading();
                                ApiException apiException = (ApiException) throwable;
                                mView.showToast(apiException.getDisplayMessage());
                            });
            compositeDisposable.add(disposable);
        }
    }

    /**
     * 检查昵称长度
     * @param userName
     * @return
     */
    private boolean prepareInfo(String userName) {
        if (userName == null || userName.length() < 2) {
            mView.showToast("呢称长度太短");
            return false;
        }
        return true;
    }

    /**
     * 检查是否改动了信息
     * @param userName
     * @param userSignat
     * @return
     */
    public boolean checkHasChang(String userName,String userSignat,String tempImg){
        if (userName.equals(SharedUtil.read(Const.User.USER_NAME,""))
                &&userSignat.equals(SharedUtil.read(Const.User.USER_SIGNATURE,""))
        &&tempImg.equals(SharedUtil.read(Const.User.USER_PIC,""))){
            return false;
        }else {
            return true;
        }
    }

    public void uploadPhoto(File file){
        String oldPhotoName = SharedUtil.read(Const.User.USER_PIC,"");
        Log.i("zy",oldPhotoName);
        Disposable disposable = mUpdateUserModel.uploadPhoto(file,oldPhotoName)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(s -> {
                    mBaseListener.success(s);
                },throwable -> {
                    ApiException exception = (ApiException)throwable;
                    mView.showToast(exception.getDisplayMessage());
                    mView.hideLoading();
                });
        compositeDisposable.add(disposable);
    }

}
