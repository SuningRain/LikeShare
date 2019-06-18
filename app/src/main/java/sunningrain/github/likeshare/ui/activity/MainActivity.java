package sunningrain.github.likeshare.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.main.MainFragmentAdapter;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.ui.drafts.DraftsActivity;
import sunningrain.github.likeshare.ui.follow.FollowFragment;
import sunningrain.github.likeshare.ui.like.LikeActivity;
import sunningrain.github.likeshare.ui.popular.PopularFragment;
import sunningrain.github.likeshare.ui.publish.PublishActivity;
import sunningrain.github.likeshare.ui.settring.SettingActivity;
import sunningrain.github.likeshare.ui.userinfo.EditUserInfoActivity;
import sunningrain.github.likeshare.ui.userinfo.PersonalCenterActivity;
import sunningrain.github.likeshare.ui.word.WordFragment;
import sunningrain.github.likeshare.util.SharedUtil;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_START_EDIT_USER_INO_ACTIVITY = 100;
    private static final int FAB_REQUEST_START_PUBLISH_ACTIVITY = 101;
    private static final int NAV_START_PUBLISH_ACTIVITY = 102;
    private static final int REQUEST_USERCENTER = 103;
    public static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigation_view;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.tabs)
    TabLayout tabs;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout ll_headlayout;
    private RelativeLayout rl_user_center;
    private CircleImageView iv_user_pic;
    private TextView tv_user_signature;
    private LinearLayout ll_edit_user_info;
    private TextView tv_user_name;
    private MainFragmentAdapter mHomeFragmentAdapter;
    private SimpleTarget mSimpleTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        initView();
        initData();
        bindEvent();
    }

    private void initData() {
        mSimpleTarget = new SimpleTarget<Bitmap>(400, 400) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                iv_user_pic.setImageBitmap(resource);
                //设置侧滑页面头部背景（根据用户头像设置背景色）
                setNavigationHeadBackground(resource);
            }
        };
        //侧滑布局设置
        setDrawerLayout();
        setViewPager();
        //渲染界面
        rendingInterface();
    }

    private void rendingInterface() {
        tv_user_name.setText(SharedUtil.read(Const.User.USER_NAME, ""));
        tv_user_signature.setText(SharedUtil.read(Const.User.USER_SIGNATURE, ""));
        String uImage = SharedUtil.read(Const.User.USER_PIC, "");
        Log.i("zy", uImage);
        setUserImg(uImage);
    }

    private void setViewPager() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("世界");
        titles.add("关注");
        titles.add("热门");
        for (int i = 0; i < titles.size(); i++) {
            tabs.addTab(tabs.newTab().setText(titles.get(i)));
        }
        WordFragment wordFragment = new WordFragment();//最新
        FollowFragment followFragment = new FollowFragment();//我的关注
        PopularFragment popularFragment = new PopularFragment();//最热
        mHomeFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mHomeFragmentAdapter.addFragment(wordFragment, titles.get(0));
        mHomeFragmentAdapter.addFragment(followFragment, titles.get(1));
        mHomeFragmentAdapter.addFragment(popularFragment, titles.get(2));
        viewPager.setAdapter(mHomeFragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabsFromPagerAdapter(mHomeFragmentAdapter);
    }

    private void setDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
    }

    protected void initView() {
        ll_headlayout = (LinearLayout) navigation_view.inflateHeaderView(R.layout.drawer_layout_nav_header);
        rl_user_center = (RelativeLayout) ll_headlayout.findViewById(R.id.rl_user_center);
        iv_user_pic = (CircleImageView) ll_headlayout.findViewById(R.id.iv_user_pic);
        tv_user_name = (TextView) ll_headlayout.findViewById(R.id.tv_user_name);
        tv_user_signature = (TextView) ll_headlayout.findViewById(R.id.tv_user_signature);
        ll_edit_user_info = (LinearLayout) ll_headlayout.findViewById(R.id.ll_edit_user_info);
        //actionBar设置
        setMyActionBar(toolbar);

    }

    /**
     * 根据头像取背景色
     */
    private void setNavigationHeadBackground(Bitmap bitmap) {
        if (bitmap == null)
            return;
        Palette.from(bitmap).generate(palette -> {
            Palette.Swatch swatch = null;
            if (palette != null) {
                swatch = palette.getVibrantSwatch();
                if (swatch == null) {
                    for (Palette.Swatch swatch1 : palette.getSwatches()) {
                        swatch = swatch1;
                        break;
                    }
                }
            }
            int rgb = 0;
            if (swatch != null) {
                rgb = swatch.getRgb();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ll_headlayout.setBackground(new ColorDrawable(rgb));
            }
        });
    }

    private void bindEvent() {
        iv_user_pic.setOnClickListener(this);
        drawerLayout.addDrawerListener(mDrawerToggle);
        navigation_view.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(this);
        ll_edit_user_info.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(Gravity.START);
        switch (menuItem.getItemId()) {
            case R.id.nav_publish_content://跳转到发布界面
                startPublishActivity(NAV_START_PUBLISH_ACTIVITY);
                break;
            case R.id.nav_my_collection://跳转到我的收藏页面
                break;
            case R.id.nav_my_favorites://跳转到我的点赞界面
                Intent like = new Intent(this, LikeActivity.class);
                this.startActivity(like);
                break;
            case R.id.nav_drafts://跳转到草稿箱界面
                Intent intent = new Intent(this, DraftsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_user_center://跳转到用户中心界面
                Intent intent1 = new Intent(this, PersonalCenterActivity.class);
                intent1.putExtra(PersonalCenterActivity.FROM,TAG);
                startActivityForResult(intent1, REQUEST_USERCENTER);
                break;
            case R.id.nav_setting://跳转到设置界面
                Intent intent2 = new Intent(this, SettingActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }

    private void startPublishActivity(int requestCode) {
        Intent intent = new Intent(this, PublishActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startPublishActivity(FAB_REQUEST_START_PUBLISH_ACTIVITY);
                break;
            case R.id.ll_edit_user_info:
                Intent intent = new Intent(this, EditUserInfoActivity.class);
                startActivityForResult(intent, REQUEST_START_EDIT_USER_INO_ACTIVITY);
                break;
            case R.id.iv_user_pic:
                Intent intent1 = new Intent(this, PersonalCenterActivity.class);
                intent1.putExtra(PersonalCenterActivity.FROM,TAG);
                startActivityForResult(intent1, REQUEST_USERCENTER);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_START_EDIT_USER_INO_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    tv_user_name.setText(SharedUtil.read(Const.User.USER_NAME, ""));
                    tv_user_signature.setText(SharedUtil.read(Const.User.USER_SIGNATURE, ""));
                    setUserImg(SharedUtil.read(Const.User.USER_PIC, ""));
//                setUserImg(SharedUtil.read(Const.User.USER_PIC,""));
                }
            case FAB_REQUEST_START_PUBLISH_ACTIVITY:
                viewPager.setCurrentItem(1);
                break;
            case NAV_START_PUBLISH_ACTIVITY:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                viewPager.setCurrentItem(1);
                break;
            case REQUEST_USERCENTER:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (data.getBooleanExtra("hasChange", false)) {
                            Glide.with(this)
                                    .asBitmap()
                                    .load(Const.BASE_PHOTO_URL + SharedUtil.read(Const.User.USER_PIC, ""))
                                    .into(mSimpleTarget);
                            tv_user_name.setText(SharedUtil.read(Const.User.USER_NAME, ""));
                            tv_user_signature.setText(SharedUtil.read(Const.User.USER_SIGNATURE, ""));
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    /**
     * 设置用户头像
     *
     * @param imageUrl
     */
    private void setUserImg(String imageUrl) {
        Glide.with(this)
                .asBitmap()
                .load(Const.BASE_PHOTO_URL + imageUrl)
                .placeholder(getResources().getDrawable(R.drawable.default_picture))
                .into(mSimpleTarget);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showToast("搜索");
                break;
            default:
                break;
        }
        return true;
    }

}
