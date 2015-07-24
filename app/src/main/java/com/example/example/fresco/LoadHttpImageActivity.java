package com.example.example.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.example.example.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 加载远程图片
 */
public class LoadHttpImageActivity extends Activity {


    @Bind(R.id.iv_fresco_simple_drawee_view)
    SimpleDraweeView ivFrescoSimpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_http_image);
        ButterKnife.bind(this);
        loadImage();
    }


    private void loadImage() {
        Uri uri = Uri.parse("http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg");
        ivFrescoSimpleDraweeView.setImageURI(uri);
    }

}
