package sunningrain.github.likeshare.net.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by 27837 on  2019/4/18.
 */
public class CustomException {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1003;
    public static ApiException handleException(Throwable e){
        ApiException ae;
        if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException){
            ae = new ApiException(PARSE_ERROR,e.getMessage());
            return ae;
        }else if (e instanceof ConnectException){
            ae = new ApiException(NETWORK_ERROR,e.getMessage());
            return ae;
        }else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException){
            ae = new ApiException(NETWORK_ERROR,e.getMessage());
            return ae;
        }else {
            ae = new ApiException(UNKNOWN,e.getMessage());
            return ae;
        }
    }
}
