package com.example.example.evernote.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.example.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class ToolbarActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());

        ButterKnife.bind(this);

        initToolbar(getToolbarTitle());
    }

    /**初始化标题
     * @param toolBarTitle
     */
    private void initToolbar(String toolBarTitle) {
        toolbar.setTitle(toolBarTitle);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**获取要设置的标题
     * @return
     */
    protected abstract String getToolbarTitle();

    /**获取布局文件
     * @return
     */
    protected abstract int getLayoutView();
}
