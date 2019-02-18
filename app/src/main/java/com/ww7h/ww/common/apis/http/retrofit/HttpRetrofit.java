package com.ww7h.ww.common.apis.http.retrofit;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class HttpRetrofit {

    private static Retrofit[] retrofits = new Retrofit[2];
    private volatile static HttpRetrofit instance = null;

    private HttpRetrofit(){}

    private static HttpRetrofit getInstance(){
        if(instance==null){
            synchronized (HttpRetrofit.class){
                if(instance==null){
                    instance = new HttpRetrofit();
                }
            }
        }
        return instance;
    }

    public static <T> T initGsonRetrofit(Class<T> t){
        if(retrofits[0]==null){
            synchronized (HttpRetrofit.class){
                if(retrofits[0]==null){
                    retrofits[0] = getInstance().getRetrofit(GsonConverterFactory.create());
                }
            }
        }
        return retrofits[0].create(t);
    }

    public <T> T initXmlRetrofit(Class<T> t){
        if(retrofits[1]==null){
            synchronized (HttpRetrofit.class){
                if(retrofits[1]==null){
                    retrofits[1] = getRetrofit(SimpleXmlConverterFactory.create());
                }
            }

        }
        return retrofits[1].create(t);
    }

    private <T extends Converter.Factory> Retrofit getRetrofit(T factory){
        return new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置 网络请求 Url
                .addConverterFactory(factory) //设置使用Xml解析(记得加入依赖)
                .build();
    }

}
