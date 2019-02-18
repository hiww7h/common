package com.ww7h.ww.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;

public class ToastUtil{

    private Context mContext;
    private static Toast mToast;
    private static ToastUtil mToastUtil;

    public static ToastUtil getInstance(@NotNull Context context){
        if(mToastUtil==null){
            synchronized (ToastUtil.class){
                if(mToastUtil==null){
                    mToastUtil = new ToastUtil(context);
                    mToast = new Toast(context);
                }
            }
        }
        mToast.cancel();
        return mToastUtil;
    }

    private ToastUtil(Context context){
        this.mContext = context;
    }

    /**
     * 短时间显示Toast【居下】
     * @param msg 显示的内容-字符串*/
    public void showShortToast(String msg) {
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.setGravity(Gravity.BOTTOM, 0, DensityUtil.INSTANCE.dp2px(mContext,64));
        mToast.show();
    }

    /**
     * 短时间显示Toast【居中】
     * @param msg 显示的内容-字符串*/
    public void showShortToastCenter(String msg){
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 短时间显示Toast【居上】
     * @param msg 显示的内容-字符串*/
    public void showShortToastTop(String msg){
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setText(msg);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.show();
    }

    /**
     * 长时间显示Toast【居下】
     * @param msg 显示的内容-字符串*/
    public void showLongToast(String msg) {
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(msg);
        mToast.setGravity(Gravity.BOTTOM, 0, DensityUtil.INSTANCE.dp2px(mContext,64));
        mToast.show();
    }
    /**
     * 长时间显示Toast【居中】
     * @param msg 显示的内容-字符串*/
    public void showLongToastCenter(String msg){
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(msg);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }
    /**
     * 长时间显示Toast【居上】
     * @param msg 显示的内容-字符串*/
    public void showLongToastTop(String msg){ 
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setText(msg);
        mToast.setGravity(Gravity.TOP, 0, 0);
        mToast.show();
    }


}
