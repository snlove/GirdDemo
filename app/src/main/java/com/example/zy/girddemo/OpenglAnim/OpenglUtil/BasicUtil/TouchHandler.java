package com.example.zy.girddemo.OpenglAnim.OpenglUtil.BasicUtil;

import java.util.List;

import android.view.View.OnTouchListener;

import com.example.zy.girddemo.OpenglAnim.OpenglUtil.GLGraphics;


public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    
    public int getTouchX(int pointer);
    
    public int getTouchY(int pointer);
    
    public List<GLGraphics.Input.TouchEvent> getTouchEvents();
}
