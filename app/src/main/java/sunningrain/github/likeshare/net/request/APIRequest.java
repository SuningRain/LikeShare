package sunningrain.github.likeshare.net.request;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import sunningrain.github.likeshare.bean.CommentBean;
import sunningrain.github.likeshare.bean.UserBean;
import sunningrain.github.likeshare.bean.response.BaseFollow;
import sunningrain.github.likeshare.bean.response.FollowResponse;
import sunningrain.github.likeshare.bean.response.PublishResponse;
import sunningrain.github.likeshare.bean.response.WorldAndPopularResponse;
import sunningrain.github.likeshare.net.response.BaseResponse;
import sunningrain.github.likeshare.net.response.BaseUserInfo;

/**
 * Created by 27837 on  2019/4/18.
 */
public interface APIRequest {

    /******************************用户个人基本信息模块*****************************************/
    /**
     * 用户注册
     *
     * @param options
     * @return
     */
    @POST("user/register")
    Observable<BaseResponse<UserBean>> register(@Body Map<String, String> options);

    /**
     * 用户登录
     *
     * @param options
     * @return
     */
    @POST("user/login")
    Observable<BaseResponse<UserBean>> login(@Body Map<String, String> options);

    /**
     * 更新用户信息
     *
     * @param options
     * @return
     */
    @PUT("user/update")
    Observable<BaseResponse<Integer>> updateUserBaseInfo(@Body Map<String, String> options);

    //这个是上传用户头像
    @Multipart
    @POST("file/upload/photos")
    Observable<BaseResponse<String>> uploadPhoto(@Part MultipartBody.Part photo, @Part MultipartBody.Part oldPhotoName);

    /*******************************发布模块***********************************/
    //这个是用户发布内容里面的图片
    @Multipart
    @POST("file/upload/pic")
    Observable<BaseResponse<String>> uploadPic(@Part MultipartBody.Part pic);

    @POST("core/publish")
    Observable<BaseResponse<Integer>> publish(@Body Map<String, String> options);

    /*******************************我的关注模块****************************************/
    @GET("core/follow/list")
    Observable<BaseResponse<FollowResponse>> getFollowList(@QueryMap Map<String, Integer> options);

    /*********************************个人中心模块****************************************/
    @GET("core/user/baseInfo")
    Observable<BaseResponse<BaseUserInfo>> getBaseUserInfo(@Query(value = "userId") int userId);

    @GET("core/publish/list")
    Observable<BaseResponse<PublishResponse>> getPublishList(@QueryMap Map<String, Integer> options);

    @GET("core/base/follow/list")
    Observable<BaseResponse<List<BaseFollow>>> getBaseFollowList(@Query(value = "userId") int userId);

    @GET("core/base/followed/list")
    Observable<BaseResponse<List<BaseFollow>>> getBaseFollowedList(@Query(value = "userId") int userId);

    @GET("core/user/follows/list")
    Observable<BaseResponse<List<BaseUserInfo>>> getUserFollows();

    @GET("core/user/fans/list")
    Observable<BaseResponse<List<BaseUserInfo>>> getUserFans();

    /***********************************世界模块*******************************************/

    @GET("core/publish/word/list")
    Observable<BaseResponse<WorldAndPopularResponse>> getWorldAndPopularList(@QueryMap Map<String, Integer> options);

    /************************************详情模块*****************************************************/
    @POST("core/publish/comment")
    Observable<BaseResponse<Integer>> addComment(@Body Map<String, Object> options);

    @GET("core/publish/comment/list")
    Observable<BaseResponse<List<CommentBean>>> getCommentList(@Query(value = "publishId") int publishId);

    @POST("core/follow/add")
    Observable<BaseResponse<Integer>> addFollow(@Body Map<String, Integer> options);

    @POST("core/followed/add")
    Observable<BaseResponse<Integer>> addFollowed(@Body Map<String, Integer> options);
}
