package sunningrain.github.likeshare.bean.response;

/**
 * Created by 27837 on  2019/5/7.
 */
public class BaseFollow {
    private Integer uId;
    private String publishTime;
    public Integer getuId() {
        return uId;
    }
    public void setuId(Integer uId) {
        this.uId = uId;
    }
    public String getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
