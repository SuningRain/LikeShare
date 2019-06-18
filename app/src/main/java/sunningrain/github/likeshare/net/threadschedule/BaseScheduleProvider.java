package sunningrain.github.likeshare.net.threadschedule;

import android.support.annotation.NonNull;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

/**
 * Created by 27837 on  2019/4/18.
 */
public interface BaseScheduleProvider {
    @NonNull
    Scheduler computation();
    @NonNull
    Scheduler io();
    @NonNull
    Scheduler ui();
    @NonNull
    <T> ObservableTransformer<T,T> applySchedulers();

}
