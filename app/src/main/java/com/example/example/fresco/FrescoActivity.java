package com.example.example.fresco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.example.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrescoActivity extends AppCompatActivity {
    public static final String TAG = "FrescoActivity";
    @Bind(R.id.btn_load_http_image)
    Button btnLoadHttpImage;
    @Bind(R.id.btn_asset_image)
    Button btnAssetImage;
    @Bind(R.id.btn_res_image)
    Button btnResImage;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_load_http_image)
    void loadHttpImage() {
        intent = new Intent(this, LoadHttpImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_asset_image)
    void loadAssetImage() {
        intent = new Intent(this, LoadAssetImageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_res_image)
    void loadResImage(){
        intent = new Intent(this, LoadRestImageActivity.class);
        startActivity(intent);
    }
}
