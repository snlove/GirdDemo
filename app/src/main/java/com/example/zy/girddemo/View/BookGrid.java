package com.example.zy.girddemo.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zy on 2015/9/22.
 */
public class BookGrid extends GridView {
    public BookGrid(Context context) {
        super(context);
    }

    public BookGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BookGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BookGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
