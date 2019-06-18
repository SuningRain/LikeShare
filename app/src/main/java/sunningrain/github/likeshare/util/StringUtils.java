package sunningrain.github.likeshare.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 27837 on  2019/4/27.
 * 字符串操作类
 */
public class StringUtils {
    /**
     * MD5加密字符串
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
    /**
     * 检查手机号合法性
     * @param phoneNumber
     * @return
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(145,147))\\d{8}$";
        return !TextUtils.isEmpty(phoneNumber) && phoneNumber.matches(telRegex);
    }

    /**
     * 检查密码是否合法
     * @param passWord
     * @return
     */
    public static boolean checkPassWord(String passWord){
        return !TextUtils.isEmpty(passWord);
    }
}
