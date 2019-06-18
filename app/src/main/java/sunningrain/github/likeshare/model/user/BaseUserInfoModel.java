package sunningrain.github.likeshare.model.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import sunningrain.github.likeshare.bean.response.BaseFollow;
import sunningrain.github.likeshare.bean.response.PublishResponse;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;
import sunningrain.github.likeshare.net.response.BaseUserInfo;

/**
 * Created by 27837 on  2019/5/6.
 */
public class BaseUserInfoModel {
    public Observable<BaseResponse<BaseUserInfo>> getBaseUserInfo(int userId){
        return NetWorkManager.getApiRequest().getBaseUserInfo(userId);
    }

    public Observable<BaseResponse<PublishResponse>> getPublishList(int pageNo,int pageSize,int userId){
        Map<String,Integer> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("userId",userId);
        return NetWorkManager.getApiRequest().getPublishList(map);
    }

    public Observable<BaseResponse<List<BaseFollow>>> getBaseFollowList(int userId){
        return NetWorkManager.getApiRequest().getBaseFollowList(userId);
    }

    public Observable<BaseResponse<List<BaseFollow>>> getBaseFollowedList(int userId){
        return NetWorkManager.getApiRequest().getBaseFollowedList(userId);
    }
}
