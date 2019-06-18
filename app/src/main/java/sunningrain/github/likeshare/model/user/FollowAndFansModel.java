package sunningrain.github.likeshare.model.user;

import java.util.List;

import io.reactivex.Observable;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;
import sunningrain.github.likeshare.net.response.BaseUserInfo;

/**
 * Created by 27837 on  2019/5/8.
 */
public class FollowAndFansModel {
    public Observable<BaseResponse<List<BaseUserInfo>>> getUserFollows(){
        return NetWorkManager.getApiRequest().getUserFollows();
    }

    public Observable<BaseResponse<List<BaseUserInfo>>> getUserFans(){
        return NetWorkManager.getApiRequest().getUserFans();
    }
}
