package com.ww7h.ww.common.utils

/**
 * Created by ww on 2018/7/12.
 */
class NumberUtil {
    companion object {
        fun Str2Int(str:String):Int{
            try {
                return str.toInt()
            }catch (e:Exception){
                return 0;
            }
        }

        fun Str2Double(str:String):Double{
            try {
                return str.toDouble()
            }catch (e:Exception){
                return 0.0;
            }
        }
    }
}