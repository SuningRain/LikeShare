package sunningrain.github.likeshare.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import sunningrain.github.likeshare.R;

public class ContactUtil {
    public static void shareText(Context context, String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, TextUtils.isEmpty(text)?"":text);
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent,context.getResources().getString(R.string.app_list)));
    }
}
