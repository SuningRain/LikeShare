package sunningrain.github.likeshare.bean;

/**
 * Created by 27837 on  2019/5/3.
 * 点赞的实体类
 */
public class LikeBean {
    private int id;
    private int pbId;
    private int uId;
    private String pbTime;
    private String pbPic;
    private String pbContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPbId() {
        return pbId;
    }

    public void setPbId(int pbId) {
        this.pbId = pbId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getPbTime() {
        return pbTime;
    }

    public void setPbTime(String pbTime) {
        this.pbTime = pbTime;
    }

    public String getPbPic() {
        return pbPic;
    }

    public void setPbPic(String pbPic) {
        this.pbPic = pbPic;
    }

    public String getPbContent() {
        return pbContent;
    }

    public void setPbContent(String pbContent) {
        this.pbContent = pbContent;
    }
}
