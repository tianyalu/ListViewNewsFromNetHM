package com.sty.listview.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sty.listview.news.adapter.NewsAdapter;
import com.sty.listview.news.bean.NewsBean;
import com.sty.listview.news.utils.NewsUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;
    private ListView lvNews;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ArrayList<NewsBean> allNews = (ArrayList<NewsBean>) msg.obj;

            if(allNews != null && allNews.size() > 0) {
                //3.创建一个adapter设置给ListView
                NewsAdapter newsAdapter = new NewsAdapter(mContext, allNews);
                lvNews.setAdapter(newsAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initViews();
    }

    private void initViews(){
        //1.找到ListView
        lvNews = (ListView) findViewById(R.id.lv_news);

        //2.先去数据库中获取缓存的新闻数据展示到listView
        ArrayList<NewsBean> allNewsDatabase = NewsUtils.getAllNewsFromDatabase(mContext);
        if(allNewsDatabase != null && allNewsDatabase.size() > 0 ) {
            //3.创建一个adapter设置给ListView
            NewsAdapter newsAdapter = new NewsAdapter(mContext, allNewsDatabase);
            lvNews.setAdapter(newsAdapter);
        }

        //4.获取新闻数据，用List封装[获取网络数据需要在子线程中做]
        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求网络数据
                //ArrayList<NewsBean> allNews = NewsUtils.getAllNews(mContext);
                ArrayList<NewsBean> allNews = NewsUtils.getAllNewsFromNetWork(mContext);
                //通过handler将msg发送到主线程中更新UI
                Message msg = Message.obtain();
                msg.obj = allNews;
                handler.sendMessage(msg);
            }
        }).start();

        //5.设置ListView条目的点击事件
        lvNews.setOnItemClickListener(this);
    }

    /**
     * ListView的条目点击时会调用该方法
     * @param parent: 代表listview
     * @param view: 点击的条目上的那个view对象
     * @param position: 条目的位置
     * @param id: 条目的ID
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //需要获取条目上bean对象中url作跳转
        NewsBean bean = (NewsBean) parent.getItemAtPosition(position);

        String url = bean.newsUrl;

        if(url.equals("NewActivity")){
            //启动新的Activity
            Intent intent = new Intent(MainActivity.this, OtherAdapterActivity.class);
            startActivity(intent);
        }else {
            //跳转浏览器
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

    }
}
