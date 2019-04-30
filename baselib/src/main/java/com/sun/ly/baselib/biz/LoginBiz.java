package com.sun.ly.baselib.biz;

import com.google.gson.Gson;
import com.sun.lib.net.SunHttp;
import com.sun.lib.net.callback.HttpCallback;
import com.sun.ly.baselib.base.BaseBiz;
import com.sun.ly.baselib.constant.BaseConstant;
import com.sun.ly.baselib.constant.BaseUrl;
import com.sun.ly.baselib.encryption.Algorithm;
import com.sun.ly.baselib.post.LoginPost;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.util.TreeMap;

public class LoginBiz extends BaseBiz {
    public void login(LifecycleProvider lifecycle, HttpCallback callback, LoginPost post){
        Gson gs = new Gson();
        String params = null;
        String sign = null;
        try {
            params = Algorithm.DesEncrypt(gs.toJson(post), BaseConstant.HB_KeyStr);

            sign = Algorithm.Md5Encrypt(BaseConstant.HB_KeyStr + "Method" + BaseUrl.SetMemberLogin
                    + "Params" + params + BaseConstant.HB_KeyStr, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }


        TreeMap<String, Object> request = new TreeMap<>();
        request.put("Method", BaseUrl.SetMemberLogin);
        request.put("Params", params);
        request.put("Sign", sign);

        SunHttp http = new SunHttp.Builder()
                .get()
                .baseUrl("http://newapi.hebiw.com/")
                .apiUrl("")
                .addParameter(request)
                .lifecycle(lifecycle)
                .build();

        http.request(callback);
    }
}
