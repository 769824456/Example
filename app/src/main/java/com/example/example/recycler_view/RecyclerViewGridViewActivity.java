package com.example.example.recycler_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
 * NOTE :RecyclerView实现类似GridView的效果
 */
public class RecyclerViewGridViewActivity extends AppCompatActivity implements GalleryGridViewAdapter.OnItemClickListener {
    public static final String TAG = "RecyclerViewGridViewActivity";
    @Bind(R.id.id_recycler_view_grid_view)
    RecyclerView idRecyclerViewGridView;
    private ArrayList<Integer> mDatas;
    private GalleryGridViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_grid_view);
        initDatas();
        ButterKnife.bind(this);
        //1.设置布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        idRecyclerViewGridView.setLayoutManager(gridLayoutManager);
        //2.设置适配器
        mAdapter = new GalleryGridViewAdapter(this, mDatas);
        idRecyclerViewGridView.setAdapter(mAdapter);
        idRecyclerViewGridView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//添加分割线
        //3.设置点击事件
        mAdapter.setOnItemClickListener(RecyclerViewGridViewActivity.this);
        //4.设置切换动画
        idRecyclerViewGridView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initDatas() {
        mDatas = new ArrayList<Integer>(Arrays.asList(R.mipmap.a,
                R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e,
                R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.l,
                R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.l,
                R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e,
                R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.l));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(RecyclerViewGridViewActivity.this, position + "", Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_recycler_view_grid_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item_grid_view_add:
                mAdapter.addData(0);
                break;
            case R.id.item_grid_view_remove:
                mAdapter.removeData(0);
                break;
        }
        return true;
    }

}
