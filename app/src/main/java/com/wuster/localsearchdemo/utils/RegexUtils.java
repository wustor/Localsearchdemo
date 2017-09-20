package com.wuster.localsearchdemo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtils {


    //输入字母的正则校验
    static boolean isEnglishAlphabet(String str) {
        Pattern p = Pattern.compile("^[A-Za-z]+$");
        Matcher m = p.matcher(str);
        return m.find();
    }

    //输入中文的正则校验
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }
}
