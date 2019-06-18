package sunningrain.github.likeshare.model.user;

import java.util.HashMap;

import io.reactivex.Observable;
import sunningrain.github.likeshare.bean.UserBean;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;

/**
 * Created by 27837 on  2019/4/18.
 */
public class RegisterModel {
    public Observable<BaseResponse<UserBean>> register(String userPhone, String userPwd){
        HashMap<String,String> map= new HashMap<>();
        map.put("userPhone",userPhone);
        map.put("userPwd",userPwd);
        return NetWorkManager.getApiRequest().register(map);
    }
}
