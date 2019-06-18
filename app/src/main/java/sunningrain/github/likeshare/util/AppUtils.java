package sunningrain.github.likeshare.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import java.security.NoSuchAlgorithmException;

import sunningrain.github.likeshare.global.LikeShare;

/**
 * Created by 27837 on  2019/4/27.
 */
public class AppUtils {
    /**
     * @return
     */
    public static int getAppVersionCode(){
        PackageManager packageManager =  LikeShare.getContext().getPackageManager();
        int code= 0;
        try{
            PackageInfo info = packageManager.getPackageInfo(LikeShare.getContext().getPackageName(),0);
            code = info.baseRevisionCode;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return  code;
    }

    /**
     * 获取app版本名称
     * @return
     */
    public static String getAppVersionName(){
        PackageManager packageManager = LikeShare.getContext().getPackageManager();
        String name = null;
        try {
            PackageInfo info = packageManager.getPackageInfo(LikeShare.getContext().getPackageName(),0);
            name = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取设备唯一的id，并进行MD5加密
     * @return
     */
    public static String getUniqueId(){
        String androidId = Settings.Secure.getString(LikeShare.getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        String id = androidId+ Build.SERIAL;
        try {
            return StringUtils.toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }

    public static String getString(int id){
        return LikeShare.getContext().getResources().getString(id);
    }
}
