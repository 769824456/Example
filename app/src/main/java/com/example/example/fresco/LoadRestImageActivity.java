package com.example.example.fresco;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.example.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 加载Res下的图片资源
 */
public class LoadRestImageActivity extends AppCompatActivity {


    @Bind(R.id.iv_load_res_image)
    SimpleDraweeView ivLoadResImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_res_image);
        ButterKnife.bind(this);
        loadImage();
    }

    private void loadImage() {
        Uri uri = Uri.parse("mipmap://"+R.mipmap.g);
        ivLoadResImage.setImageURI(uri);

    }

}
