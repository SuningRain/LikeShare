package sunningrain.github.likeshare.presenter.publish;

import android.app.Activity;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.bean.DraftsBean;
import sunningrain.github.likeshare.callback.BaseListener;
import sunningrain.github.likeshare.model.publish.PublishModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.publish.PublishView;

/**
 * Created by 27837 on  2019/5/3.
 */
public class PublishPresenter extends BasePresenter<PublishView> {
    private PublishModel mPublishModel = new PublishModel();
    private BaseListener mBaseListener;
    public void setListener(BaseListener baseListener){
        this.mBaseListener = baseListener;
    }

    /**
     * 保存到草稿箱
     * @param publishPic
     * @param description
     */
    public void saveToDrafts(String publishPic, String description, DraftsBean drafts, Activity activity) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        if (drafts==null) {
            DraftsBean draftsBean = new DraftsBean();
            draftsBean.setPublishPic(publishPic);
            draftsBean.setPublishText(description);
            draftsBean.setPublishTime(time);
            draftsBean.save();
            mView.goToDraftsActivity(publishPic,description);
        }else{
            Log.i("zy","不为空");
            drafts.setPublishPic(publishPic);
            drafts.setPublishText(description);
            drafts.setPublishTime(time);
            drafts.save();
            activity.setResult(Activity.RESULT_OK);
        }

    }

    /**
     * 发布
     * @param publishPic
     * @param description
     */
    public void publish(String publishPic, String description) {
        Disposable disposable = mPublishModel.publish(publishPic,description)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(integer -> {
                    Log.i("zy",integer+"");
                    mView.showToast("发布成功");
                    mView.hideLoading();
                    mView.refreshFollowFragment();
                },throwable -> {
                    ApiException exception = (ApiException)throwable;
                    mView.showToast(exception.getDisplayMessage());
                    mView.hideLoading();
                });
        compositeDisposable.add(disposable);
    }

    public void uploadPic(String picPath){
        mView.showLoading();
        File file = new File(picPath);
        if (file.exists()){
            Disposable disposable = mPublishModel.uploadPic(file)
                    .compose(ResponseTransformer.handleResult())
                    .compose(schedulerProvider.applySchedulers())
                    .subscribe(s -> {
                        mBaseListener.success(s);
                    },throwable -> {
                        ApiException exception = (ApiException)throwable;
                        mView.showToast(exception.getDisplayMessage());
                        mView.showLoading();
                    });
            compositeDisposable.add(disposable);
        }else {
            mView.showToast("文件不存在");
        }
    }
}
