package com.example.example.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.example.example.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 加载Asset下的图片
 */
public class LoadAssetImageActivity extends Activity {


    @Bind(R.id.iv_load_asset_image)
    SimpleDraweeView ivLoadAssetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_asset_image);
        ButterKnife.bind(this);
        loadImage();
    }


    private void loadImage() {
        Uri uri = Uri.parse("assets://b.jpg");
//        Uri uri = Uri.parse("res://"+R.mipmap.a);
        ivLoadAssetImage.setImageURI(uri);
    }


}
