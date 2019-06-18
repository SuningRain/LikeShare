package sunningrain.github.likeshare.bean;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 27837 on  2019/5/3.
 * 作品简要信息
 */
public class WorksBean implements Serializable {

    private static final long serialVersionUID = -6422400431099658547L;

    private Integer userId;//作品的发布者
    private String userName;//发布者的呢称
    private String userPic;//发布者的头像地址
    private String worksPic;//作品的图片地址
    private String workDescription;//作品的描述

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getWorksPic() {
        return worksPic;
    }

    public void setWorksPic(String worksPic) {
        this.worksPic = worksPic;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorksBean worksBean = (WorksBean) o;
        return Objects.equals(userId, worksBean.userId) &&
                Objects.equals(userName, worksBean.userName) &&
                Objects.equals(userPic, worksBean.userPic) &&
                Objects.equals(worksPic, worksBean.worksPic) &&
                Objects.equals(workDescription, worksBean.workDescription);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, userPic, worksPic, workDescription);
    }

    @Override
    public String toString() {
        return "WorksBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPic='" + userPic + '\'' +
                ", worksPic='" + worksPic + '\'' +
                ", workDescription='" + workDescription + '\'' +
                '}';
    }
}
