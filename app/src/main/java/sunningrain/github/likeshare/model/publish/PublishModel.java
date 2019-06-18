package sunningrain.github.likeshare.model.publish;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import sunningrain.github.likeshare.net.NetWorkManager;
import sunningrain.github.likeshare.net.response.BaseResponse;

/**
 * Created by 27837 on  2019/5/3.
 */
public class PublishModel {
    public Observable<BaseResponse<Integer>> publish(String publishPic, String description){
        Map<String,String> map = new HashMap<>();
        map.put("publishPic",publishPic);
        map.put("publishText",description);
        return NetWorkManager.getApiRequest().publish(map);
    }

    public Observable<BaseResponse<String>> uploadPic(File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form_data"),file);
        MultipartBody.Part pic = MultipartBody.Part.createFormData("pic",file.getName(),requestBody);
        return NetWorkManager.getApiRequest().uploadPic(pic);
    }
}
