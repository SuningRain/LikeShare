package sunningrain.github.likeshare.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.CommentAdapter;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.bean.CommentBean;
import sunningrain.github.likeshare.bean.response.FollowResponse;
import sunningrain.github.likeshare.bean.response.WorldAndPopularResponse;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.presenter.detail.DetailPresenter;
import sunningrain.github.likeshare.ui.CommentActivity;
import sunningrain.github.likeshare.ui.userinfo.PersonalCenterActivity;
import sunningrain.github.likeshare.util.ContactUtil;
import sunningrain.github.likeshare.util.SharedUtil;

@CreatePresenter(presenter = DetailPresenter.class)
public class DetailActivity extends BaseActivity implements View.OnClickListener, DetailView {

    public static final String DATA = "data";
    public static final String FROM = "from";
    public static final String FollowAdapter = "followAdapter";
    public static final String WORD_AND_POPULAR_ADAPTER = "word_and_popular_adapter";
    public static final String FROM_PUBLISH_ADAPTER = "from_publish_adapter";
    public static final String DATA_BEAN = "data_bean";
    public static final String DATA_BEAN1 = "data_bean_1";
    @PresenterVariable
    DetailPresenter mDetailPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_publish_pic)
    ImageView iv_publish_pic;
    @BindView(R.id.tv_publish_text)
    TextView tv_publish_text;
    @BindView(R.id.tv_like_count)
    TextView tv_like_count;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.civ_user_pic)
    CircleImageView civ_user_pic;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_publish_time)
    TextView tv_publish_time;
    @BindView(R.id.follow)
    ImageButton follow;
    @BindView(R.id.iv_like)
    ImageView iv_like;
    @BindView(R.id.iv_forward)
    ImageView iv_forward;
    @BindView(R.id.iv_comment)
    ImageView iv_comment;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.civ_my_photo)
    CircleImageView civ_my_photo;
    @BindView(R.id.rl_user_info)
    RelativeLayout rl_user_info;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.bt_send_comment)
    Button bt_send_comment;
    @BindView(R.id.tv_show_all_comment)
    TextView tv_show_all_comment;
    private boolean likeFlag = false;
    private WorldAndPopularResponse.DataBean mDataBean1;
    private FollowResponse.DataBean mDataBean;
    private int flag = -1;
    private List<CommentBean> mCommentBeanList;
    private List<CommentBean> mCommentBeanList2;
    private CommentAdapter commentAdapter;
    int userId = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_detail2;
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
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCommentBeanList = new ArrayList<>();
        mCommentBeanList2 = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, mCommentBeanList);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setAdapter(commentAdapter);
        recycler_view.setNestedScrollingEnabled(false);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String from = intent.getStringExtra(FROM);
            if (from != null) {
                switch (from) {
                    case FollowAdapter:
                        flag = 0;
                        mDataBean =
                                (FollowResponse.DataBean) intent.getSerializableExtra(DATA);
                        userId = mDataBean.getUId();
                        if (mDataBean != null) {
                            initData(mDataBean);
                        }
                        break;
                    case WORD_AND_POPULAR_ADAPTER:
                        flag = 1;
                        mDataBean1 =
                                (WorldAndPopularResponse.DataBean) intent.getSerializableExtra(DATA);
                        userId = mDataBean1.getUId();
                        if (mDataBean1 != null) {
                            initData(mDataBean1);
                        }
                        break;
                    case FROM_PUBLISH_ADAPTER:
                        flag = 2;
                        mDataBean =
                                (FollowResponse.DataBean) intent.getSerializableExtra(DATA);
                        userId = mDataBean.getUId();
                        if (mDataBean != null) {
                            initData(mDataBean);
                        }
                        break;
                    default:
                        break;
                }
            }
            if (userId == SharedUtil.read(Const.Auth.USER_ID, 0)) {
                follow.setVisibility(View.GONE);
            } else {
                follow.setVisibility(View.VISIBLE);
            }
        }
        if (flag == 0) {
            if (mDataBean != null) {
                mDetailPresenter.getCommentList(mDataBean.getPublishId());
            }
        } else if (flag == 1) {
            if (mDataBean1 != null) {
                mDetailPresenter.getCommentList(mDataBean1.getPublishId());
            }
        }
    }

    private void initData(WorldAndPopularResponse.DataBean dataBean1) {
        Glide.with(this)
                .load(Const.BASE_PICTURE_URL + dataBean1.getPublishPic())
                .into(iv_publish_pic);
        tv_publish_text.setText(dataBean1.getPublishText());
        Glide.with(this)
                .load(Const.BASE_PHOTO_URL + dataBean1.getUPhoto())
                .placeholder(getResources().getDrawable(R.drawable.default_picture))
                .into(civ_user_pic);
        tv_user_name.setText(dataBean1.getUName());
        tv_publish_time.setText(dataBean1.getPublishTime());
        Glide.with(this)
                .load(Const.BASE_PHOTO_URL + SharedUtil.read(Const.User.USER_PIC, ""))
                .placeholder(getResources().getDrawable(R.drawable.default_picture))
                .into(civ_my_photo);
    }

    private void initData(FollowResponse.DataBean dataBean) {
        Glide.with(this)
                .load(Const.BASE_PICTURE_URL + dataBean.getPublishPic())
                .into(iv_publish_pic);
        tv_publish_text.setText(dataBean.getPublishText());
        Glide.with(this)
                .load(Const.BASE_PHOTO_URL + dataBean.getUPhoto())
                .placeholder(getResources().getDrawable(R.drawable.default_picture))
                .into(civ_user_pic);
        tv_user_name.setText(dataBean.getUName());
        tv_publish_time.setText(dataBean.getPublishTime());
        Glide.with(this)
                .load(Const.BASE_PHOTO_URL + SharedUtil.read(Const.User.USER_PIC, ""))
                .placeholder(getResources().getDrawable(R.drawable.default_picture))
                .into(civ_my_photo);
    }

    private void setListener() {
        follow.setOnClickListener(this);
        iv_like.setOnClickListener(this);
        iv_comment.setOnClickListener(this);
        bt_send_comment.setOnClickListener(this);
        tv_show_all_comment.setOnClickListener(this);
        rl_user_info.setOnClickListener(this);
        fab.setOnClickListener(this);
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.follow:
                if (flag == 0 || flag == 2) {
                    mDetailPresenter.addFollow(SharedUtil.read(Const.Auth.USER_ID, 0),mDataBean.getUId());
                }else if (flag == 1){
                    mDetailPresenter.addFollow(SharedUtil.read(Const.Auth.USER_ID, 0),mDataBean1.getUId());
                }
                break;
            case R.id.iv_like:
                likeFlag = !likeFlag;
                if (likeFlag) {
                    iv_like.setBackgroundResource(R.drawable.ic_liked);
                } else {
                    iv_like.setBackgroundResource(R.drawable.ic_like);
                }
                break;
            case R.id.iv_comment:
                et_comment.setFocusable(true);
                et_comment.setFocusableInTouchMode(true);
                et_comment.post(new Runnable() {
                    @Override
                    public void run() {
                        et_comment.requestFocus();
                    }
                });
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.showSoftInput(et_comment, 0);
                }
                break;
            case R.id.bt_send_comment:
                String commentContent = et_comment.getText().toString().trim();
                if (flag == 0) {
                    if (mDataBean != null) {
                        mDetailPresenter.sendComment(mDataBean.getPublishId(), SharedUtil.read(Const.Auth.USER_ID,0), commentContent);
                    }
                } else if (flag == 1) {
                    if (mDataBean1 != null) {
                        mDetailPresenter.sendComment(mDataBean1.getPublishId(), SharedUtil.read(Const.Auth.USER_ID,0), commentContent);
                    }
                }
                break;
            case R.id.tv_show_all_comment:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra(CommentActivity.DATA, (Serializable) mCommentBeanList2);
                startActivity(intent);
                break;
            case R.id.rl_user_info:
                Intent intent1 = new Intent(this, PersonalCenterActivity.class);
                intent1.putExtra(PersonalCenterActivity.FROM, PersonalCenterActivity.FROM_DETAIL_ACTIVITY);
                intent1.putExtra(PersonalCenterActivity.DATA, userId);
                startActivity(intent1);
                break;
            case R.id.fab:
                ContactUtil.shareText(this,tv_publish_text.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshRecyclerView(String commentContent) {
        CommentBean commentBean = new CommentBean();
        commentBean.setuId(SharedUtil.read(Const.Auth.USER_ID, 0));
        commentBean.setUserName(SharedUtil.read(Const.User.USER_NAME, ""));
        commentBean.setUserPic(SharedUtil.read(Const.User.USER_PIC, ""));
        commentBean.setCommentContent(commentContent);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        commentBean.setCommentTime(simpleDateFormat.format(new Date()));
        mCommentBeanList.add(commentBean);
        mCommentBeanList2.add(commentBean);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void setData(List<CommentBean> commentBeanList) {
        if (commentBeanList.isEmpty()) {
            tv_show_all_comment.setVisibility(View.GONE);
        } else {
            tv_show_all_comment.setVisibility(View.VISIBLE);
            mCommentBeanList.clear();
            mCommentBeanList2.addAll(commentBeanList);
            mCommentBeanList.addAll(commentBeanList.subList(0,commentBeanList.size()>=3?3:commentBeanList.size()));
            commentAdapter.notifyDataSetChanged();
        }
    }
}
