package sunningrain.github.likeshare.bean;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 27837 on  2019/4/16.
 * 用户实体类
 */
public class UserBean implements Serializable {

    private Integer id;//用户id
    private String userPhone;//手机号
    private String userName="爱享";//呢称（默认“爱享”）
    private String userPic="";//头像路径
    private String userSignat="";//个性签名
    private String userPwd;//密码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public String getUserSignat() {
        return userSignat;
    }

    public void setUserSignat(String signature) {
        this.userSignat = signature;
    }
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBean userBean = (UserBean) o;
        return Objects.equals(id,userBean.id)&&
                Objects.equals(userPhone, userBean.userPhone) &&
                Objects.equals(userName, userBean.userName) &&
                Objects.equals(userPic, userBean.userPic) &&
                Objects.equals(userSignat, userBean.userSignat) &&
                Objects.equals(userPwd, userBean.userPwd);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id,userPhone, userName, userPic, userSignat,userPwd);
    }

    @Override
    public String toString() {
        return "id:"+id
                +"phoneNumber:"+userPhone
                +",userName:"+userName
                +",userPic:"+userPic
                +",signature:"+userSignat
                +",userPwd:"+userPwd;
    }
}
