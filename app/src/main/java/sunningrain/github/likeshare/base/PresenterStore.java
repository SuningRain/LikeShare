package sunningrain.github.likeshare.base;

import java.util.HashMap;

/**
 * Create by 27837 on 2019/4/10.
 * 这个类用于保存 Presenter,便于管理
 */
public class PresenterStore<P extends BasePresenter> {
    private static final String DEFAULT_KEY = "PresenterStore.DefaultKey";
    private HashMap<String, P> mPresenterMap = new HashMap<>();

    public final void put(String key, P presenter){
        P olderPresenter = mPresenterMap.put(DEFAULT_KEY + key, presenter);
        if (olderPresenter != null){
            olderPresenter.onCleared();
        }
    }

    public final P get(String key){
        return mPresenterMap.get(DEFAULT_KEY+key);
    }

    public final void clear(){
        for (P presenter: mPresenterMap.values()){
            presenter.onCleared();
        }
        mPresenterMap.clear();
    }

    public final int getSize(){
        return mPresenterMap.size();
    }

    public final HashMap<String, P> getMap(){
        return mPresenterMap;
    }
}
