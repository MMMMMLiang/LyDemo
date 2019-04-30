package com.sun.ly.baselib.base.callback;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.lib.net.callback.HttpCallback;
import com.sun.ly.baselib.base.response.BaseResponse;

public abstract class BaseCallback<T> extends HttpCallback<T> {
    private BaseResponse response;

    @Override
    public T onConvert(String data) throws Exception {
        T t = null;
        response = new Gson().fromJson(data, BaseResponse.class);
        int code = response.getState();
        String msg = response.getMsg();
        JsonElement result = response.getResult();

        if (code > 0){
            t = convert(result);
        }else {
            onError(code, msg);
        }
        return t;
    }

    public abstract T convert(JsonElement data);

    @Override
    public abstract void onSuccess(T value);

    @Override
    public abstract void onError(int code, String desc);

    @Override
    public abstract void onCancel();

    @Override
    public boolean isBusinessOk() {
        return response.getState()>0;
    }
}
