package sunningrain.github.likeshare.presenter.world;

import io.reactivex.disposables.Disposable;
import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.model.WorldAndPopularModel;
import sunningrain.github.likeshare.net.exception.ApiException;
import sunningrain.github.likeshare.net.response.ResponseTransformer;
import sunningrain.github.likeshare.ui.word.WorldAndPopularView;

/**
 * Created by 27837 on  2019/5/7.
 */
public class WorldAndPopularPresenter extends BasePresenter<WorldAndPopularView> {
    private WorldAndPopularModel mWorldAndPopularModel = new WorldAndPopularModel();
    public void getWorldAndPopularList(int pageNo, int pageSize){
        Disposable disposable = mWorldAndPopularModel.getWorldAndPopularList(pageNo,pageSize)
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(worldResponse -> {
                    mView.setData(worldResponse);
                },throwable -> {
                    mView.hideLoading();
                    ApiException apiException = (ApiException)throwable;
                    mView.showToast(apiException.getDisplayMessage());
                });
        compositeDisposable.add(disposable);
    }
}
