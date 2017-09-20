package com.wuster.localsearchdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wuster.localsearchdemo.adapter.AddressSearchAdapter;
import com.wuster.localsearchdemo.adapter.ClassifyDetailAdapter;
import com.wuster.localsearchdemo.base.RvListener;
import com.wuster.localsearchdemo.entity.CityBean;
import com.wuster.localsearchdemo.utils.MatchUtils;
import com.wuster.localsearchdemo.utils.PinYinUtil;
import com.wuster.localsearchdemo.view.ItemHeaderDecoration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvCity;
    private ArrayList<CityBean> mBodyDatas = new ArrayList<>();//城市列表
    private EditText etSearch;
    private RelativeLayout rvContent;
    private RecyclerView rvResult;
    private ArrayList<CityBean> searchResult = new ArrayList<>();//搜索结果
    private AddressSearchAdapter searchAdapter;
    private LinearLayout llBack;
    private Context mContext;
    private DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListener();
        initData();
    }

    private void initData() {
        Type listType = new TypeToken<ArrayList<CityBean>>() {
        }.getType();
        List<CityBean> list = new Gson().fromJson(getAssetsData("address.json"), listType);//解析asset目录下的数据
        PinYinUtil.getOrderCity(list);//将数据进行排序
        mBodyDatas.addAll(list);
        initDatas(list);
    }


    private void initViews() {
        mContext = this;
        rvResult = (RecyclerView) findViewById(R.id.rv_result);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(manager);
        rvResult.setHasFixedSize(true);
        searchAdapter = new AddressSearchAdapter(this, searchResult, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //TODO 点击事件
            }
        });
        //设置悬浮索引
        rvResult.setAdapter(searchAdapter);
        rvContent = (RelativeLayout) findViewById(R.id.rv_content);
        mDividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        rvResult.addItemDecoration(mDividerItemDecoration);
        rvCity = (RecyclerView) findViewById(R.id.rv_city);
        etSearch = (EditText) findViewById(R.id.et_search);

    }

    protected void initListener() {
        llBack.setOnClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    rvContent.setVisibility(View.GONE);
                    rvResult.setVisibility(View.VISIBLE);
                } else {
                    rvContent.setVisibility(View.VISIBLE);
                    rvResult.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    filterCity(s);
                }
            }
        });
    }

    //搜索数据
    private void filterCity(Editable s) {
        searchResult.clear();
        String inputStr = s.toString();
        MatchUtils.find(inputStr, mBodyDatas, searchResult);
        //因为每次搜索的结果不同，所以匹配类型不同，但是数据源都是同一个数据源，所以每次搜索前，要重置数据
        int size = searchResult.size();
        Log.d("size-->", String.valueOf(size));
        searchAdapter.notifyDataSetChanged();
    }

    //进行排序
    private void getContacts() {
        PinYinUtil.getOrderCity(mBodyDatas);
        searchAdapter.notifyDataSetChanged();
    }


    private String getAssetsData(String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = mContext.getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", e.getMessage());
            return result;
        }
    }


    //加载城市列表
    private void initDatas(List<CityBean> data) {
        String first = data.get(0).getPinyinFirst();
        CityBean cityBean = new CityBean();
        cityBean.setPinyinFirst(first);
        cityBean.setTitle(true);
        data.add(0, cityBean);
        for (int i = 0; i < data.size(); i++) {
            if (!TextUtils.equals(data.get(i).getPinyinFirst(), first)) {
                first = data.get(i).getPinyinFirst();
                CityBean bean = new CityBean();
                bean.setPinyinFirst(first);
                bean.setTitle(true);
                data.add(i, bean);
            }
        }

        Log.d("size---->", String.valueOf(data.size()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvCity.setLayoutManager(linearLayoutManager);
        ClassifyDetailAdapter mAdapter = new ClassifyDetailAdapter(mContext, data, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {

            }
        });
        ItemHeaderDecoration decoration = new ItemHeaderDecoration(mContext, data);
        rvCity.addItemDecoration(decoration);
        rvCity.addItemDecoration(mDividerItemDecoration);
        rvCity.setAdapter(mAdapter);
        getContacts();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }


}
