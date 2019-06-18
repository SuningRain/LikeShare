package sunningrain.github.likeshare.model.follow;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import sunningrain.github.likeshare.bean.response.FollowResponse;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;

/**
 * Created by 27837 on  2019/5/5.
 */
public class FollowModel {
    public Observable<BaseResponse<FollowResponse>> getFollowList(int pageNo, int pageSize){
        Map<String,Integer> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        return NetWorkManager.getApiRequest().getFollowList(map);
    }
}
