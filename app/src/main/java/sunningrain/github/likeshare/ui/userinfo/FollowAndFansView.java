package sunningrain.github.likeshare.ui.userinfo;

import java.util.List;

import sunningrain.github.likeshare.base.BaseView;
import sunningrain.github.likeshare.net.response.BaseUserInfo;

/**
 * Created by 27837 on  2019/5/8.
 */
public interface FollowAndFansView extends BaseView {
    void showUserFollows(List<BaseUserInfo> baseUserInfos);

    void showUserFans(List<BaseUserInfo> baseUserInfos);
}
