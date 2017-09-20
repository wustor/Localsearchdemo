package com.wuster.localsearchdemo.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wuster.localsearchdemo.entity.CityBean;
import com.wuster.localsearchdemo.R;
import com.wuster.localsearchdemo.base.RvAdapter;
import com.wuster.localsearchdemo.base.RvHolder;
import com.wuster.localsearchdemo.base.RvListener;

import java.util.List;

public class ClassifyDetailAdapter extends RvAdapter<CityBean> {

    public ClassifyDetailAdapter(Context context, List<CityBean> list, RvListener listener) {
        super(context, list, listener);
    }


    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.item_title : R.layout.item_classify_detail;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ClassifyHolder(view, viewType, listener);
    }

    public class ClassifyHolder extends RvHolder<CityBean> {
        TextView tvCity, tvTitle;

        public ClassifyHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            switch (type) {
                case 0:
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    tvCity = (TextView) itemView.findViewById(R.id.tvCity);
                    break;

            }
        }

        @Override
        public void bindHolder(CityBean cityBean, int position) {
            int type = ClassifyDetailAdapter.this.getItemViewType(position);
            switch (type) {
                case 0:
                    tvTitle.setText(cityBean.getPinyinFirst());
                    break;
                case 1:
                    tvCity.setText(cityBean.getRegionName());
                    break;

            }

        }
    }
}
