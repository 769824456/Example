package com.example.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.example.recycler_view.RecyclerViewGridViewActivity;
import com.example.example.recycler_view.RecyclerViewHorizontalActivity;
import com.example.example.recycler_view.RecyclerViewVerticalActivity;
import com.example.example.toolbar.ToolBarActivity;
import com.example.example.weather.activity.ChooseAreaActivity;
import com.example.example.weather.activity.ChooseAreaActivity_;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.lv_main)
    ListView lvMain;
    private ArrayList<String> mDatas;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListView();
    }

    private void initListView() {
        mDatas = new ArrayList<>();
        mDatas.add(RecyclerViewHorizontalActivity.TAG);
        mDatas.add(RecyclerViewVerticalActivity.TAG);
        mDatas.add(RecyclerViewGridViewActivity.TAG);
        mDatas.add(ToolBarActivity.TAG);
        mDatas.add("天气预报");


        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        lvMain.setAdapter(mAdapter);
        lvMain.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String position = mDatas.get(i);
        Intent intent = null;
        if (TextUtils.equals(position, RecyclerViewHorizontalActivity.TAG)) {
            intent = new Intent(this, RecyclerViewHorizontalActivity.class);
        } else if (TextUtils.equals(position, RecyclerViewVerticalActivity.TAG)) {
            intent = new Intent(this, RecyclerViewVerticalActivity.class);
        } else if (TextUtils.equals(position, RecyclerViewGridViewActivity.TAG)) {
            intent = new Intent(this, RecyclerViewGridViewActivity.class);
        } else if (TextUtils.equals(position, ToolBarActivity.TAG)) {
            intent = new Intent(this, ToolBarActivity.class);
        } else if (TextUtils.equals(position, ChooseAreaActivity.TAG)) {
            intent = new Intent(this, ChooseAreaActivity_.class);
        }
        if (null != intent) {
            startActivity(intent);
        }
    }


}
