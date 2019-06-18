package sunningrain.github.likeshare.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import sunningrain.github.likeshare.callback.PermissionListener;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{
    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;
    private PermissionListener mPermissionListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        mPresenterDispatch.attachView(this,this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        init();
    }

    /**
     * 获得Contentview
     * @return
     */
    protected abstract int getContentView();

    /**
     * 初始化
     */
    protected abstract void init();
    protected P getPresenter(){
        return mPresenterProviders.getPresenter(0);
    }
    public PresenterProviders getPresenterProviders(){
        return mPresenterProviders;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterDispatch.detachView();
    }

    /**
     * 设置显示action，可以为空
     * @param toolbar
     */
    protected void setMyActionBar(Toolbar toolbar){
        if (toolbar !=null) {
            setSupportActionBar(toolbar);
        }else{
            if (getSupportActionBar()!=null) {
                getSupportActionBar().hide();
            }
        }
    }

    protected void handlePermissions(List<String> permissions , PermissionListener permissionListener){
        if (permissions == null){
            return;
        }
        mPermissionListener = permissionListener;
        List<String> requestPermissions = new ArrayList<>();
        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_GRANTED){
                requestPermissions.add(permission);
            }
        }
        if (!requestPermissions.isEmpty()){
            ActivityCompat.requestPermissions(this,requestPermissions.toArray(new String[requestPermissions.size()]),1);
        }else {
            permissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                List<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    String permission = permissions[i];
                    if (!(grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
                        deniedPermissions.add(permission);
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    mPermissionListener.onGranted();
                } else {
                    mPermissionListener.onDenid(deniedPermissions);
                }
            }
        }
    }
}
