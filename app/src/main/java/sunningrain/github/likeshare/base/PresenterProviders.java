package sunningrain.github.likeshare.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Create by 27837 on 2019/4/10.
 */
public class PresenterProviders {

    private PresenterStore mPresenterStore = new PresenterStore<>();
    private Activity mActivity;
    private Fragment mFragment;
    private Class<?> mClass;

    public static PresenterProviders inject(Activity activity){
        return new PresenterProviders(activity, null);
    }

    public static PresenterProviders inject(Fragment fragment){
        return new PresenterProviders(null, fragment);
    }

    private PresenterProviders(Activity activity, Fragment fragment){
        if (activity !=null) {
            this.mActivity = activity;
            mClass = this.mActivity.getClass();
        }
        if (fragment != null) {
            this.mFragment = fragment;
            mClass = this.mFragment.getClass();
        }

        resolveCreatePresenter();
        resolvePresenterVariable();
    }

    private static Application checkApplication(Activity activity){
        Application application = activity.getApplication();
        if (application == null){
            throw new IllegalStateException("Your Activity is not yet attached to Application. " +
                    "Your can not request PresenterProviders");
        }
        return application;
    }

    private static Activity checkActivity(Fragment fragment){
        Activity activity = fragment.getActivity();
        if (activity == null){
            throw new IllegalStateException("can't create PresenterProviders for detached fragment");
        }
        return activity;
    }

    public static Context checkContext(Context context){
        Context resultContext = null;
        if (context instanceof Activity){
            resultContext = context;
        }
        if (resultContext == null){
            throw new IllegalStateException("Context must Activity");
        }
        return resultContext;
    }

    private <P extends BasePresenter> PresenterProviders resolveCreatePresenter(){
        CreatePresenter createPresenter = mClass.getAnnotation(CreatePresenter.class);
        if (createPresenter != null){
            Class<P>[] classes = (Class<P>[]) createPresenter.presenter();
            for (Class<P> clazz: classes){
                String canonicaName = clazz.getCanonicalName();
                Log.i("canonicaName",canonicaName);
                try{
                    mPresenterStore.put(canonicaName, clazz.newInstance());
                }catch (InstantiationException e){
                    e.printStackTrace();
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    private <P extends BasePresenter> PresenterProviders resolvePresenterVariable(){
        for (Field field: mClass.getDeclaredFields()){
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1){
                continue;
            }
            if (anns[0] instanceof PresenterVariable){
                String canonicalName = field.getType().getName();
                Log.i("canonicalName",canonicalName);
                P presenterInstance = (P) mPresenterStore.get(canonicalName);
                if (presenterInstance !=null){
                    try{
                        field.setAccessible(true);
                        field.set(mFragment != null?mFragment:mActivity, presenterInstance);
                    }catch (IllegalAccessException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }

    public <P extends BasePresenter> P getPresenter(int index){
        CreatePresenter createPresenter = mClass.getAnnotation(CreatePresenter.class);
        if (createPresenter ==null || createPresenter.presenter().length == 0){
            return null;
        }
        if(index >= 0 && index<createPresenter.presenter().length){
            String key = createPresenter.presenter()[index].getCanonicalName();
            BasePresenter presenter = mPresenterStore.get(key);
            if (presenter !=null){
                return (P) presenter;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public PresenterStore getPresenterStore() {
        return mPresenterStore;
    }
}
