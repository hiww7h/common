package com.ww7h.ww.common.utils

import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination


/**
 * Created by ww on 2018/7/12.
 */
class PinyinUtil {

    companion object {
        /**
         * 将文字转为汉语拼音
         * @param chineselanguage 要转成拼音的中文
         */
        fun toHanyuPinyin(ChineseLanguage: String): String {
            val cl_chars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
            var hanyupinyin = ""
            val defaultFormat = HanyuPinyinOutputFormat()
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE)// 输出拼音全部小写
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)// 不带声调
            defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V)
            try {
                for (i in cl_chars.indices) {
                    if (cl_chars[i].toString().matches("[\u4e00-\u9fa5]+".toRegex())) {// 如果字符是中文,则将中文转为汉语拼音
                        hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0]
                    } else {// 如果字符不是中文,则不转换
                        hanyupinyin += cl_chars[i]
                    }
                }
            } catch (e: BadHanyuPinyinOutputFormatCombination) {
                println("字符不能转成汉语拼音")
            }

            return hanyupinyin
        }
    }


    fun getFirstLettersUp(ChineseLanguage: String): String {
        return getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE)
    }

    fun getFirstLettersLo(ChineseLanguage: String): String {
        return getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE)
    }

    fun getFirstLetters(ChineseLanguage: String, caseType: HanyuPinyinCaseType): String {
        val cl_chars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
        var hanyupinyin = ""
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.setCaseType(caseType)// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)// 不带声调
        try {
            for (i in cl_chars.indices) {
                val str = cl_chars[i].toString()
                if (str.matches("[\u4e00-\u9fa5]+".toRegex())) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0].substring(0, 1)
                } else if (str.matches("[0-9]+".toRegex())) {// 如果字符是数字,取数字
                    hanyupinyin += cl_chars[i]
                } else if (str.matches("[a-zA-Z]+".toRegex())) {// 如果字符是字母,取字母
                    hanyupinyin += cl_chars[i]
                } else {// 否则不转换
                    hanyupinyin += cl_chars[i]//如果是标点符号的话，带着
                }
            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            println("字符不能转成汉语拼音")
        }

        return hanyupinyin
    }

    fun getPinyinString(ChineseLanguage: String): String {
        val cl_chars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
        var hanyupinyin = ""
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE)// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)// 不带声调
        try {
            for (i in cl_chars.indices) {
                val str = cl_chars[i].toString()
                if (str.matches("[\u4e00-\u9fa5]+".toRegex())) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(
                            cl_chars[i], defaultFormat)[0]
                } else if (str.matches("[0-9]+".toRegex())) {// 如果字符是数字,取数字
                    hanyupinyin += cl_chars[i]
                } else if (str.matches("[a-zA-Z]+".toRegex())) {// 如果字符是字母,取字母

                    hanyupinyin += cl_chars[i]
                } else {// 否则不转换
                }
            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            println("字符不能转成汉语拼音")
        }

        return hanyupinyin
    }

    /**
     * 取第一个汉字的第一个字符
     * @Title: getFirstLetter
     * @Description: TODO
     * @return String
     * @throws
     */
    fun getFirstLetter(ChineseLanguage: String): String {
        val cl_chars = ChineseLanguage.trim { it <= ' ' }.toCharArray()
        var hanyupinyin = ""
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE)// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE)// 不带声调
        try {
            val str = cl_chars[0].toString()
            if (str.matches("[\u4e00-\u9fa5]+".toRegex())) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
                        cl_chars[0], defaultFormat)[0].substring(0, 1)
            } else if (str.matches("[0-9]+".toRegex())) {// 如果字符是数字,取数字
                hanyupinyin += cl_chars[0]
            } else if (str.matches("[a-zA-Z]+".toRegex())) {// 如果字符是字母,取字母

                hanyupinyin += cl_chars[0]
            } else {// 否则不转换

            }
        } catch (e: BadHanyuPinyinOutputFormatCombination) {
            println("字符不能转成汉语拼音")
        }

        return hanyupinyin
    }

}