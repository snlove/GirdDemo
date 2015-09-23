package com.example.zy.girddemo.OpenglAnim.Anim;

import android.content.Intent;
import android.view.KeyEvent;

import com.example.zy.girddemo.MainActivity;
import com.example.zy.girddemo.OpenglAnim.BasicElements.BasciScreen;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.LogMes;
import com.example.zy.girddemo.OpenglAnim.OpenglUtil.Screen;

/**
 * Created by zy on 2015/9/22.
 */
public class ExitActivity extends BasciScreen {

    @Override
    public Screen getStartScreen() {
        return new ExitScreen(this);
    }



}
