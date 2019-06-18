package sunningrain.github.likeshare.ui.userinfo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.main.MainFragmentAdapter;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.base.BaseFragment;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.ui.userinfo.fragment.BaseFansFragment;
import sunningrain.github.likeshare.ui.userinfo.fragment.BaseFollowFragment;
import sunningrain.github.likeshare.util.SharedUtil;

public class FollowAndFansActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected int getContentView() {
        return R.layout.activity_follow_and_fans;
    }

    @Override
    protected void init() {
        initView();
        initData();
        setListener();
    }

    private void initView() {
        ButterKnife.bind(this);
        setMyActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(SharedUtil.read(Const.User.USER_NAME, ""));
        }
    }

    private void initData() {
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        List<String> titles = new ArrayList<>();
        titles.add("关注");
        titles.add("粉丝");
        for (int i = 0; i < titles.size(); i++) {
            tabs.addTab(tabs.newTab().setText(titles.get(i)));
            tabs.addTab(tabs.newTab().setText(titles.get(i)));
        }
        BaseFragment baseFollowFragment = new BaseFollowFragment();
        BaseFragment baseFansFragment = new BaseFansFragment();
        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(baseFollowFragment, titles.get(0));
        adapter.addFragment(baseFansFragment, titles.get(1));
        viewpager.setAdapter(adapter);
        tabs.setupWithViewPager(viewpager);
        if (from != null) {
            if (from.equals(PersonalCenterActivity.FROM_FOLLOW_COUNT)) {
                viewpager.setCurrentItem(0);
            } else {
                viewpager.setCurrentItem(1);
            }
        }
    }

    private void setListener() {
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
