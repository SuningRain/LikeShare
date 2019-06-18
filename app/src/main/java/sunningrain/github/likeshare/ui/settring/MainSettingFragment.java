package sunningrain.github.likeshare.ui.settring;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.widget.Toast;

import org.litepal.LitePal;

import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.bean.DraftsBean;
import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.ui.login.LoginActivity;
import sunningrain.github.likeshare.util.AppUtils;

/**
 * Created by 27837 on  2019/5/10.
 */
public class MainSettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Context mContext;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.main_preference);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null)
            return;
        else {
            mContext = getActivity();
        }

        Preference logout = findPreference(getString(R.string.key_logout));
        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                LikeShare.logout();
                logout();
                return true;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(getString(R.string.key_automatic_check_update));
        if (preference instanceof SwitchPreferenceCompat) {
            if (!((SwitchPreferenceCompat) preference).isChecked()) {
                Toast.makeText(LikeShare.getContext(), AppUtils.getString(R.string.check_update_in_about_if_you_need)
                        , Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void logout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setMessage("您确定要退出当前账号吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LikeShare.logout();
                        LitePal.deleteAllAsync(DraftsBean.class).listen(null);
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }).setNegativeButton("取消", null)
                .create();
        alertDialog.show();
    }
}
