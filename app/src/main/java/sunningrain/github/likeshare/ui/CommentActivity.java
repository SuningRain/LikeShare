package sunningrain.github.likeshare.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.CommentAdapter;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.bean.CommentBean;

public class CommentActivity extends BaseActivity {
    public static final String DATA = "data";
    public static final String DATA_FROM = "data_from";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private List<CommentBean> mCommentBeanList;
    private CommentAdapter mCommentAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_comment_activiy;
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
            actionBar.setTitle("所有评论");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        mCommentBeanList = new ArrayList<>();
        mCommentAdapter = new CommentAdapter(this, mCommentBeanList);
        recycler_view.setAdapter(mCommentAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            List<CommentBean> dataBeanList = (List<CommentBean>) intent.getSerializableExtra(DATA);
            if (dataBeanList != null) {
                mCommentBeanList.clear();
                mCommentBeanList.addAll(dataBeanList);
                mCommentAdapter.notifyDataSetChanged();
            }
        }
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
