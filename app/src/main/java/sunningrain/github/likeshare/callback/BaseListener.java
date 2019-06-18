package sunningrain.github.likeshare.callback;

/**
 * Created by 27837 on  2019/5/2.
 */
public interface BaseListener {
    void success(String picPath);
    void fail(Throwable throwable);
}
