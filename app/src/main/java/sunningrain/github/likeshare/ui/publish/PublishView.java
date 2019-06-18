package sunningrain.github.likeshare.ui.publish;

import sunningrain.github.likeshare.base.BaseView;

/**
 * Created by 27837 on  2019/5/3.
 */
public interface PublishView extends BaseView {
    void refreshFollowFragment();

    void showAlertDialog();

    void goToDraftsActivity(String publishPic, String description);
}
