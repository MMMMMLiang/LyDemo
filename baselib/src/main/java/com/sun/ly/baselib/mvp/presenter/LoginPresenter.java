package com.sun.ly.baselib.mvp.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.sun.lib.net.mvp.MvpPresenter;
import com.sun.ly.baselib.base.callback.BaseCallback;
import com.sun.ly.baselib.biz.LoginBiz;
import com.sun.ly.baselib.constant.BaseConstant;
import com.sun.ly.baselib.mvp.view.ILoginView;
import com.sun.ly.baselib.post.LoginPost;
import com.sun.ly.baselib.result.UserInfo;
import com.sun.view.weight.utils.LogUtil;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.util.List;

public class LoginPresenter extends MvpPresenter<ILoginView> {
    private LifecycleProvider lifecycle;

    public LoginPresenter(LifecycleProvider lifecycle) {
        this.lifecycle = lifecycle;
    }

    public void getLoginData(String userName, String pswd){
        BaseCallback<List<UserInfo>> callback = new BaseCallback<List<UserInfo>>() {
            @Override
            public List<UserInfo> convert(JsonElement data) {
                return new Gson().fromJson(data, new TypeToken<List<UserInfo>>(){}.getType());
            }

            @Override
            public void onSuccess(List<UserInfo> value) {
                getView().mvpData(BaseConstant.LOGIN_ACTION, value);
            }

            @Override
            public void onError(int code, String desc) {
                if (isViewAttached()) {
                    getView().mvpError(BaseConstant.LOGIN_ACTION, code, desc);
                }
            }

            @Override
            public void onCancel() {
                LogUtil.e("请求取消了");
            }
        };

        LoginPost post = new LoginPost();
        post.setUsername(userName);
        post.setPassword(pswd);

        //请求
        new LoginBiz().login(lifecycle, callback, post);
    }
}
