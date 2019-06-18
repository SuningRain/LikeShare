package sunningrain.github.likeshare.ui.userinfo;

import java.util.List;

import sunningrain.github.likeshare.base.BaseView;
import sunningrain.github.likeshare.bean.response.BaseFollow;
import sunningrain.github.likeshare.bean.response.PublishResponse;
import sunningrain.github.likeshare.net.response.BaseUserInfo;

/**
 * Created by 27837 on  2019/5/6.
 */
public interface UserCenterView extends BaseView {
    void initData(BaseUserInfo baseUserInfo);

    void setData(PublishResponse publishResponse);

    void setBaseFollowInfo(List<BaseFollow> baseFollowInfo);

    void setBaseFollowedInfo(List<BaseFollow> baseFollowedInfo);
}
