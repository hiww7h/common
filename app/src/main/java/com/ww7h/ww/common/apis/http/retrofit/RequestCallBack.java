package com.ww7h.ww.common.apis.http.retrofit;

public interface RequestCallBack {

    public interface HttpPostCallBack<T>{
        public void postSuccess(T t);

        public void postFail(T t);
    }

}
