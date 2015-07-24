package com.example.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.example.R;
import com.example.example.evernote.ui.activity.NoteMainActivity;
import com.example.example.fresco.FrescoActivity;
import com.example.example.recycler_view.RecyclerViewGridViewActivity;
import com.example.example.recycler_view.RecyclerViewHorizontalActivity;
import com.example.example.recycler_view.RecyclerViewVerticalActivity;
import com.example.example.toolbar.ToolBarActivity;
import com.example.example.universal_image_loader.UniversalImageLoader;
import com.example.example.weather.activity.ChooseAreaActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * PROJECT_NAME :Example
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/2015 2:01 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 程序入口
 */
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
        //1.initDatas
        mDatas = new ArrayList<>();
        mDatas.add(RecyclerViewHorizontalActivity.TAG);
        mDatas.add(RecyclerViewVerticalActivity.TAG);
        mDatas.add(RecyclerViewGridViewActivity.TAG);
        mDatas.add(ToolBarActivity.TAG);
        mDatas.add(ChooseAreaActivity.TAG);
        mDatas.add(FrescoActivity.TAG);
        mDatas.add(UniversalImageLoader.TAG);
        mDatas.add(NoteMainActivity.TAG);
        //2.
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        lvMain.setAdapter(mAdapter);
        lvMain.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String position = mDatas.get(i);
        Intent intent = null;
        if (TextUtils.equals(position, RecyclerViewHorizontalActivity.TAG)) {       //水平画廊
            intent = new Intent(this, RecyclerViewHorizontalActivity.class);
        } else if (TextUtils.equals(position, RecyclerViewVerticalActivity.TAG)) {  //垂直
            intent = new Intent(this, RecyclerViewVerticalActivity.class);
        } else if (TextUtils.equals(position, RecyclerViewGridViewActivity.TAG)) {  //卡片
            intent = new Intent(this, RecyclerViewGridViewActivity.class);
        } else if (TextUtils.equals(position, ToolBarActivity.TAG)) {               //Toolbar
            intent = new Intent(this, ToolBarActivity.class);
        } else if (TextUtils.equals(position, ChooseAreaActivity.TAG)) {             //天气预报
            intent = new Intent(this, ChooseAreaActivity.class);
        }else if (TextUtils.equals(position, FrescoActivity.TAG)) {                 //Fresco
            intent = new Intent(this, FrescoActivity.class);
        }else if (TextUtils.equals(position, UniversalImageLoader.TAG)) {           //universal image loader
            intent = new Intent(this, UniversalImageLoader.class);
        }else if (TextUtils.equals(position, NoteMainActivity.TAG)) {               //笔记本App
            intent = new Intent(this, NoteMainActivity.class);
        }
        if (null != intent) {
            startActivity(intent);
        }
    }


}
