package com.wuster.localsearchdemo.utils;

import android.text.TextUtils;

import com.wuster.localsearchdemo.entity.CityBean;

import java.util.List;

/**
 * Created by fatchao
 * 日期  2017-09-18.
 * 邮箱  fat_chao@163.com
 */

public class MatchUtils {

    /**
     * @param inputStr 用户输入的字符串
     * @param old      全部数据集合
     * @param target   符合条件的集合
     */
    public static void find(String inputStr, List<CityBean> old, List<CityBean> target) {
        if (RegexUtils.isEnglishAlphabet(inputStr)) {
            //拼音模糊匹配
            findByEN(inputStr, old, target);
        } else {
            //含有中文精准匹配
            findByCN(inputStr, old, target);
        }

    }

    /**
     * 英文模糊匹配
     *
     * @param inputStr
     * @param mBodyDatas
     * @param searchResult
     */

    private static void findByEN(String inputStr, List<CityBean> mBodyDatas, List<CityBean> searchResult) {
        //把输入的内容变为大写
        String searPinyin = PinYinUtil.transformPinYin(inputStr);
        //搜索字符串的长度
        int searLength = searPinyin.length();
        //搜索的第一个大写字母
        for (int i = 0; i < mBodyDatas.size(); i++) {
            CityBean cityBean = mBodyDatas.get(i);
            //如果输入的每一个字母都和名字的首字母一样，那就可以匹配比如：武汉，WH
            if (cityBean.getMatchPin().contains(searPinyin)) {
                searchResult.add(cityBean);
            } else {
                boolean isMatch = false;
                //先去匹配单个字，比如武汉WU,HAN.输入WU，肯定匹配第一个
                for (int j = 0; j < cityBean.getNamePinyinList().size(); j++) {
                    String namePinyinPer = cityBean.getNamePinyinList().get(j);
                    if (!TextUtils.isEmpty(namePinyinPer) && namePinyinPer.startsWith(searPinyin)) {
                        //符合的话就是当前字匹配成功
                        searchResult.add(cityBean);
                        isMatch = true;
                        break;
                    }
                }
                if (isMatch) {
                    continue;
                }
//                根据拼音包含来实现，比如武汉：WUHAN,输入WUHA或者WUHAN。
                if (!TextUtils.isEmpty(cityBean.getNamePinYin()) && cityBean.getNamePinYin().contains(searPinyin)) {
                    //这样的话就要从每个字的拼音开始匹配起
                    for (int j = 0; j < cityBean.getNamePinyinList().size(); j++) {
                        StringBuilder sbMatch = new StringBuilder();
                        for (int k = j; k < cityBean.getNamePinyinList().size(); k++) {
                            sbMatch.append(cityBean.getNamePinyinList().get(k));
                        }
                        if (sbMatch.toString().startsWith(searPinyin)) {
                            //匹配成功
                            int length = 0;
                            //比如输入是WUH，或者WUHA,或者WUHAN,这些都可以匹配上
                            for (int k = j; k < cityBean.getNamePinyinList().size(); k++) {
                                length = length + cityBean.getNamePinyinList().get(k).length();
                                if (length >= searLength) {
                                    break;
                                }
                            }
                            if (!searchResult.contains(cityBean))
                                searchResult.add(cityBean);
                        }
                    }
                }

            }
        }
    }

    /**
     * 中文精准匹配
     *
     * @param inputStr
     * @param mBodyDatas
     * @param searchResult
     */
    private static void findByCN(String inputStr, List<CityBean> mBodyDatas, List<CityBean> searchResult) {
        for (int i = 0; i < mBodyDatas.size(); i++) {
            CityBean cityBean = mBodyDatas.get(i);
            if (!TextUtils.isEmpty(cityBean.getRegionName()) && cityBean.getRegionName().contains(inputStr)) {
                searchResult.add(cityBean);
            }
        }
    }
}
