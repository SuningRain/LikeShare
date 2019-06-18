package sunningrain.github.likeshare.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.net.interceptor.MyOkhttpInterceptor;
import sunningrain.github.likeshare.net.request.APIRequest;

/**
 * Created by 27837 on  2019/4/18.
 */
public class NetWorkManager {
    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static volatile APIRequest apiRequest;
    public static NetWorkManager getInstance(){
        if (mInstance == null){
            synchronized(NetWorkManager.class){
                if (mInstance == null){
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }
    private NetWorkManager(){
    }
    public void init(){
        //初始化okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MyOkhttpInterceptor())
                .build();
        //初始化retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    public static APIRequest getApiRequest(){
        if (apiRequest == null) {
            synchronized (APIRequest.class) {
                apiRequest = retrofit.create(APIRequest.class);
            }
        }
        return apiRequest;
    }
}
