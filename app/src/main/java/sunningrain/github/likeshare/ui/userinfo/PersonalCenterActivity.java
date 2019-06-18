package sunningrain.github.likeshare.ui.userinfo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.PublishAdapter;
import sunningrain.github.likeshare.adapter.follow.FollowAdapter;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.bean.response.BaseFollow;
import sunningrain.github.likeshare.bean.response.PublishResponse;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.net.response.BaseUserInfo;
import sunningrain.github.likeshare.presenter.user.UserCenterPresenter;
import sunningrain.github.likeshare.ui.activity.MainActivity;
import sunningrain.github.likeshare.util.SharedUtil;

@CreatePresenter(presenter = UserCenterPresenter.class)
public class PersonalCenterActivity extends BaseActivity implements UserCenterView, View.OnClickListener {

    private static final int REQUST_EDITUSERINFOACTIVITY = 100;

    @PresenterVariable
    UserCenterPresenter mUserCenterPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.iv_user_pic)
    ImageView iv_user_pic;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_signature)
    TextView tv_user_signature;
    @BindView(R.id.ll_publish_count)
    LinearLayout ll_publish_count;
    @BindView(R.id.ll_follow_count)
    LinearLayout ll_follow_count;
    @BindView(R.id.ll_fans_count)
    LinearLayout ll_fans_count;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_publish_count)
    TextView tv_publish_count;
    @BindView(R.id.tv_follow_count)
    TextView tv_follow_count;
    @BindView(R.id.tv_followed_count)
    TextView tv_followed_count;


    public static final String FROM_FOLLOW_COUNT = "f_follow_c";
    public static final String FROM_FANS_COUNT = "f_fans_c";
    public static final String FROM_DETAIL_ACTIVITY = "f_detail_activity";
    public static final String FROM_COMMENT_ADAPTER = "f_comment_adapter";
    public static final String FROM = "from";
    public static final String WORD_AND_POPULAR_ADAPTER = "word_and_popular_adapter";
    public static final String DATA = "data";
    private PublishAdapter mPublishAdapter;

    private ArrayList<PublishResponse.DataBean> dataBeanList;
    private BaseUserInfo mBaseUserInfo;
    private ProgressBar progressBar;
    private boolean hasChange = false;
    private int userId = 0;
    private boolean notMe = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_personnel_center;
    }

    @Override
    protected void init() {
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        ll_follow_count.setOnClickListener(this);
        ll_fans_count.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    private void initView() {
        ButterKnife.bind(this);
        setMyActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsing_toolbar.setTitle(SharedUtil.read(Const.User.USER_NAME, "") + " ");
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String from = intent.getStringExtra(FROM);
            if (from != null) {
                switch (from) {
                    case MainActivity.TAG:
                        BaseUserInfo baseUserInfo = new BaseUserInfo();
                        baseUserInfo.setUserName(SharedUtil.read(Const.User.USER_NAME, ""));
                        baseUserInfo.setUserPhoto(SharedUtil.read(Const.User.USER_PIC, ""));
                        baseUserInfo.setUserSignat(SharedUtil.read(Const.User.USER_SIGNATURE, ""));
                        initData(baseUserInfo);
                        userId = SharedUtil.read(Const.Auth.USER_ID, 0);
                        break;
                    case FollowAdapter.TAG:
                        userId = intent.getIntExtra(DATA, 0);
                        break;
                    case WORD_AND_POPULAR_ADAPTER:
                        userId = intent.getIntExtra(DATA, 0);
                        break;
                    case FROM_DETAIL_ACTIVITY:
                        userId = intent.getIntExtra(DATA, 0);
                        break;
                    case FROM_COMMENT_ADAPTER:
                        userId = intent.getIntExtra(DATA, 0);
                        break;
                    default:
                        break;
                }
            }
        }
        if (userId != SharedUtil.read(Const.Auth.USER_ID, 0)) {
            fab.setImageResource(R.drawable.ic_follow);
            notMe = true;
        }
        mUserCenterPresenter.getBaseUserInfo(userId);//获取用户的基本信息
        mUserCenterPresenter.getBaseFollowList(userId);//获取关注的列表
        mUserCenterPresenter.getBaseFollowedList(userId);//获取被关注的列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        dataBeanList = new ArrayList<>();
        mPublishAdapter = new

                PublishAdapter(this, dataBeanList, "", "");
        recycler_view.setAdapter(mPublishAdapter);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (hasChange) {//在当前页面启动了用户信息编辑页面，并修改了个人信息，则需要刷新侧滑页面个人信息
            setResultForFront();
        }
        finish();
    }

    private void setResultForFront() {
        Intent intent = new Intent();
        intent.putExtra("hasChange", true);
        setResult(RESULT_OK, intent);
        hasChange = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (hasChange) {
                    setResultForFront();
                }
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData(BaseUserInfo baseUserInfo) {
        this.mBaseUserInfo = baseUserInfo;
        Glide.with(this)
                .load(Const.BASE_PHOTO_URL + baseUserInfo.getUserPhoto())
                .placeholder(getResources().getDrawable(R.drawable.default_picture))
                .into(iv_user_pic);
        tv_user_name.setText(baseUserInfo.getUserName());
        tv_user_signature.setText(baseUserInfo.getUserSignat());
        mUserCenterPresenter.getPublishList(0, 10, userId);
    }

    @Override
    public void setData(PublishResponse publishResponse) {
        String userName = mBaseUserInfo.getUserName().trim();
        String userPhoto = mBaseUserInfo.getUserPhoto().trim();
        tv_publish_count.setText(String.valueOf(publishResponse.getData().size()));
        mPublishAdapter.refreshData(userId,userName == null ? "" : userName, userPhoto == null ? "" : userPhoto, publishResponse.getData());
    }

    @Override
    public void setBaseFollowInfo(List<BaseFollow> baseFollowInfo) {
        if (baseFollowInfo.isEmpty()) {
            tv_follow_count.setText("0");
        } else {
            tv_follow_count.setText(String.valueOf(baseFollowInfo.size()));
        }
    }

    @Override
    public void setBaseFollowedInfo(List<BaseFollow> baseFollowedInfo) {
        if (baseFollowedInfo.isEmpty()) {
            tv_followed_count.setText("0");
        } else {
            tv_follow_count.setText(String.valueOf(baseFollowedInfo.size()));
        }
    }

    @Override
    public void showLoading() {
        progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_follow_count:
                Intent intent = new Intent(this, FollowAndFansActivity.class);
                intent.putExtra("from", FROM_FOLLOW_COUNT);
                startActivity(intent);
                break;
            case R.id.ll_fans_count:
                Intent intent1 = new Intent(this, FollowAndFansActivity.class);
                intent1.putExtra("from", FROM_FANS_COUNT);
                startActivity(intent1);
                break;
            case R.id.fab:
                if (!notMe) {
                    Intent intent2 = new Intent(this, EditUserInfoActivity.class);
                    startActivityForResult(intent2, REQUST_EDITUSERINFOACTIVITY);
                } else {
                    //加入关注
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUST_EDITUSERINFOACTIVITY:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (data.getBooleanExtra("hasChange", false)) {
                            hasChange = true;
                            userId = SharedUtil.read(Const.Auth.USER_ID, 0);
                            mUserCenterPresenter.getBaseUserInfo(userId);//修改了用户信息后回来需要刷新个人中心界面
                        }
                    }
                }
        }
    }
}
