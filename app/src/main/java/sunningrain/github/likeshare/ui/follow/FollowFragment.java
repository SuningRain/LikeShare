package sunningrain.github.likeshare.ui.follow;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.follow.FollowAdapter;
import sunningrain.github.likeshare.base.BaseFragment;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.bean.response.FollowResponse;
import sunningrain.github.likeshare.callback.listener.OnLoadMoreListener;
import sunningrain.github.likeshare.presenter.follow.FollowPresenter;

/**
 * Created by 27837 on  2019/5/3.
 */
@CreatePresenter(presenter = FollowPresenter.class)
public class FollowFragment extends BaseFragment implements FollowView {
    @PresenterVariable
    FollowPresenter mFollowPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<FollowResponse.DataBean> mResponseList;
    private FollowAdapter adapter;
    private FollowResponse follows;
    private boolean isLoadMore = false;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void init() {
        initData();
        setListener();
    }

    private void initData() {
        mResponseList = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new FollowAdapter(mContext, mResponseList);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFollowPresenter.getFollowList(0, 10);
            }
        });
        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                isLoadMore = true;
                if (follows != null) {
                    if (!follows.isIsLastPage()) {
                        mFollowPresenter.getFollowList((follows.getPageNo() + 1) * follows.getPageSize()
                                , follows.getPageSize());
                    }else {
                        isLoadMore = false;
                    }
                }
            }
        });
    }

    @Override
    public void refresh(FollowResponse followResponse) {
        follows = followResponse;
        if (isLoadMore) {
            mResponseList.addAll(followResponse.getData());
            isLoadMore = false;
        } else {
            mResponseList.clear();
            mResponseList.addAll(followResponse.getData());
            mSwipeRefreshLayout.setRefreshing(false);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void lazyLoadData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mFollowPresenter.getFollowList(0, 10);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
    }
}
