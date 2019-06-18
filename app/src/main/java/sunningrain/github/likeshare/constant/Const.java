package sunningrain.github.likeshare.constant;

/**
 * Created by 27837 on  2019/4/27.
 * 全局的静态常量
 */
public class Const {
    public static final String BASE_URL ="http://182.254.180.91:8080/";
    public static final String BASE_PHOTO_URL =BASE_URL+"file/download/photos/";
    public static final String BASE_PICTURE_URL = BASE_URL+"file/download/pic/";
    public static class Auth{
        public static final String USER_ID = "u_d";//用户id
        public static final String TOKEN = "t_k";//用户token
        public static final String LOGIN_TYPE = "l_t";//登录类型
    }
    public static class User{
        public static final String USER_NAME = "u_n";//用户呢称
        public static final String USER_PASSWORD =  "u_pwd";//密码
        public static final String USER_PIC = "u_photo";//头像
        public static final String USER_SIGNATURE = "u_sig";//个性签名
        public static final String USER_PHONE = "u_phone";//用户电话号码
    }
    public static class TEMPORARY{
        public static final String ALBUM_PIC="a_p";//从相册获取的临时头像
//        public static final String LAST_TIME="la_ti";//用于刷新图片，解决glide图片缓冲问题
    }
}
