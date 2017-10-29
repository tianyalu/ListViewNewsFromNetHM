package com.sty.listview.news;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/18/0018.
 */

public class OtherAdapterActivity extends Activity {
    private Context mContext;

    private ListView lvArray;
    private ListView lvSimple;

    public String[] classz = {"android", "ios", "javaEE", "C#"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other_adapter);
        mContext = this;

        initViews();
    }

    private void initViews(){
        lvArray = (ListView) findViewById(R.id.lv_array);
        lvSimple = (ListView) findViewById(R.id.lv_simple);

        //创建一个ArrayAdapter
        //context:环境,上下文 resource:布局id textViewResourceId:条目布局中textViw空控件的id
        //objects:条目上textView显示的内容
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,
                R.layout.item_other_adapter_layout, R.id.item_tv_class, classz);
        lvArray.setAdapter(arrayAdapter);


        //创建一个simpleAdapter,封装simpleAdapter的数据
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> hashMap1 = new HashMap<>();
        hashMap1.put("class", "C++");
        arrayList.add(hashMap1);

        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("class", "android");
        arrayList.add(hashMap2);

        HashMap<String, String> hashMap3 = new HashMap<>();
        hashMap3.put("class", "javaEE");
        arrayList.add(hashMap3);

        //context data:显示的数据 resource:item布局id from:map中的key to:布局控件中的id
        SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, arrayList,
                R.layout.item_other_adapter_layout, new String[]{"class"}, new int[]{R.id.item_tv_class});
        lvSimple.setAdapter(simpleAdapter);
    }
}
