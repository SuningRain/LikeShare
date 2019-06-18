package sunningrain.github.likeshare.model.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import sunningrain.github.likeshare.base.BaseModel;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;

/**
 * Created by 27837 on  2019/4/30.
 */
public class UpdateUserModel extends BaseModel {
    public Observable<BaseResponse<Integer>> updateUserBaseInfo(String userName,String userSignat,String userPic){
        Map<String,String> map = new HashMap<>();
        map.put("userName",userName);
        map.put("userSignat",userSignat);
        map.put("userPic",userPic);
        return NetWorkManager.getApiRequest().updateUserBaseInfo(map);
    }

    public Observable<BaseResponse<String>> uploadPhoto(File file, String oldPhotoName){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form_data"),file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo",file.getName(),requestBody);
        MultipartBody.Part oldPhotoName1 = MultipartBody.Part.createFormData("oldPhotoName",oldPhotoName);
        return NetWorkManager.getApiRequest().uploadPhoto(photo,oldPhotoName1);
    }
}
