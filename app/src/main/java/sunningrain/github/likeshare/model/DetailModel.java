package sunningrain.github.likeshare.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import sunningrain.github.likeshare.bean.CommentBean;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;

/**
 * Created by 27837 on  2019/5/16.
 */
public class DetailModel {
    public Observable<BaseResponse<Integer>> addComment(Integer publishId,Integer commentUserId,String commentContent){
        Map<String,Object> map = new HashMap<>();
        map.put("publishId",publishId);
        map.put("commentUserId",commentUserId);
        map.put("commentContent",commentContent);
        return NetWorkManager.getApiRequest().addComment(map);
    }

    public Observable<BaseResponse<List<CommentBean>>> getCommentList(Integer publishId){
        return NetWorkManager.getApiRequest().getCommentList(publishId);
    }

    public Observable<BaseResponse<Integer>> addFollow(int uId,int clcWho){
        Map<String,Integer> map = new HashMap<>();
        map.put("uId",uId);
        map.put("clcWho",clcWho);
        return NetWorkManager.getApiRequest().addFollow(map);
    }

    public Observable<BaseResponse<Integer>> addFollowed(int uId,int clcBeWho){
        Map<String,Integer> map = new HashMap<>();
        map.put("uId",uId);
        map.put("clcBeWho",clcBeWho);
        return NetWorkManager.getApiRequest().addFollowed(map);
    }
}
