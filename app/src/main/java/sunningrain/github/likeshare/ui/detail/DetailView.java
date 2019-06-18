package sunningrain.github.likeshare.ui.detail;

import java.util.List;

import sunningrain.github.likeshare.base.BaseView;
import sunningrain.github.likeshare.bean.CommentBean;

/**
 * Created by 27837 on  2019/5/16.
 */
public interface DetailView extends BaseView {
    void refreshRecyclerView(String commentContent);

    void setData(List<CommentBean> commentBeanList);
}
