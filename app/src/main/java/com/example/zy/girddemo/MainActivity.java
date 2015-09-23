package com.example.zy.girddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.zy.girddemo.OpenglAnim.Anim.EnterActivity;
import com.example.zy.girddemo.View.GridAdapater;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> itemName;
    private ArrayList<Integer> itemImages;
    private GridAdapater gridAdapater;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridview = (GridView) findViewById(R.id.bookGrid);
        initData();
        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        gridAdapater = new GridAdapater(this,itemName,itemImages);
//        SimpleAdapter saImageItems = new SimpleAdapter(this,
//                lstImageItem,//数据来源
//                R.layout.nitht_item,//night_item的XML实现
//
//                //动态数组与ImageItem对应的子项
//                new String[]{"ItemImage", "ItemText"},
//
//                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
//                new int[]{R.id.bookCover, R.id.bookName});
//        //添加并且显示
        gridview.setAdapter(gridAdapater);
        //添加消息处理
        gridview.setOnItemClickListener(new BookGridClick());
    }

    public void initData() {
        //生成动态数组，并且转入数据
        itemName = new ArrayList<String>();
        itemImages = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
           itemName.add(i,"NO." + String.valueOf(i));
           itemImages.add(i,R.drawable.cover);
       }

//        for (int i = 0; i < 10; i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("ItemImage", R.mipmap.ic_launcher);//添加图像资源的ID
//            map.put("ItemText", "NO." + String.valueOf(i));//按序号做ItemText
//            lstImageItem.add(map);
//        }

    }

    private  class BookGridClick implements AdapterView.OnItemClickListener{

        /**
         *  当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
         * @param parent
         * @param view
         * @param position
         * @param id
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);
//            //显示所选Item的ItemText
//            setTitle((String) item.get("ItemText"));
//            Log.d("TAG","============"+ item.get("ItemText"));
            Intent i = new Intent(MainActivity.this, EnterActivity.class);
            startActivity(i);
            Log.d("TAG","=========Enter the Book Shelf");
        }
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "if you enter the back again,app exit", Toast.LENGTH_SHORT).show();
    }



}


