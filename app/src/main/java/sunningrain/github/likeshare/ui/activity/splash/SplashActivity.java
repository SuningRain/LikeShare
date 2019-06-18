package sunningrain.github.likeshare.ui.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.global.LikeShare;
import sunningrain.github.likeshare.ui.activity.MainActivity;
import sunningrain.github.likeshare.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
        delayStart();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    private void delayStart() {
        Thread delayStart = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!LikeShare.isLogin()){
                    Intent startLoginActivity = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(startLoginActivity);
                    finish();
                }else {
                    Intent startMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(startMainActivity);
                    finish();
                }
            }
        };
        delayStart.start();
    }

    /**
     * 屏蔽返回键
     */
    @Override
    public void onBackPressed() {
    }
}
