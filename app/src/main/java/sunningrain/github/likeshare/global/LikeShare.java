package sunningrain.github.likeshare.global;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import sunningrain.github.likeshare.constant.Const;
import sunningrain.github.likeshare.util.SharedUtil;

/**
 * Created by 27837 on  2019/4/27.
 * 全局的api接口
 */
public class LikeShare {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private static Handler mHandler;

    private static boolean isLogin;

    private static long userId;

    private static String token;

    private static int loginType = -1;

    public static final int IMG_MAX_SIZE = 10 * 1024 * 1024;

    /**
     * 初始化接口。这里会进行应用程序的初始化操作，一定要在代码执行的最开始调用。
     *
     * @param c
     *          Context参数，注意这里要传入的是Application的Context，千万不能传入Activity或者Service的Context。
     */
    public static void initialize(Context c) {
        mContext = c;
        mHandler = new Handler(Looper.getMainLooper());
        refreshLoginState();
    }

    /**
     * 获取全局Context，在代码的任意位置都可以调用，随时都能获取到全局Context对象。
     *
     * @return 全局Context对象。
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 获取创建在主线程上的Handler对象。
     *
     * @return 创建在主线程上的Handler对象。
     */
    public static Handler getHandler() {
        return mHandler;
    }
    /**
     * 判断用户是否已登录。
     *
     * @return 已登录返回true，未登录返回false。
     */
    public static boolean isLogin() {
        return isLogin;
    }

    /**
     * 返回当前应用的包名。
     */
    public static String getPackageName() {
        return mContext.getPackageName();
    }
    /**
     * 获取当前登录用户的id。
     * @return 当前登录用户的id。
     */
    public static long getUserId() {
        return userId;
    }
    /**
     * 获取当前登录用户的token。
     * @return 当前登录用户的token。
     */
    public static String getToken() {
        return token;
    }

    /**
     * 获取当前登录用户的登录类型。
     * @return 当前登录用户登录类型。
     */
    public static int getLoginType() {
        return loginType;
    }
    /**
     * 刷新用户的登录状态。
     */
    public static void refreshLoginState() {
        long u = SharedUtil.read(Const.Auth.USER_ID, 0);
        String t = SharedUtil.read(Const.Auth.TOKEN, "");
        int lt = SharedUtil.read(Const.Auth.LOGIN_TYPE, -1);
        isLogin = u > 0 ;
        if (isLogin) {
            userId = u;
        }
    }
    /**
     * 注销用户登录。
     */
    public static void logout() {
        SharedUtil.clear(Const.Auth.USER_ID);
        SharedUtil.clear(Const.Auth.TOKEN);
        SharedUtil.clear(Const.Auth.LOGIN_TYPE);
        SharedUtil.clear(Const.User.USER_NAME);
        SharedUtil.clear(Const.User.USER_PASSWORD);
        SharedUtil.clear(Const.User.USER_PIC);
        SharedUtil.clear(Const.User.USER_SIGNATURE);
        SharedUtil.clear(Const.User.USER_PHONE);
        LikeShare.refreshLoginState();
    }
}
