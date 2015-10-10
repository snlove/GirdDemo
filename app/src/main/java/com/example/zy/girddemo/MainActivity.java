package com.example.zy.girddemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        LogMes.d("TAGPOS","=========ImageX :"+ itemImages.get(1).getWidth() + "ImageH" + itemImages.get(1).getHeight());

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

            Rect appRect = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(appRect);
            int screenWidth = appRect.width();
            int screenHeight = appRect.height();

            //更新gridV
            final Message message = myHandler.obtainMessage();
            message.arg1 = 1;
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myHandler.sendMessage(message);
                }
            },2000);

            //得到屏幕中占的比例，进行坐标转换
            ImageView imageView = (ImageView) view.findViewById(R.id.bookCover);
            int[] location = new int[2];
            imageView.getLocationOnScreen(location);
            int viewX = location[0];
            int viewY = location[1];
            int height = imageView.getHeight();
            int width = imageView.getWidth();
            float viewxXScale = (float) viewX / screenWidth;
            float viewYScale = (float) (viewY - appRect.top)/ screenHeight;
            float heithtScale = (float) 400 / screenHeight;
            float widthScale = (float) 200 / screenWidth;

            //回到初始位置的坐标
            int[] firstPos = getFirItemPos();
            int firstX = firstPos[0];
            int firstY = firstPos[1]  ;
            float firstXScale = (float) (viewX) / screenWidth;
            float firstYScale = (float) (viewY - appRect.top ) / screenHeight;
            float[] fristscale = {firstXScale,firstYScale};

            //传递数据
            Intent i = new Intent(MainActivity.this, EnterActivity.class);
            float[] postion = {viewxXScale, viewYScale};
            float[] scale = {widthScale, heithtScale};
            Bundle b = new Bundle();
            b.putFloatArray("PostionScale", postion);
            b.putFloatArray("ViewScale", scale);
            b.putFloatArray("FirstPostion", fristscale);
            i.putExtra("Postion", b);
            i.putExtra("bookCoverId", BitmapUtil.getBitmaoIndex(position));
            startActivity(i);
            overridePendingTransition(0, 0);
            LogMes.d("TAGPOS", "=========viewX :" + viewX + "viewY" + viewY);
            LogMes.d("TAGPOS","=========width :"+ width + "height" + height);
            LogMes.d("TAGPOS","========="+ appRect.top);
        }
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
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


    //得到第一个图片的位置
    private int[] getFirItemPos() {

         Bitmap bitmap= itemImages.get(0);
        int[] fristPostion  = {bitmap.getWidth(),bitmap.getHeight()};
        return  fristPostion;
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


