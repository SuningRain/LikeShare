package sunningrain.github.likeshare.bean;

import java.io.Serializable;

/**
 * Created by 27837 on  2019/5/3.
 * 评论的实体类
 */
public class CommentBean implements Serializable {
    private static final long serialVersionUID = 3118904537587064926L;
    private Integer uId;//评论人的id
    private String commentTime;//作品的评论时间
    private String commentContent;//评论的内容
    private String userName;//评论人的姓名
    private String userPic;//评论人的头像
    public Integer getuId() {
        return uId;
    }
    public void setuId(Integer uId) {
        this.uId = uId;
    }
    public String getCommentTime() {
        return commentTime;
    }
    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
    public String getCommentContent() {
        return commentContent;
    }
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
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
}
