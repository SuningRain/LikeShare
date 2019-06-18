package sunningrain.github.likeshare.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import sunningrain.github.likeshare.R;
import sunningrain.github.likeshare.base.BaseActivity;
import sunningrain.github.likeshare.base.CreatePresenter;
import sunningrain.github.likeshare.base.PresenterVariable;
import sunningrain.github.likeshare.presenter.user.LoginPresenter;
import sunningrain.github.likeshare.ui.activity.MainActivity;
import sunningrain.github.likeshare.ui.register.RegisterActivity;

@CreatePresenter(presenter = LoginPresenter.class)
public class LoginActivity extends BaseActivity implements LoginView,View.OnClickListener {

    @PresenterVariable
    LoginPresenter mLoginPresenter;
    @BindView(R.id.phone_Edit)
    TextInputEditText phone_Edit;
    @BindView(R.id.et_password)
    TextInputEditText et_password;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.forget_password)
    TextView forget_password;
    @BindView(R.id.immediate_registration)
    TextView immediate_registration;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        forget_password.setOnClickListener(this);
        immediate_registration.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (mProgressBar.getVisibility() == View.GONE)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (mProgressBar.getVisibility() == View.VISIBLE)
            mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_password://忘记密码
                break;
            case R.id.immediate_registration://立即注册
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_login://登录
                String userPhone = Objects.requireNonNull(phone_Edit.getText()).toString();
                String userPwd = Objects.requireNonNull(et_password.getText()).toString();
                mLoginPresenter.login(userPhone,userPwd);
                break;
            default:
                break;
        }
    }

    @Override
    public void jumpToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
