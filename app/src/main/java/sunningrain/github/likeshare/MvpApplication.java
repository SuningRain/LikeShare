package sunningrain.github.likeshare;

import android.app.Application;

import org.litepal.LitePal;

import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.net.NetWorkManager;

public class MvpApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
//        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
//        OkHttpFinal.getInstance().init(builder.build());
        LitePal.initialize(this);
        LikeShare.initialize(this);
        NetWorkManager.getInstance().init();
    }
}
