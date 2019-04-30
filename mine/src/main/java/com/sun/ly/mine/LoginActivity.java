package com.sun.ly.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sun.lib.net.mvp.activity.MvpAppCompatActivity;
import com.sun.lib.net.mvp.root.IMvpPresenter;
import com.sun.ly.baselib.mvp.presenter.LoginPresenter;
import com.sun.ly.baselib.mvp.view.ILoginView;
import com.sun.view.weight.utils.LogUtil;

import androidx.annotation.Nullable;

public class LoginActivity extends MvpAppCompatActivity implements ILoginView {
    private LoginPresenter presenter = new LoginPresenter(this);

    private EditText et_username, et_pswd;
    private Button btn;

    @Override
    protected IMvpPresenter[] getPresenterArray() {
        return new IMvpPresenter[]{presenter};
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        et_username = findViewById(R.id.a_login_user);
        et_pswd = findViewById(R.id.a_login_pswd);
        btn = findViewById(R.id.a_login_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getLoginData(et_username.getText().toString(), et_pswd.getText().toString());
            }
        });
    }

    @Override
    public void mvpLoading(String action, boolean show) {

    }

    @Override
    public <M> void mvpData(String action, M data) {
        if (data == null){
            return;
        }else {
            LogUtil.e(data.toString());
        }
    }

    @Override
    public void mvpError(String action, int code, String msg) {

    }
}
