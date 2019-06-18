package sunningrain.github.likeshare.ui.publish;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.bean.DraftsBean;
import sunningrain.github.likeshare.callback.BaseListener;
import sunningrain.github.likeshare.presenter.publish.PublishPresenter;
import sunningrain.github.likeshare.ui.drafts.DraftsActivity;
import sunningrain.github.likeshare.ui.userinfo.ShowAlbumActivity;

@CreatePresenter(presenter = PublishPresenter.class)
public class PublishActivity extends BaseActivity implements View.OnClickListener,PublishView, BaseListener {

    private static final int REQUEST_AlBUM_ACTIVITY = 100;
    @PresenterVariable
    PublishPresenter mPublishPresent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_publish_pic)
    ImageView iv_publish_pic;
    @BindView(R.id.et_publish_description)
    TextView et_publish_description;
    private String publishPic;//发布的图片的路径
    private ProgressDialog progressDialog;
    private DraftsBean mDraftsBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_publish;
    }

    @Override
    protected void init() {
        initView();
        initData();
        setEvent();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            int a =  intent.getIntExtra("data",-1);
            if (a>=0 ){
                mDraftsBean = LitePal.findAll(DraftsBean.class).get(a);
                publishPic = mDraftsBean.getPublishPic();
                Glide.with(this).load(publishPic)
                        .placeholder(getResources().getDrawable(R.drawable.default_picture))
                        .into(iv_publish_pic);
                et_publish_description.setText(mDraftsBean.getPublishText());
            }
        }
        mPublishPresent.setListener(this);
    }

    private void setEvent() {
        iv_publish_pic.setOnClickListener(this);
    }

    private void initView() {
        ButterKnife.bind(this);
        setMyActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                checkData();//检查是否有添加内容
                break;
            case R.id.publish://发布
                String description = et_publish_description.getText().toString().trim();
                if (publishPic!=null && !TextUtils.isEmpty(description))
                    mPublishPresent.uploadPic(publishPic);//先上传图片，成功后再更新上传信息
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_publish_pic://点击图片
                Intent intent = new Intent(this, ShowAlbumActivity.class);
                intent.putExtra("which_view","PublishActivity");
                startActivityForResult(intent, REQUEST_AlBUM_ACTIVITY);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_AlBUM_ACTIVITY:
                if (resultCode == RESULT_OK){
                    if (data != null) {
                        String filePath  = data.getStringExtra("file");
                        File file = new File(filePath);
                        if (file.exists()) {
                            publishPic = filePath;
                            Glide.with(this).load(data.getStringExtra("file"))
                                    .placeholder(getResources().getDrawable(R.drawable.default_picture))
                                    .into(iv_publish_pic);
                        }else {
                            Glide.with(this).load(R.drawable.default_picture).into(iv_publish_pic);
                            showToast("文件不存在");
                        }
                    }else{
                        showToast("数据异常");
                    }
                }
        }
    }

    @Override
    public void onBackPressed() {
        checkData();
    }

    private void checkData() {
        String description = et_publish_description.getText().toString().trim();
        if (publishPic!=null || !TextUtils.isEmpty(description))
            showAlertDialog();
        else{
            finish();
        }
    }

    @Override
    public void refreshFollowFragment() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("是否保存到草稿箱");
        dialog.setCancelable(true);
        dialog.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String description = et_publish_description.getText().toString().trim();
                if (publishPic!=null || !TextUtils.isEmpty(description)) {
                    mPublishPresent.saveToDrafts(publishPic,description,mDraftsBean,PublishActivity.this);
                    finish();
                }
            }
        });
        dialog.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDraftsBean!=null){
                    mDraftsBean.delete();
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void goToDraftsActivity(String publishPic, String description) {
        Intent intent = new Intent(this, DraftsActivity.class);
        startActivity(intent);
    }

    @Override
    public void success(String picPath) {
        showToast("图片上传成功");
        String description = et_publish_description.getText().toString().trim();
        mPublishPresent.publish(picPath,description);
    }

    @Override
    public void fail(Throwable throwable) {
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在上传，请稍等..,");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
