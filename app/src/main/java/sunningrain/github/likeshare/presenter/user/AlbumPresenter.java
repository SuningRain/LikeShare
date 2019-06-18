package sunningrain.github.likeshare.presenter.user;

import java.io.File;
import java.util.List;

import sunningrain.github.likeshare.base.BasePresenter;
import sunningrain.github.likeshare.ui.userinfo.ShowAlbumView;
import sunningrain.github.likeshare.util.async.GetAlbumAsyncTask;

/**
 * Created by 27837 on  2019/5/1.
 */
public class AlbumPresenter extends BasePresenter<ShowAlbumView> implements GetAlbumAsyncTask.OnTaskListener{
    /**
     * 获取系统相册图片
     * @return
     */
    public void getAlbumFile() {
        GetAlbumAsyncTask task = new GetAlbumAsyncTask(this);
        task.execute();
    }

    @Override
    public void onSuccess(List<File> fileList) {
        mView.refreshList(fileList);
    }
}
