package sunningrain.github.likeshare.ui.userinfo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.callback.BaseListener;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.presenter.user.UpdateUserInfoPresenter;
import sunningrain.github.likeshare.util.SharedUtil;

@CreatePresenter(presenter = UpdateUserInfoPresenter.class)
public class EditUserInfoActivity extends BaseActivity implements EditUsrInfoView, View.OnClickListener, BaseListener {

    private static final int PERMISSIONS_REQUEST_OPEN_ALBUM = 101;
    private static final int SELECT_PIC = 100;
    private static final int REQUEST_CROP = 102;

    @PresenterVariable
    UpdateUserInfoPresenter mUpdateUserInfoPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_edit_user_pic)
    RelativeLayout rl_edit_user_pic;
    @BindView(R.id.iv_user_pic)
    CircleImageView iv_user_pic;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_brief_introduction)
    TextView tv_brief_introduction;
    @BindView(R.id.ti_et_user_name)
    TextInputEditText ti_et_user_name;
    @BindView(R.id.ti_et_user_signature)
    TextInputEditText ti_et_user_signature;
    private ProgressDialog progressDialog;
    private boolean photoHasUpdete = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        initView();
        initData();
        setEvent();
    }

    private void initView() {
        setMyActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initData() {
        mUpdateUserInfoPresenter.setBaseListener(this);
        tv_user_name.setText(SharedUtil.read(Const.User.USER_NAME, ""));
        tv_brief_introduction.setText(SharedUtil.read(Const.User.USER_SIGNATURE, ""));
        ti_et_user_name.setText(SharedUtil.read(Const.User.USER_NAME, ""));
        ti_et_user_signature.setText(SharedUtil.read(Const.User.USER_SIGNATURE, ""));
        String imgPath = SharedUtil.read(Const.User.USER_PIC, "");

        setUserImg(imgPath, false);
    }

    private void setEvent() {
        rl_edit_user_pic.setOnClickListener(this);
        ti_et_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_user_name.setText(editable);
            }
        });
        ti_et_user_signature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_brief_introduction.setText(editable);
            }
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_edit_user_pic://点击弹出图片选择框（从相册获取或者相机拍摄）
                showListDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sava_user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String userName = ti_et_user_name.getText().toString().trim();
        String userSignat = ti_et_user_signature.getText().toString().trim();
        String tempImg = SharedUtil.read(Const.TEMPORARY.ALBUM_PIC, Const.User.USER_PIC);
        switch (item.getItemId()) {
            case R.id.save_info:
                if (photoHasUpdete) {
                    File file = new File(SharedUtil.read(Const.TEMPORARY.ALBUM_PIC, ""));
                    if (file.exists()) {
                        showLoading();
                        mUpdateUserInfoPresenter.uploadPhoto(file);
                    } else{
                        showToast("图片路径不存在");
                    }
                } else if (mUpdateUserInfoPresenter.checkHasChang(userName,userSignat,tempImg)){
                    mUpdateUserInfoPresenter.updateUserInfo(userName, userSignat);
                }else {
                    finish();
                }
                break;
            case android.R.id.home:
                checkHasChange(userName, userSignat, tempImg);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当点击返回按钮或者按下返回键时，检查用户信息是否发生了变化
     *
     * @param userName
     * @param userSignat
     */
    private void checkHasChange(String userName, String userSignat, String tempImg) {
        if (mUpdateUserInfoPresenter.checkHasChang(userName, userSignat, tempImg)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("您已经修改了个人信息，确定放弃保存修改吗");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("离开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialog.setNegativeButton("留下", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        } else {
            photoHasUpdete = false;
            finish();
        }
    }

    @Override
    public void updateUserInfo() {
        photoHasUpdete = false;
        Intent intent = new Intent();
        intent.putExtra("hasChange",true);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        String userName = ti_et_user_name.getText().toString().trim();
        String userSignat = ti_et_user_signature.getText().toString().trim();
        String tempImg = SharedUtil.read(Const.TEMPORARY.ALBUM_PIC, Const.User.USER_PIC);
        checkHasChange(userName, userSignat, tempImg);
    }

    private void showListDialog() {
        final String[] items = {"拍照", "我的相册"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        listDialog.setTitle("选择头像图片");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                // ...To-do
                if (items[which].equals("拍照")) {
                    //拍照
                } else if (items[which].equals("我的相册")) {
                    goAlbums();
                }
            }
        });
        listDialog.setCancelable(true);
        listDialog.show();
    }

    /**
     * (调用相册)
     * 修改为获取相册文件并显示
     */
    private void goAlbums() {
        //第二个参数是需要申请的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_OPEN_ALBUM);
        } else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
//            openAlbums();
            startShowAlbumActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_OPEN_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startShowAlbumActivity();
                } else {
                    showToast("您拒绝了该权限");
                }
        }
    }

    private void startShowAlbumActivity() {
        Intent intent = new Intent(this, ShowAlbumActivity.class);
        startActivityForResult(intent, REQUEST_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    String imgPath = null;
                    if (data != null) {
                        imgPath = data.getStringExtra(ShowAlbumActivity.IMAGE_PATH);
                        photoHasUpdete = true;
                    }
                    if (imgPath != null) {
                        setUserImg(imgPath, true);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 设置用户图片
     *
     * @param imgPath
     */
    private void setUserImg(String imgPath, boolean fromResult) {
        Glide.with(this)
                .load(fromResult ? imgPath : Const.BASE_PHOTO_URL + imgPath)
                .placeholder(getResources().getDrawable(R.drawable.default_picture))
                .into(iv_user_pic);
        SharedUtil.save(Const.TEMPORARY.ALBUM_PIC, imgPath);
    }

    @Override
    public void success(String picPath) {
        SharedUtil.save(Const.User.USER_PIC, picPath);
        String userName = ti_et_user_name.getText().toString().trim();
        String userSignat = ti_et_user_signature.getText().toString().trim();
        String tempImg = SharedUtil.read(Const.TEMPORARY.ALBUM_PIC, Const.User.USER_PIC);
        if (mUpdateUserInfoPresenter.checkHasChang(userName, userSignat, tempImg))
            mUpdateUserInfoPresenter.updateUserInfo(userName, userSignat);
    }

    @Override
    public void fail(Throwable throwable) {

    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(EditUserInfoActivity.this);
            progressDialog.setMessage("正在保存，请稍后...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
