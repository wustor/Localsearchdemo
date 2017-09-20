package com.wuster.localsearchdemo.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fatchao
 * 日期  2017-07-11.
 * 邮箱  fat_chao@163.com
 */

public class CityBean implements Serializable {

    private String regionName;
    private boolean isTitle;//是标题还是title
    private String pinyinFirst;//悬浮栏
    private String matchPin = "";//首字母缩写,武汉则为WH
    private String namePinYin = "";//武汉,WUHAN
    private ArrayList<String> namePinyinList = new ArrayList<>();//名字拼音集合，比如武汉，WU,HAN

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }


    public String getRegionName() {
        return regionName;
    }


    public void setPinyinFirst(String pinyinFirst) {
        this.pinyinFirst = pinyinFirst;
    }

    public String getPinyinFirst() {
        return pinyinFirst;
    }

    public String getMatchPin() {
        return matchPin;
    }

    public void setMatchPin(String matchPin) {
        this.matchPin = matchPin;
    }

    public String getNamePinYin() {
        return namePinYin;
    }

    public void setNamePinYin(String namePinYin) {
        this.namePinYin = namePinYin;
    }


    public ArrayList<String> getNamePinyinList() {
        return namePinyinList;
    }



}
