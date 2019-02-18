package com.ww7h.ww.common.apis.db;


import org.jetbrains.annotations.NotNull;

public interface GreenDaoCallBack {

    interface QueryCallBack<T> {

        void querySuccess(@NotNull T t);

        void queryFail(String message);

    }

}
