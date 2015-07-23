package com.example.example.recycler_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.example.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * PROJECT_NAME :Example
 * VERSION :[V 1.0.0]
 * AUTHOR :  yulongsun 
 * CREATE AT : 7/23/2015 1:35 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE :RecyclerView
 */
public class RecyclerViewHorizontalActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {
    public static final String TAG = "RecyclerViewHorizontalActivity";
    @Bind(R.id.id_recycler_view_horizontal)
    RecyclerView idRecyclerViewHorizontal;
    @Bind(R.id.iv_recycler_view_content)
    ImageView ivRecyclerViewContent;
    private ArrayList<Integer> mDatas;
    private GalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initDatas();
        ButterKnife.bind(this);
        ivRecyclerViewContent.setImageResource(0);
        //1.设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        idRecyclerViewHorizontal.setLayoutManager(linearLayoutManager);
        //2.设置适配器
        mAdapter = new GalleryAdapter(this, mDatas);
        idRecyclerViewHorizontal.setAdapter(mAdapter);
        //3.设置点击事件
        mAdapter.setOnItemClickListener(RecyclerViewHorizontalActivity.this);
    }

    private void initDatas() {
        mDatas = new ArrayList<Integer>(Arrays.asList(R.mipmap.a,
                R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e,
                R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.l));

    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(RecyclerViewActivity.this, position + "", Toast.LENGTH_SHORT)
//                .show();
        ivRecyclerViewContent.setImageResource(mDatas.get(position));
    }

}
