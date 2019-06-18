package sunningrain.github.likeshare.model.user;

import java.util.HashMap;

import io.reactivex.Observable;
import sunningrain.github.likeshare.base.BaseModel;
import sunningrain.github.likeshare.bean.UserBean;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;

/**
 * Created by 27837 on  2019/4/16.
 */
public class LoginModel extends BaseModel {
    public Observable<BaseResponse<UserBean>> login(String phoneNumber, String passWord){
        HashMap<String,String> map = new HashMap<>();
        map.put("userPhone",phoneNumber);
        map.put("userPwd",passWord);
        return NetWorkManager.getApiRequest().login(map);
    }
}
