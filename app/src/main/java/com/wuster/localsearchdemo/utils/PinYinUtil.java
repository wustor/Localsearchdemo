package com.wuster.localsearchdemo.utils;

import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;
import com.wuster.localsearchdemo.entity.CityBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PinYinUtil {
    private static String indexStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static List<CityBean> getOrderCity(List<CityBean> totalList) {
        for (CityBean cityBean : totalList) {
            if (!TextUtils.isEmpty(cityBean.getRegionName())) {
                getPinyinList(cityBean);
            } else {
                cityBean.setPinyinFirst("#");
            }
        }
        Collections.sort(totalList, new CityComparator());//数据排序
        return totalList;
    }

    private static void getPinyinList(CityBean contactsBean) {
        //拿"武汉"举例
        StringBuilder bufferNamePiny = new StringBuilder();//拼音全拼"WUHAN"
        StringBuilder bufferNameMatch = new StringBuilder();//拼音首字母"WH"
        String name = contactsBean.getRegionName();
        for (int i = 0; i < name.length(); i++) {
            StringBuilder bufferNamePer = new StringBuilder();
            String namePer = name.charAt(i) + "";//名字的每个字
            for (int j = 0; j < namePer.length(); j++) {
                char character = namePer.charAt(j);
                String pinCh = Pinyin.toPinyin(character).toUpperCase();
                bufferNamePer.append(pinCh);
                bufferNameMatch.append(pinCh.charAt(0));
                bufferNamePiny.append(pinCh);
            }
            contactsBean.getNamePinyinList().add(bufferNamePer.toString());//单个名字集合{"WU","HAN"}
        }
        contactsBean.setNamePinYin(bufferNamePiny.toString());
        contactsBean.setMatchPin(bufferNameMatch.toString());
        String firstPinyin = contactsBean.getNamePinYin().charAt(0) + "";
        if (indexStr.contains(firstPinyin)) {
            contactsBean.setPinyinFirst(firstPinyin);
        } else {
            contactsBean.setPinyinFirst("#");
        }
    }

    public static String transformPinYin(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)).toUpperCase());
        }
        return buffer.toString();
    }

    private static class CityComparator implements Comparator<CityBean> {

        @Override
        public int compare(CityBean c1, CityBean c2) {
            if (c1.getPinyinFirst().equals("#")) {
                return 1;
            } else if (c2.getPinyinFirst().equals("#")) {
                return -1;
            }
            return c1.getPinyinFirst().compareTo(c2.getPinyinFirst());
        }
    }

}
