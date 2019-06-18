package sunningrain.github.likeshare.net.response;

/**
 * Created by 27837 on  2019/5/6.
 */
public class BaseUserInfo {

    /**
     * userPhoto : 5_1556952081467.jpg
     * userName : 张雨
     * userSignat : 逆光
     */

    private String userPhoto;
    private String userName;
    private String userSignat;

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSignat() {
        return userSignat;
    }

    public void setUserSignat(String userSignat) {
        this.userSignat = userSignat;
    }
}
