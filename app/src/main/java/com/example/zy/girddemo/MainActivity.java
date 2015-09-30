package com.example.zy.girddemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zy.girddemo.OpenglAnim.Anim.EnterActivity;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.Util.BitmapUtil;
import com.example.zy.girddemo.Util.Constant;
import com.example.zy.girddemo.View.GridAdapater;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> itemName;
    private ArrayList<Bitmap> itemImages;
    private Integer[] imageId;
    private GridAdapater gridAdapater;
    private GridView gridview;
    private LinearLayout linearLayout;
    private IntentFilter filter;
    BroadcastReceiver broadcastReceiver;
    MyHandler myHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.bookGrid);
        linearLayout = (LinearLayout) findViewById(R.id.layout);
        initData();
        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        gridAdapater = new GridAdapater(this, itemName, itemImages);
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
        myHandler = new MyHandler();
    }

    public void initData() {
        //生成动态数组，并且转入数据
        itemName = new ArrayList<String>();
        itemImages = BitmapUtil.loadThunmbnails(this.getResources(), 400);

        for (int i = 0; i < itemImages.size(); i++) {
            itemName.add(i, "NO." + String.valueOf(i));
        }


//        for (int i = 0; i < 10; i++) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("ItemImage", R.mipmap.ic_launcher);//添加图像资源的ID
//            map.put("ItemText", "NO." + String.valueOf(i));//按序号做ItemText
//            lstImageItem.add(map);
//        }

    }

    private class BookGridClick implements AdapterView.OnItemClickListener {

        /**
         * 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
         *
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
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;
            final Message message = myHandler.obtainMessage();
            message.arg1 = 1;
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myHandler.sendMessage(message);
                }
            },2000);
            ImageView imageView = (ImageView) view.findViewById(R.id.bookCover);
            int[] location = new int[2];
            imageView.getLocationOnScreen(location);
            int viewX = location[0];
            int viewY = location[1] + imageView.getHeight();
            int height = view.getHeight();
            int width = view.getWidth();
            float viewxXScale = (float) viewX / screenWidth;
            float viewYScale = (float) viewY / screenHeight;
            float heithtScale = (float) height / screenHeight;
            float widthScale = (float) width / screenWidth;
            Intent i = new Intent(MainActivity.this, EnterActivity.class);
            float[] postion = {viewxXScale, viewYScale};
            Bundle b = new Bundle();
            b.putFloatArray("PostionScale", postion);
            i.putExtra("Postion", b);
            i.putExtra("bookCoverId", BitmapUtil.getBitmaoIndex(position));
            startActivity(i);
            overridePendingTransition(0, 0);
            Log.d("TAG", "=========Enter the Book Shelf");
            LogMes.d("ScaleTag", "===========view" + viewYScale + "   " + viewY);
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
    protected void onPause() {
        super.onPause();
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

    class MyHandler extends  Handler{
        public MyHandler(){
            super();
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1){
                gridview.setSelection(0);
                LogMes.d(Constant.TAG, "=======action" + msg.arg1);
                super.handleMessage(msg);
            }

        }
    }



}


