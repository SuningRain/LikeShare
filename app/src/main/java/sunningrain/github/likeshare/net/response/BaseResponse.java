package sunningrain.github.likeshare.net.response;

/**
 * Created by 27837 on  2019/4/18.
 */
public class BaseResponse<T> {
    private int code;//返回的code
    private T data;//具体的数据
    private String message;//可用来返回接口的说明

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
