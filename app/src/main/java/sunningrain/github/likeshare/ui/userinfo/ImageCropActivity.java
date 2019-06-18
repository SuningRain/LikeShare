package sunningrain.github.likeshare.ui.userinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.base.BaseActivity;

/**
 * 该界面已经弃用
 */
public class ImageCropActivity extends BaseActivity {

    private static final int REQUEST_CAMERA = 100;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cropImageView)
    CropImageView mCropImageView;
    private Bitmap cropped;
    private Uri uri;
    private String filepath;

    @Override
    protected int getContentView() {
        return R.layout.activity_image_crop;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        setMyActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("剪裁");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        uri = intent.getParcelableExtra("uri");
        filepath = intent.getStringExtra("filepath");
        Log.i("zy",filepath);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
        }else {
            cropImage(filepath);
        }
// or (must subscribe to async event using cropImageView.setOnGetCroppedImageCompleteListener(listener))
//        mCropImageView.getCroppedImageAsync( 400, 400);
    }

    private void cropImage(String filepath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        if (bitmap!=null){
            Log.i("zy",bitmap.getWidth()+"");
        }
        mCropImageView.setImageBitmap(bitmap);
//        cropped = mCropImageView.getCroppedImage();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crop:
                Intent intent = new Intent(this,EditUserInfoActivity.class);
                startActivity(intent);
                finish();
                break;
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_CAMERA:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    cropImage(filepath);
                }else {
                    showToast("您拒绝了该权限");
                }
        }
    }
}
