package sunningrain.github.likeshare.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sunningrain.github.likeshare.global.LikeShare;

/**
 * Created by 27837 on  2019/5/1.
 * 内容提供者工具类，用于获取其他应用程序的共享数据
 */
public class ContentResolverUtil {
    private static final String TAG = "ContentResolverUtil";
    public static List<File> getAlbumFile(){
        List<File> fileList = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = null;
        if (uri!=null){
            ContentResolver contentResolver = LikeShare.getContext().getContentResolver();
            try {
                cursor = contentResolver.query(uri, null, null, null, null);
                if (cursor == null || cursor.getCount() <= 0) {
                    return null;
                }
                while (cursor.moveToNext()) {
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    String path = cursor.getString(index);
                    File file = new File(path);
                    if (file.exists()) {
                        fileList.add(file);
                        Log.i(TAG, path);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (cursor!=null)
                    cursor.close();
            }
        }
        return fileList;
    }


    /**
     * 此方法有问题，直接Uri.fromFile(File(filepath))便可得到Uri
     * @param context
     * @param imageFile
     * @return
     */

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
