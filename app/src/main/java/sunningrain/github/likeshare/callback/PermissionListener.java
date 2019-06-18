package sunningrain.github.likeshare.callback;

import java.util.List;

/**
 * Created by 27837 on  2019/5/2.
 */
public interface PermissionListener {
    void onGranted();
    void onDenid(List<String> deniedPermissions);
}
