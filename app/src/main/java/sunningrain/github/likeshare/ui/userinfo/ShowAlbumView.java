package sunningrain.github.likeshare.ui.userinfo;

import java.io.File;
import java.util.List;

import sunningrain.github.likeshare.base.BaseView;

/**
 * Created by 27837 on  2019/5/1.
 */
public interface ShowAlbumView extends BaseView {
    void refreshList(List<File> fileList);
}
