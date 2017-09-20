package com.wuster.localsearchdemo.holder;

import android.view.View;
import android.widget.TextView;

import com.wuster.localsearchdemo.entity.CityBean;
import com.wuster.localsearchdemo.R;
import com.wuster.localsearchdemo.base.RvHolder;
import com.wuster.localsearchdemo.base.RvListener;


/**
 * author pangchao
 * created on 2017/6/15
 * email fat_chao@163.com.
 */

public class AddressSearchHolder extends RvHolder<CityBean> {
    private TextView nickName;

    public AddressSearchHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        nickName = (TextView) itemView.findViewById(R.id.tvCity);

    }

    @Override
    public void bindHolder(CityBean meiTuanBean, int position) {
        nickName.setText(meiTuanBean.getRegionName());
    }
}
