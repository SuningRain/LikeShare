package sunningrain.github.likeshare.ui.userinfo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.user.AlbumAdapter;
import sunningrain.github.likeshare.base.BaseFragment;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.presenter.user.AlbumPresenter;
import sunningrain.github.likeshare.ui.userinfo.ShowAlbumView;

/**
 * Created by 27837 on  2019/5/2.
 */
@CreatePresenter(presenter = AlbumPresenter.class)
public class AlbumFragment extends BaseFragment implements AlbumAdapter.OnItemClickListener, ShowAlbumView {
    @PresenterVariable
    AlbumPresenter mAlbumPresenter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<File> mFileList;
    private AlbumAdapter albumAdapter;
    private boolean needCrop = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_album;
    }

    @Override
    protected void init() {
        initView();
        initData();
        setData();
    }

    private void initView() {

    }

    private void initData() {
        String whichView = getArguments().getString("which_view");
        if (whichView != null && whichView.equals("PublishActivity")) {
            needCrop = false;
        } else {
            needCrop = true;
        }
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        mFileList = new ArrayList<>();
        albumAdapter = new AlbumAdapter(mContext, mFileList);
        albumAdapter.setOnItemClickListener(this);
        recyclerview.setAdapter(albumAdapter);
    }

    private void setData() {
        mAlbumPresenter.getAlbumFile();
    }

    @Override
    public void refreshList(List<File> fileList) {
        mFileList.clear();
        mFileList.addAll(fileList);
        albumAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int postion, File file) {
        if (needCrop) {
            Uri fileUri = Uri.fromFile(file);
            CropImage.activity(fileUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setFixAspectRatio(true)
                    .setAspectRatio(1, 1)
                    .setActivityTitle(LikeShare.getContext().getResources().getString(R.string.crop))
                    .setRequestedSize(150, 150)
                    .setCropMenuCropButtonIcon(R.drawable.crop)
                    .start(mActivity);
        }else {
            Intent intent = new Intent();
            intent.putExtra("file",file.getPath());
            mActivity.setResult(Activity.RESULT_OK,intent);
            mActivity.finish();
        }
    }

}
