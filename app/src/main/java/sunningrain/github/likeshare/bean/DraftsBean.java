package sunningrain.github.likeshare.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by 27837 on  2019/5/5.
 */
public class DraftsBean extends LitePalSupport implements Serializable {
    private static final long serialVersionUID = -2414005388530700750L;
    private String publishPic;
    private String publishText;
    private String publishTime;

    public String getPublishPic() {
        return publishPic;
    }

    public void setPublishPic(String publishPic) {
        this.publishPic = publishPic;
    }

    public String getPublishText() {
        return publishText;
    }

    public void setPublishText(String publishText) {
        this.publishText = publishText;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
