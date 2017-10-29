package com.sty.listview.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.sty.listview.news.R;
import com.sty.listview.news.bean.NewsBean;
import com.sty.listview.news.view.MyImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/17/0017.
 */

public class NewsAdapter extends BaseAdapter {
    private ArrayList<NewsBean> list;
    private Context context;

    public NewsAdapter(Context context, ArrayList<NewsBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        //1.复用convertView优化ListView,创建一个view作为getView的返回值用来显示一个条目
        if(convertView != null){
            view = convertView;
        }else{
            //context:上下文 resource:要转换称view对象的layout的id root:将layout用root(ViewGroup)
            //包一层作为getView的返回值，一般传null
            view = View.inflate(context, R.layout.item_news_layout, null);  //[Layout填充的三种方式]

            //通过LayoutInflater将布局转换为view对象
            //view = LayoutInflater.from(context).inflate(R.layout.item_news_layout, null);

            //通过Context获取系统服务得到一个LayoutInflater,通过LayoutInflater将一个布局转换为view对象
            //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = layoutInflater.inflate(R.layout.item_news_layout, null);
        }
        //2.获取view上的子控件对象
        //SmartImageView itemImgIcon = (SmartImageView) view.findViewById(R.id.item_img_icon);
        MyImageView itemImgIcon = (MyImageView) view.findViewById(R.id.item_img_icon);
        TextView itemTvTitle = (TextView) view.findViewById(R.id.item_tv_title);
        TextView itemTvDes = (TextView) view.findViewById(R.id.item_tv_des);
        TextView itemTvComment = (TextView) view.findViewById(R.id.item_tv_comment);
        TextView itemTvType = (TextView) view.findViewById(R.id.item_tv_type);
        //3.获取条目对应的list集合中的新闻数据，Bean对象
        NewsBean newsBean = list.get(position);
        //4.将数据设置给这些子控件显示
        itemImgIcon.setImageUrl(newsBean.iconUrl); //设置ImageView的图片
        itemTvTitle.setText(newsBean.title);
        itemTvDes.setText(newsBean.des);
        itemTvComment.setText("评论数：" + newsBean.comment);

        //0:头条  1：娱乐  2：体育
        switch (newsBean.type){
            case 0:
                itemTvType.setText("头条");
                break;
            case 1:
                itemTvType.setText("娱乐");
                break;
            case 2:
                itemTvType.setText("体育");
                break;
            default:
                break;
        }


        return view;
    }
}
