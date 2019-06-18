package sunningrain.github.likeshare.ui.follow;

import sunningrain.github.likeshare.base.BaseView;
import sunningrain.github.likeshare.bean.response.FollowResponse;

/**
 * Created by 27837 on  2019/5/5.
 */
public interface FollowView extends BaseView {
    void refresh(FollowResponse followResponse);
}
