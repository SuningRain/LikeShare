package sunningrain.github.likeshare.ui.register;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import sunningrain.github.likeshare.presenter.user.RegisterPresenter;

@CreatePresenter(presenter = RegisterPresenter.class)
public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener {
    @PresenterVariable
    RegisterPresenter mRegisterPresenter;

    @BindView(R.id.til_phoneNumber)
    TextInputLayout til_phoneNumber;
    @BindView(R.id.et_phoneNumber)
    TextInputEditText et_phoneNumber;
    @BindView(R.id.til_passWord)
    TextInputLayout til_passWord;
    @BindView(R.id.et_password)
    TextInputEditText et_password;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.moveOnText)
    TextView moveOnText;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        setListener();
    }
    public void setListener(){
        moveOnText.setOnClickListener(this);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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
    public void jumpToLoginActivity() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.moveOnText:
                String phoneNumber = Objects.requireNonNull(et_phoneNumber.getText()).toString();
                String passWord = Objects.requireNonNull(et_password.getText()).toString();
                mRegisterPresenter.setUser(phoneNumber,passWord);
                break;
        }
    }
}
