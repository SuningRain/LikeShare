package sunningrain.github.likeshare.presenter.detail;

import android.text.TextUtils;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.model.DetailModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.detail.DetailView;

/**
 * Created by 27837 on  2019/5/16.
 */
public class DetailPresenter extends BasePresenter<DetailView> {
    private DetailModel mDetailModel = new DetailModel();
    public void sendComment(int publishId,int commentUserId,String commentContent) {
        if (TextUtils.isEmpty(commentContent)) {
            mView.showToast("评论内容不能为空");
        } else {
            Disposable disposable = mDetailModel.addComment(publishId, commentUserId, commentContent)
                    .compose(ResponseTransformer.handleResult())
                    .compose(schedulerProvider.applySchedulers())
                    .subscribe(integer -> {
                                mView.refreshRecyclerView(commentContent);
                            }
                            , throwable -> {
                                ApiException exception = (ApiException) throwable;
                                mView.showToast(exception.getDisplayMessage());
                            });
            compositeDisposable.add(disposable);
        }
    }


    public void getCommentList(int publishId){
        Disposable disposable = mDetailModel.getCommentList(publishId)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(commentBeans -> {
                    mView.setData(commentBeans);
                },throwable -> {
                    ApiException exception = (ApiException)throwable;
                    mView.showToast(exception.getDisplayMessage());
                        }
                );
        compositeDisposable.add(disposable);
    }

    public void addFollow(int uId,int clcWho){
        Disposable disposable = mDetailModel.addFollow(uId,clcWho)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(integer -> {
                    mView.showToast("关注成功");
                },throwable -> {
                    ApiException apiException = (ApiException)throwable;
                    mView.showToast(apiException.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }

}
