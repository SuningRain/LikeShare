package sunningrain.github.likeshare.ui.word;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.adapter.WordAndPopularAdapter;
import sunningrain.github.likeshare.base.BaseFragment;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.bean.response.WorldAndPopularResponse;
import sunningrain.github.likeshare.callback.listener.OnLoadMoreListener;
import sunningrain.github.likeshare.presenter.world.WorldAndPopularPresenter;

/**
 * Created by 27837 on  2019/5/3.
 */
@CreatePresenter(presenter = WorldAndPopularPresenter.class)
public class WordFragment extends BaseFragment implements WorldAndPopularView{
    @PresenterVariable
    WorldAndPopularPresenter mWorldAndPopularPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    private List<WorldAndPopularResponse.DataBean> dataBeanList ;
    private WordAndPopularAdapter mWordAndPopularAdapter;
    private boolean isLoadMore = false;
    private WorldAndPopularResponse world;
    private RecyclerView.OnScrollListener mOnScrollListener;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_word_popular;
    }

    @Override
    protected void init() {
        initData();
        setListenter();
    }

    private void initData() {
        dataBeanList = new ArrayList<>();
        mWordAndPopularAdapter = new WordAndPopularAdapter(mContext,dataBeanList);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mWordAndPopularAdapter);
    }

    @Override
    public void setData(WorldAndPopularResponse worldAndPopularResponse) {
        world = worldAndPopularResponse;
        if (isLoadMore){
            isLoadMore = false;
        }else{
            dataBeanList.clear();
            swipe_refresh.setRefreshing(false);
        }
        dataBeanList.addAll(worldAndPopularResponse.getData());
        mWordAndPopularAdapter.notifyDataSetChanged();
    }

    private void setListenter() {
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWorldAndPopularPresenter.getWorldAndPopularList(0,10);
            }
        });
    }

    @Override
    protected void lazyLoadData() {
        mOnScrollListener = new OnLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                isLoadMore = true;
                if (world!=null) {
                    if (!world.isIsLastPage()) {
                        mWorldAndPopularPresenter.getWorldAndPopularList((world.getPageNo()+1)*world.getPageSize(),world.getPageSize());
                    }else {
                        isLoadMore = false;
                    }
                }
            }
        };
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mWorldAndPopularPresenter.getWorldAndPopularList(0,10);
    }

    @Override
    public void hideLoading() {
        swipe_refresh.setRefreshing(false);
    }

    @Override
    protected void onInvisible() {
        if (mOnScrollListener!=null) {
            mRecyclerView.removeOnScrollListener(mOnScrollListener);
        }
        isLoadMore = false;
    }
}
