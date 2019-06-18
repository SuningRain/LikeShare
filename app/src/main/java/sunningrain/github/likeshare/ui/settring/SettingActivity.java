package sunningrain.github.likeshare.ui.settring;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;

    @Override
    protected int getContentView() {
        return R.layout.activity_settring;
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
            actionBar.setTitle("设置");
        }
    }

    private void initData() {
        MainSettingFragment fragment = new MainSettingFragment();
        replaceFragment(fragment);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
