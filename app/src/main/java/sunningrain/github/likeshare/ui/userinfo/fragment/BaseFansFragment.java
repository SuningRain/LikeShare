package sunningrain.github.likeshare.ui.userinfo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.BaseUserInfoAdapter;
import sunningrain.github.likeshare.base.BaseFragment;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.net.response.BaseUserInfo;
import sunningrain.github.likeshare.presenter.user.FollowAndFansPresenter;
import sunningrain.github.likeshare.ui.userinfo.FollowAndFansView;

/**
 * Created by 27837 on  2019/5/8.
 */
@CreatePresenter(presenter = FollowAndFansPresenter.class)
public class BaseFansFragment extends BaseFragment implements FollowAndFansView {
    @PresenterVariable
    FollowAndFansPresenter mFollowAndFansPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private ArrayList<BaseUserInfo> baseUserInfos;
    private BaseUserInfoAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_fans;
    }
    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        baseUserInfos = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        adapter = new BaseUserInfoAdapter(mContext,baseUserInfos);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setAdapter(adapter);

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void lazyLoadData() {
        mFollowAndFansPresenter.getFansList();
    }

    @Override
    public void showUserFollows(List<BaseUserInfo> baseUserInfos) {

    }

    @Override
    public void showUserFans(List<BaseUserInfo> baseUserInfos) {
        if (baseUserInfos.isEmpty()) {
            showToast("暂时还没有人关注您");
        } else {
            this.baseUserInfos.clear();
            this.baseUserInfos.addAll(baseUserInfos);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }
}
