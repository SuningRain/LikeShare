package sunningrain.github.likeshare.net.threadschedule;

import android.support.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 27837 on  2019/4/18.
 */
public class SchedulerProvider implements BaseScheduleProvider{
    private static SchedulerProvider scheduleProvider;
    public static synchronized SchedulerProvider getInstance(){
        if (scheduleProvider == null){
            scheduleProvider = new SchedulerProvider();
        }
        return scheduleProvider;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable->observable.subscribeOn(io())
                .observeOn(ui());
    }
}
