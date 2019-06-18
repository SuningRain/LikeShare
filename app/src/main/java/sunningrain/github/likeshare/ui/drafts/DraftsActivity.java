package sunningrain.github.likeshare.ui.drafts;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.DraftsAdapter;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.bean.DraftsBean;
import sunningrain.github.likeshare.ui.publish.PublishActivity;

public class DraftsActivity extends BaseActivity implements DraftsAdapter.OnItemClickListener{

    private static final int START_PUBLISH_ACTIVITY = 100;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private DraftsAdapter mDraftsAdapter;
    private List<DraftsBean> draftsBeanList;

    @Override
    protected int getContentView() {
        return R.layout.activity_dustbin;
    }

    @Override
    protected void init() {
        initView();
        initData();
        setListener();
//        setData();
    }

    private void initView() {
        ButterKnife.bind(DraftsActivity.this);
        setMyActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        draftsBeanList= new ArrayList<>();
        draftsBeanList.addAll(LitePal.findAll(DraftsBean.class));
        mDraftsAdapter = new DraftsAdapter(this,draftsBeanList);
        recycler_view.setAdapter(mDraftsAdapter);
    }

    private void setListener() {
        mDraftsAdapter.setOnItemClickListener(this);
    }
    private void setData() {
//        draftsBeanList.clear();
//        draftsBeanList.addAll(LitePal.findAll(DraftsBean.class));
        mDraftsAdapter.refresh(LitePal.findAll(DraftsBean.class));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, PublishActivity.class);
        intent.putExtra("data", position);
        startActivityForResult(intent,START_PUBLISH_ACTIVITY);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case START_PUBLISH_ACTIVITY:
                if (resultCode==RESULT_OK){
                    setData();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
