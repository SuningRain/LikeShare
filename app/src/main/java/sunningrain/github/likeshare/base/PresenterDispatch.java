package sunningrain.github.likeshare.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by 27837 on 2019/4/10.
 */
public class PresenterDispatch {
    private PresenterProviders mPresenterProviders;

    public PresenterDispatch(PresenterProviders presenterProviders){
        this.mPresenterProviders = presenterProviders;
    }

    public <P extends BasePresenter> void attachView(Context context, Object view){
        PresenterStore presenterStore = mPresenterProviders.getPresenterStore();
        HashMap<String ,P> mMap = presenterStore.getMap();
        for (Map.Entry<String, P> entry:mMap.entrySet()){
            P presenter = entry.getValue();
            if (presenter != null){
                presenter.attachView(context, view);
            }
        }
    }

    public <P extends BasePresenter> void detachView(){
        PresenterStore presenterStore = mPresenterProviders.getPresenterStore();
        HashMap<String ,P> mMap = presenterStore.getMap();
        for (Map.Entry<String, P> entry:mMap.entrySet()){
            P presenter = entry.getValue();
            if (presenter != null){
                presenter.detachView();
            }
        }
    }

    public <P extends BasePresenter> void onCreatePresenter(@Nullable Bundle savedState) {
        PresenterStore store = mPresenterProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onCreatePresenter(savedState);
            }
        }
    }

    public <P extends BasePresenter> void onDestroyPresenter() {
        PresenterStore store = mPresenterProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onDestoryPresenter();
            }
        }
    }

    public <P extends BasePresenter> void onSaveInstanceState(Bundle outState) {
        PresenterStore store = mPresenterProviders.getPresenterStore();
        HashMap<String, P> mMap = store.getMap();
        for (Map.Entry<String, P> entry : mMap.entrySet()) {
            P presenter = entry.getValue();
            if (presenter != null) {
                presenter.onSaveInstanceState(outState);
            }
        }
    }
}
