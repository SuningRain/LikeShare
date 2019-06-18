package sunningrain.github.likeshare.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.ui.userinfo.fragment.AlbumFragment;

public class ShowAlbumActivity extends BaseActivity {

    private static final int PHOTO_CUT = 100;
    public  static final String IMAGE_PATH = "image_path";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_show_album;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initView() {
        ButterKnife.bind(this);
        setMyActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("我的相册");
        }
    }

    private void initData() {
        Intent intent = getIntent();
        String whichView = intent.getStringExtra("which_view");
        Bundle bundle = new Bundle();
        bundle.putString("which_view",whichView);
        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_CANCELED) {
                    return;
                }
                Intent intent = new Intent();
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    intent.putExtra(ShowAlbumActivity.IMAGE_PATH,result.getUri().getPath());
                }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Log.i("Cropping failed",result.getError()+" "+result.getError().getMessage());
                }
                setResult(resultCode,intent);
                finish();
                break;
            default:
                break;
        }

    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
