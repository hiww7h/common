package com.ww7h.ww.common.utils;

public enum ConstantCode {
    RT,Request,Number;

    interface RequestCode{
        enum ListToDetail{
            CODE1("1","code1"),
            CODE2("2","CODE2");

            ListToDetail(String s, String code1) {

            }
        }
    }
}
