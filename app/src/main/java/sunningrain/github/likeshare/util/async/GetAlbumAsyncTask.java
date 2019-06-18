package sunningrain.github.likeshare.util.async;

import android.os.AsyncTask;

import java.io.File;
import java.util.List;

import sunningrain.github.likeshare.util.ContentResolverUtil;

/**
 * Created by 27837 on  2019/5/1.
 */
public class GetAlbumAsyncTask extends AsyncTask<Void,String, List<File>> {
    private OnTaskListener mOnTaskListener;
    public GetAlbumAsyncTask(OnTaskListener onTaskListener){
        this.mOnTaskListener = onTaskListener;
    }
    public interface OnTaskListener {
        void onSuccess(List<File> fileList);
    }

    @Override
    protected List<File> doInBackground(Void... voids) {
        return ContentResolverUtil.getAlbumFile();
    }

    @Override
    protected void onPostExecute(List<File> fileList) {
        super.onPreExecute();
        mOnTaskListener.onSuccess(fileList);
    }
}
