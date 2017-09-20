package com.wuster.localsearchdemo.adapter;

import android.content.Context;
import android.view.View;


import com.wuster.localsearchdemo.holder.AddressSearchHolder;
import com.wuster.localsearchdemo.entity.CityBean;
import com.wuster.localsearchdemo.R;
import com.wuster.localsearchdemo.base.RvAdapter;
import com.wuster.localsearchdemo.base.RvHolder;
import com.wuster.localsearchdemo.base.RvListener;

import java.util.List;


public class AddressSearchAdapter extends RvAdapter<CityBean> {


    public AddressSearchAdapter(Context context, List<CityBean> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.meituan_item_select_city;
    }


    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new AddressSearchHolder(view,viewType,listener);
    }

}
