package sunningrain.github.likeshare.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.util.AppUtils;
import sunningrain.github.likeshare.util.SharedUtil;

/**
 * Created by 27837 on  2019/4/27.
 */
public class MyOkhttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.addHeader("appId", AppUtils.getUniqueId());
        builder.addHeader("versionId",AppUtils.getAppVersionCode()+"");
        builder.addHeader("token", SharedUtil.read(Const.Auth.TOKEN,""));
        builder.addHeader("uId",SharedUtil.read(Const.Auth.USER_ID,-1)+"");
        return chain.proceed(builder.build());
    }
}
