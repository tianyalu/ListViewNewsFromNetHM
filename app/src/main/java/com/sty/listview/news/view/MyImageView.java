package com.sty.listview.news.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Shi Tianyi on 2017/10/29/0029.
 */

public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            MyImageView.this.setImageBitmap(bitmap);
        }
    };

    public void setImageUrl(final String url_str){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取url对应的图片资源，bitmap
                    URL url = new URL(url_str);
                    HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();

                    openConnection.setRequestMethod("GET");
                    openConnection.setConnectTimeout(10 * 1000);

                    int code = openConnection.getResponseCode();
                    if(code == 200){
                        InputStream inputStream = openConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
