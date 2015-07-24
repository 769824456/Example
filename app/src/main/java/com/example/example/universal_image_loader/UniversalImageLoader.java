package com.example.example.universal_image_loader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.example.R;
import com.example.example.weather.utils.LogUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * PROJECT_NAME :Example
 * VERSION :[V 1.0.0]
 * AUTHOR : yulong sun
 * CREATE AT : 7/21/2015 2:01 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE : 选择区域
 * 教程地址
 * http://blog.csdn.net/vipzjyno1/article/details/23206387
 *
 */
public class UniversalImageLoader extends AppCompatActivity {

    public static final String TAG = "UniversalImageLoader";

    @Bind(R.id.iv_universal_image_loader)
    ImageView ivUniversalImageLoader;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_image_loader);
        ButterKnife.bind(this);

        initImageLoaderConfig();
        initDisplayImageOptions();

        imageLoader = ImageLoader.getInstance();
        String uri="http://p3.gexing.com/kongjianpifu/20120825/1625/50388c07cebd1.jpg";
        imageLoader.displayImage(uri, ivUniversalImageLoader, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                LogUtil.d(TAG,"onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                LogUtil.d(TAG,"onLoadingFailed");
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                LogUtil.d(TAG,"onLoadingComplete");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                LogUtil.d(TAG,"onLoadingCancelled");
            }
        });
    }

    private void initImageLoaderConfig() {
        //建造者模式：
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480,800)//保存每个缓存图片的最大长宽
                .threadPoolSize(3)//线程池加载数量
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))
                .memoryCacheSize(2*1024*1024)
                .diskCacheSize(50*1024*1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this,5000,3000))
                .writeDebugLogs()
                .build();

        //全局初始化此配置
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }
    private void initDisplayImageOptions() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//设置图片加入缓存前，对bitmap进行设置
//.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(50))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }


}
