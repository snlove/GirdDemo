package com.example.zy.girddemo.OpenglAnim.OpenglUtil;

import android.content.Context;
import android.view.Window;

/**
 * Created by zy on 2015/9/1.
 */
public interface Game {


    public GLGraphics getGraphics();



    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();

    public Context getContext();

    public Window getApplyWindow();
}
