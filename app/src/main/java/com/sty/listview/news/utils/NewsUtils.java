package com.sty.listview.news.utils;

import android.content.Context;

import com.sty.listview.news.bean.NewsBean;
import com.sty.listview.news.dao.NewsDaoUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shi Tianyi on 2017/10/29/0029.
 */

public class NewsUtils {

    public static String newsPathUrl = "http://192.168.1.8/newsServiceHM/servlet/GetNewsServlet";

    //封装从服务器获取的新闻数据到List中返回
    public static ArrayList<NewsBean> getAllNewsFromNetWork(Context context){
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        try{
            //1.请求服务器获取新闻数据
            //获取一个url对象，通过url对象得到一个URLConnection对象
            URL url = new URL(newsPathUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //设置连接的方式和超时时间
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10 * 1000);
            //获取响应码
            int code = connection.getResponseCode();
            if(code == 200){
                //获取请求的流信息
                InputStream inputStream = connection.getInputStream();
                String result = StreamUtils.streamToString(inputStream);

                //2.解析获取的新闻数据到List集合中
                JSONObject rootJson = new JSONObject(result); //将一个字符串封装成一个json对象
                JSONArray jsonArray = rootJson.getJSONArray("news"); //获取rootJson中的news作为jsonArray对象

                for (int i = 0; i < jsonArray.length(); i++){ //循环遍历jsonArray
                    JSONObject newsJson = jsonArray.getJSONObject(i); //获取一条新闻的json

                    NewsBean newsBean = new NewsBean();
                    newsBean.id = newsJson.getInt("id");
                    newsBean.comment = newsJson.getInt("comment"); //评论数
                    newsBean.type = newsJson.getInt("type");  //新闻的类型 0：头条 1：娱乐 2：体验
                    newsBean.time = newsJson.getString("time");
                    newsBean.des = newsJson.getString("des");
                    newsBean.title = newsJson.getString("title");
                    newsBean.newsUrl = newsJson.getString("news_url");
                    newsBean.iconUrl = newsJson.getString("icon_url");

                    arrayList.add(newsBean);
                }

                //3. 清除数据库中旧的数据，并将数据缓存到数据库中
                new NewsDaoUtils(context).delete();
                new NewsDaoUtils(context).saveNews(arrayList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    //从数据库中获取上次缓存的新闻数据作listView的展示
    public static ArrayList<NewsBean> getAllNewsFromDatabase(Context context){
        return new NewsDaoUtils(context).getNews();
    }
}
