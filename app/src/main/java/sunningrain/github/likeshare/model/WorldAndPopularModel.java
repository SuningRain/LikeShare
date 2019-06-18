package sunningrain.github.likeshare.model;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import sunningrain.github.likeshare.bean.response.WorldAndPopularResponse;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;

/**
 * Created by 27837 on  2019/5/7.
 */
public class WorldAndPopularModel {
    public Observable<BaseResponse<WorldAndPopularResponse>> getWorldAndPopularList(int pageNo, int pageSize){
        Map<String,Integer> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        return NetWorkManager.getApiRequest().getWorldAndPopularList(map);
    }
}
